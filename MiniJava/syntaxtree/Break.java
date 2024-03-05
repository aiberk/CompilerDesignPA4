package syntaxtree;

public class Break extends Statement{

    public Break(){
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}