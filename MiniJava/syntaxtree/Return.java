package syntaxtree;

public class Return extends Statement{
    public Exp e;
    
    public Return(Exp e){
        this.e=e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}