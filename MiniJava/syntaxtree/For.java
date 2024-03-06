package syntaxtree;

public class For extends Statement{
    public IdentifierExp type;
    public IdentifierExp i;
    public boolean is_increment;
    public Exp e;
    public Exp e1;
    public InPlaceOp op;
    public StatementList for_block;
    public For(IdentifierExp type, IdentifierExp i, Exp e, Exp e1, InPlaceOp op, StatementList for_block){
        this.type = type; this.i = i; this.e = e;
        this.e1 = e1; this.op = op; this.for_block = for_block;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}