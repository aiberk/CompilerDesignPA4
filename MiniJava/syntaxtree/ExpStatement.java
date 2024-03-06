package syntaxtree;

public class ExpStatement extends Statement {
    public Exp e;
    
    public ExpStatement(Exp e){
        this.e = e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}