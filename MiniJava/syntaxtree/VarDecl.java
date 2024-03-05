package syntaxtree;

public class VarDecl {
    public Identifier t;
    public Identifier i;
    public boolean is_array;
    
    public VarDecl( Identifier t, Identifier i, boolean is_array){
        this.t=t; this.i=i;
        this.is_array = is_array;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}