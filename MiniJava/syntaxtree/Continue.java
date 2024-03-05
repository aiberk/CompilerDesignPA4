package syntaxtree;

public class Continue extends Statement{

    public Continue(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}