package syntaxtree;

public class IntegerLiteral extends Exp{
    public String s;
    
    public IntegerLiteral(String i){
        this.s=i;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}