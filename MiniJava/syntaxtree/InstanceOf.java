package syntaxtree;

public class InstanceOf extends Exp{

    public Exp e1;
    public Exp e2;
    
    public InstanceOf(Exp e1,Exp e2){
        this.e1=e1; this.e2=e2;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}