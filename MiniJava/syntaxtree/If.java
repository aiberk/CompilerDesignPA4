package syntaxtree;

public class If extends Statement{
    public Exp e;
    public StatementList if_block;
    public ElseIf elif_block;
    public StatementList else_block;
    
    public If(Exp e, StatementList if_block, ElseIf elif_block, StatementList else_block){
        this.e=e;
        this.if_block = if_block;
        this.elif_block = elif_block;
        this.else_block = else_block;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}