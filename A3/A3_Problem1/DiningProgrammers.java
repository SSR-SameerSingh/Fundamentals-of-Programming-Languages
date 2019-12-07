
public class DiningProgrammers {

	public static void main(String[] args)  {
	    
		 Forks fs = new Forks();
		 
	        Programmer p1 = new Programmer("James", fs);
	        Programmer p2 = new Programmer("Bjarne", fs);
	        Programmer p3 = new Programmer("Guido", fs);    
	        Programmer p4 = new Programmer("Brendan", fs);
	        Programmer p5 = new Programmer("Martin", fs);
	        
	        p1.start();
	        p2.start();
	        p3.start();
	        p4.start();
	        p5.start();
	    }
	}

	class Programmer extends Thread  {
		
	    String name;
	    Forks fs;
	    int count = 0;   // the number of forks in hand
	    String state;
	    
	    Programmer(String name, Forks fs) {   
	    	this.name = name;
	    	this.count = 0;
	    	this.fs = fs;
	    }
	    
	    @Override
	    public void run() {
	        try {
	         set_state("C");                     // C = coding
	          for(int i=0; i<50; i++) {
	           fs.pickup(this);
	            set_state("H");                  // H = hungry
	            fs.pickup(this);
	            Thread.sleep((int)(250*Math.random()));
	            set_state("E");                  // E = eating
	            set_state("C"); 
	            fs.drop(this);
	          }
	        } 
	        catch(Exception e){}
	    }
	    
	    void set_state(String s){
	    	state = s;
	    	try {
	    	if (s.equals("C")) {
	        	System.out.println (name + " is busy coding");
	            Thread.sleep((int)(300*Math.random()));
	        }
	    	if (s.equals("H")) {
	    		System.out.println (name + " got the first fork");
	            Thread.sleep((int)(200*Math.random()));
	    	}
	    	if (s.equals("E")) {
	            System.out.println (name +" got two forks and is eating"); 
	    		Thread.sleep((int)(100*Math.random())); 
	            System.out.println (name +" put down both forks");
	    	}
	    	}
	    	catch (Exception e) {}
	    }
	}

	class Forks {
	     int n = 5;
	     // provide the code for 'pickup' and 'drop'
	     synchronized void pickup(Programmer p) {
	    	 try {
		    	 while(n!=0 && p.count<2) {
		    		 if(p.count==0 && n>1) {
		    			 p.count=1;
		    			 n--;
		    		 }
		    		 else if(p.count==1 && n>0) {
		    			 p.count=2;
		    			 n--;	
		    		 }
		    		 else {
		    			 wait();
		    		 }
		    	 }
	    	 }
	    	 catch(Exception e) {}
	     }
	     synchronized void drop(Programmer p) {
	    	 try {
	    		 if(p.count>0) {
	    			 p.count=p.count-2;
	    			 n=n+2;
	    			 notify();
	    		 }
	    	 }
	    	 catch(Exception e) {}
	     }

	}   



