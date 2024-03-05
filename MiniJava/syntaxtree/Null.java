package syntaxtree;

public class Null extends Exp{

    public Null(){

    }


    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}