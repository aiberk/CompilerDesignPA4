package syntaxtree;

public class Inline extends Exp{
    public Exp conditional;
    public Exp if_true;
    public Exp if_false;
    public Inline(Exp conditional,Exp if_true, Exp if_false){
        this.conditional = conditional;
        this.if_true = if_true;
        this.if_false = if_false;
    }

    public Object accept(Visitor visitor, Object data) {
        return visitor.visit(this, data);
      }
    
}