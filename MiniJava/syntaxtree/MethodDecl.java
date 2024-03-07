package syntaxtree;

public class MethodDecl {
    public Identifier type;
    public Identifier name;
    public FormalList args;
    public VarDeclList vars;
    public boolean is_array;
    public StatementList statements;
    public Exp returns;

    public MethodDecl(Identifier type, boolean is_array, Identifier name, FormalList args,
                       VarDeclList vars, StatementList statements, Exp returns){
        this.type=type;this.name=name;this.args=args;this.vars=vars;this.statements=statements;this.returns=returns;
        this.is_array = is_array;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}