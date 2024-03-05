package syntaxtree;

public class NewObject extends Exp{
    public Exp i;
    
    public NewObject(Exp i){
        this.i=i;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}