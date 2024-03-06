package syntaxtree;

public class While extends Statement{
    public Exp e;
    public StatementList s;
    
    public While(Exp e,StatementList s){
        this.e=e; this.s=s;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}