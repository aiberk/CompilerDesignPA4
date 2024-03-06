package syntaxtree;

public class ElseIf extends Statement {
    public Exp e;
    public StatementList block;
    public ElseIf n;

    public ElseIf(ElseIf n, Exp e, StatementList block){
        this.e=e;
        this.block = block;
        this.n=n;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}