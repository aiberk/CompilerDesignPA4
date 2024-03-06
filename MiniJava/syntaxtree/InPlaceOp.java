package syntaxtree;

public class InPlaceOp extends Statement{
    public IdentifierExp i;
    public boolean is_increment;
    
    public InPlaceOp(IdentifierExp i, boolean is_increment){
        this.i=i;
        this.is_increment = is_increment;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}