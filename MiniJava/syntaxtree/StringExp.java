package syntaxtree;

public class StringExp extends Exp{
    public String s;
    
    public StringExp(String s){
        this.s=s;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}