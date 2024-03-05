package syntaxtree;

public class Throw extends Statement{
    public Exp e;
    
    public Throw(Exp e){
        this.e=e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}