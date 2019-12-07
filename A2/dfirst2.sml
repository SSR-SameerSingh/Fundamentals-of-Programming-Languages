Control.Print.printDepth := 10;

datatype 'a tree = leaf | node of 'a * 'a tree * 'a tree;

fun insert(i, leaf) = node(i,leaf,leaf)
  | insert(i, node(v,left,right)) =
                if i = v then node(v,left,right)
                else if i < v then node(v,insert(i,left),right)
                else node(v, left, insert(i,right));

fun dfirst(leaf) = []
  | dfirst(node(v,t1,t2)) =
                dfirst(t1) @ [v] @ dfirst(t2);


(* fill in the blanks below *)

fun dfirst2(tr) =

     let fun df([], ans) = ans
           | df(leaf::t, ans) = df(t,ans)
           | df(node(v,leaf,t2)::t, ans) = df(t2::t, ans@[v])
           | df(node(v,t1,t2)::t, ans) = df([t1,node(v,leaf,leaf),t2]@t, ans)
      in
            df([tr],[])
        end;

fun testcase() =

        let fun reduce(f,b,[]) = b
              | reduce(f,b,x::t) = f(x,reduce(f,b,t));

             val testcase = reduce(insert,
                                             leaf,
                                             [100,50,150,200,125,175,250])
         in
            (dfirst(testcase), dfirst2(testcase))
        end;
