Control.Print.printDepth := 10;

datatype 'a ntree = leaf of 'a | node of 'a ntree list;

fun map(f,[]) = []
  | map(f,x::t) = f(x) :: map(f,t);

fun reduce(f,b,[]) = b
  | reduce(f,b,x::t) = f(x,reduce(f,b,t));

(* fill in the blanks *)

fun toString(leaf(x)) = x ^ " "
  | toString(node(l)) = 
		let fun h(tr,y) = if y = "" then
					toString(tr)
				  else
					toString(tr)^ y;
		 in reduce(h,"",l)
		end;

(* fill in the blanks *)

fun subst(leaf(x), v1, v2) = 
			if x = v1 then
		leaf(v2)
				else
		leaf(x)
  | subst(node(l), v1, v2) = 
		let fun h(tr) = subst(tr, v1, v2)
		in  node(map(h,l))
		end;

val part_4a = subst(node([node([leaf("x"),leaf("y")]), 
			  leaf("z"), 
			  leaf("w"), 
			  node([leaf("x"),leaf("y")])
			 ]),"x","w");

val part_4b = toString(part_4a);
