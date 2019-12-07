
import Scheduler.Guard;
import java.util.*;

import Scheduler.Scheduler;
import Scheduler.Process;


public class RWP_MessagePassing {

	public static void main(String[] args) {

		RWController rwc = new RWController();
		Database d = new Database();
		
		Reader r1 = new Reader();
		Reader r2 = new Reader();
		Reader r3 = new Reader();
		Reader r4 = new Reader();
		Reader r5 = new Reader();
		
		Writer w1 = new Writer();
		Writer w2 = new Writer();
		Writer w3 = new Writer();
		Writer w4 = new Writer();
		Writer w5 = new Writer();

		Process[] arr = {rwc,d,r1,r2,r3,r4,r5,w1,w2,w3,w4,w5};
		Scheduler s = new Scheduler(arr, false);
		s.start();
	}
}


// --------------Reader Process --------------------


class Reader extends Process  {

	public void run() {
		for (int i = 0; i < 5; i++) {;
			send("request_read");
			receive("read");
			System.out.println("Database read = " + (int) get_data() + "\n");
			send("done_read");		
		}
	}
}

// ---------------- Writer Process ------------------


class Writer extends Process {

	public void run() {
		Random r = new Random();
		for (int i = 1; i < 6; i++) {	
			try {Thread.sleep(250);} catch (Exception e) {}
			send("request_write");
			send_data("write", i*1000); 
			send("done_write");
		}
	}
}

// --------------Database Process --------------------


class Database extends Process {
	
	private int data = 0;
	String state;
	
	public void run() {
		while (true) {
			
			send_or_receive_data("read",data,"write");
			state = chosen;
			if (chosen.equals("write")) {
				data = data + (int) get_data();
				System.out.println("Database write = " + data + "\n");
			}
		}
	}
	
}


// --------------- RWController ----------------------


class RWController extends Process {
	 
	private int r = 0, w = 0, ww = 0, wr = 0;
	
	public void run() {
		schedule_with_priority();
	}
	
	public void schedule_with_priority() {
		Guard g = new G();
		while (true) {
			String state = "init";
			receive_guard(g, "request_read", "request_write");
			state = chosen;
			if (state.equals("request_write")){
					w = 1;
					receive("done_write");
					w = 0;
					state = "done_write";
				// to be completed by you			 } 
			}
			else {
				r = 1;
			 while (r != 0) {
				receive_guard(g, "request_read", "done_read");
				state = chosen;
				switch(chosen) {
				  case ("request_read"): r++; break;
				  case ("done_read"):    r--; break;
				}
			}
			}
		}
	}
    
// sets the ww field
int ww() {
	int t = num_waiting("send", "request_write");
	if (t != ww)
	   ww = t;
	return ww;
}

// sets the wr field
int wr() {
	int t = num_waiting("send", "request_read");	 
	if (t != wr)
	   wr = t;
	return wr;
}

class G implements Guard {
	public boolean test(Object o) {
		return ww()==0;
	}
}


public void schedule_without_priority() {
		while (true) {
		   String state = "init";
		   receive("request_read", "request_write");
	         state = chosen;
	         switch(chosen) {
			  case ("request_read"):
				 r = 1;
				 while (r != 0) {
					receive("request_read", "done_read");
					state = chosen;
					switch(chosen) {
					  case ("request_read"): r++; break;
					  case ("done_read"):    r--; break;
					}
				}
			    break;
			 case ("request_write"):
				    w = 1;
					receive("done_write");
					w = 0;
					state = "done_write";
			}
		}
	}

}