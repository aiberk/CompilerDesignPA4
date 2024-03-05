package syntaxtree;

public class Attribute extends Exp{
    public Identifier i;
    public Exp e;
    
    public Attribute(Exp e, Identifier i){
        this.e=e; this.i=i;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}