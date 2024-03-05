package syntaxtree;

public class CharacterExp extends Exp{
    public String c;
    
    public CharacterExp(String c){
        this.c=c;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}