package syntaxtree;

public class MethodDecl {
    public Identifier type;
    public Identifier name;
    public VarDeclList args;
    public VarDeclList vars;
    public StatementList statements;
    public Exp returns;

    public MethodDecl(Identifier type, Identifier name, VarDeclList args,
                       VarDeclList vars, StatementList statements, Exp returns){
        this.type=type;this.name=name;this.args=args;this.vars=vars;this.statements=statements;this.returns=returns;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}