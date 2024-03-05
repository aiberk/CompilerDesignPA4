package syntaxtree;

public class Call extends Exp{
    public Exp e1;
    public Identifier i;
    public ExpList e2;

    public Call(Exp e1,Identifier i, ExpList e2){
        //NOTE: `i` is always null as we only need to perform the call on `e1`
        //NOTE: `e2` can be null, to represent a function call with no parameters passed i.e f()
        this.e1=e1; this.i=i; this.e2=e2;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}