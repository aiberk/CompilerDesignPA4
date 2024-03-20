import syntaxtree.*;
import java.util.HashMap;

public class TypeCheckingVisitor implements Visitor {

    public SymbolTable st;

    public int num_errors=0;

    public static PP_Visitor miniC = new PP_Visitor();

    public TypeCheckingVisitor(SymbolTable st) {
        this.st = st;
    }

    public static String format_type(String tname, boolean is_array){
        return tname + (is_array ? "[]" : "");
    }

    public String type_lookup(Object data, String label){
        String dt = (String) data;
        if (this.st.typeName.containsKey(dt + "$" + label)){
            return this.st.typeName.get(dt + "$" + label);
        }
        
        return this.st.typeName.get(dt.replaceAll("\\$\\w+$", "") + "$" + label);
        
    }

    public static String getTypeName(Object node){
        // we only recognize 3 types in miniC  int, boolean, and *void
        if (node.getClass().equals(syntaxtree.BooleanType.class)){
            return "boolean";
        }else if (node.getClass().equals(syntaxtree.IntegerType.class)){
            return "int";
        } else {return "void";}
    }

    public Object visit(And node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("boolean") || !t2.equals("boolean")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "boolean"; 
    } 

    public Object visit(ArrayAssign node, Object data){ 
        // not in miniC
        Identifier i = node.i;
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.i.accept(this,data);
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        String target_type = this.type_lookup(data, node.i.s);

        if (!t1.equals("int")){
            System.out.println("Array assign requires integer index value");
            num_errors++;
        }
        if (!target_type.replaceAll("\\[\\]$", "").equals(t2)){
            System.out.println("Array assign types do not match: "+target_type.replaceAll("\\[\\]$", "") + " != "+t2);
            num_errors++;
        }
        return "void";
    } 

    public Object visit(ArrayLength node, Object data){ 
        // not in miniC
        Exp e=node.e;
        node.e.accept(this, data);
        return data; 
    } 

    public Object visit(ArrayLookup node, Object data){ 
        // not in miniC
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t = (String) node.e1.accept(this, data);
        String t1 = (String) node.e2.accept(this, data);
        if (!t1.equals("int")){
            System.out.println("Array assign requires integer index value");
            num_errors++;
            return "void";
        }

        return t.replaceAll("\\[\\]$", "");
    } 

    public Object visit(Assign node, Object data){ 
        Identifier i=node.i;
        Exp e=node.e;
        String t1 = (String) node.i.accept(this, data);
        String t2 = (String) node.e.accept(this, data);
        if (!t1.equals(t2)) {
            System.out.println("Assign Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }
        return "void"; 
    } 

    public Object visit(Block node, Object data){ 
        StatementList slist=node.slist;
        if (node.slist != null){    
            node.slist.accept(this, data);
        }
        return "void"; 
    } 

    public Object visit(BooleanType node, Object data){ 
        return "void";
    } 

    public Object visit(Call node, Object data){ 
        
        MethodDecl m = this.attr_method_obj((Attribute) node.e1, data);
        String m_path = this.attr_method_obj_path((Attribute) node.e1, data);
        String return_type = this.format_type(m.type.s, m.is_array);
        String args = "";
        if (m.args != null){
            args = (String) m.args.accept(this, m_path);
        }

        String vars = "";
        if (node.e2 != null){
            vars = (String) node.e2.accept(this, data);
        }
        if (!args.equals(vars)) {
            System.out.println("Call Type error: " + args + " != " + vars+" in method "+m.name.s);
            num_errors++;
            return "void";
        }

        return return_type;
    } 

    public Object visit(ClassDecl node, Object data){ 
        String dt = (String) data + ((( String)data).length() == 0 ? node.i.s : "$" + node.i.s);
        Identifier i = node.i;
        VarDeclList v=node.v;
        MethodDeclList m=node.m;
        node.i.accept(this, dt);
        if (node.v != null){
            node.v.accept(this, dt);
        }
        if (node.m != null){
            node.m.accept(this, dt);
        }
        return "void";
    } 

    public Object visit(ClassDeclList node, Object data){ 
        // not in miniC
        ClassDecl c=node.c;
        ClassDeclList clist=node.clist;
        node.c.accept(this, data);
        if (node.clist != null){
            node.clist.accept(this, data);
        }

        return "void";
    } 

    public Object visit(ExpGroup node, Object data){ 
        Exp e=node.e;
        String result = (String) node.e.accept(this, data);

        return result; 
    } 

    public Object visit(ExpList node, Object data){ 
        // this return a list of the types of the expressions!
        Exp e=node.e;
        ExpList elist=node.elist;
        String t1 = (String) node.e.accept(this, data);
        String t2 = "";
        if (node.elist != null){
            t2 = (String) node.elist.accept(this, data);
        }
        return t1+" "+t2; 
    }

    public Object visit(False node, Object data){ 
        return "boolean";
    } 

    public Object visit(Formal node, Object data){ 
        if (node.t.s.equals("boolean")) {
            return this.format_type(node.t.s, node.is_array);
        } 
        if (node.t.s.equals("int")) {
            return this.format_type(node.t.s, node.is_array);
        }
        if (node.t.s.equals("String")) {
            return this.format_type(node.t.s, node.is_array);
        }
        for (String key : this.st.classes.keySet()){
            if (node.t.s.equals(key)){
                return this.format_type(node.t.s, node.is_array);
            }
        }
        
        System.out.println("Unknown Type, Type error: " + node.t.s + " is not a valid type");
        num_errors++;
            
        
        return "void";
    }

    public Object visit(FormalList node, Object data){ 
        Formal f=node.f;
        FormalList flist=node.flist;
        String t1 = (String) node.f.accept(this, data);
        String t2 = "";
        if (node.flist != null) {
            t2 = (String) node.flist.accept(this, data);
        }
        
        return t1+" "+t2; 
    }

    public Object visit(Identifier node, Object data){ 
        String s=node.s;  
        String result = this.type_lookup(data, s);

        return result; 
    }

    public Object visit(IdentifierExp node, Object data){ 
        String s=node.s;
        if (s.equals("this")){
            return (((String) data).split("$"))[0];
        }
        return this.type_lookup(data, s);

    }

    public Object visit(IdentifierType node, Object data){
        // not in miniC
        String s=node.s;

        return data; 
    }

    public Object visit(If node, Object data){ 
        Exp e=node.e;
        node.e.accept(this, data);
        if (node.if_block != null){
            node.if_block.accept(this,data);
        }
        if (node.elif_block != null){
             node.elif_block.accept(this,data);
        }
        if (node.else_block != null){
             node.else_block.accept(this,data);
        }

        return "void"; 
    }
    public Object visit(ElseIf node, Object data){
        
        node.e.accept(this,data);
        if (node.block != null){
            node.block.accept(this,data);
        }
        if (node.n != null){
             node.n.accept(this,data);
        }

        return data;
    }
    public Object visit(IntArrayType node, Object data){
        return "int[]"; 
    }

    public Object visit(IntegerLiteral node, Object data){ 

        return "int"; 
    }

    public Object visit(IntegerType node, Object data){ 
        return "int"; 
    }

    public Object visit(LessThan node, Object data){ 
        // not in miniC
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Comparison Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "boolean";
    }

    public Object visit(MainClass node, Object data){ 
        // not in miniC
        Identifier i=node.i;
        Statement s=node.s;
        node.i.accept(this, data);
        node.s.accept(this, data);

        return data; 
    }

    public Object visit(MethodDecl node, Object data){ 

        String dt = (String) data + ((( String)data).length() == 0 ? node.name.s : "$" + node.name.s);
        String returns = null;
        if (node.args != null){
          node.args.accept(this, dt);
        }
        if (node.vars != null){
          node.vars.accept(this, dt);
        }
        if (node.statements != null){
          node.statements.accept(this, dt);
        }
        if (node.returns != null){
          returns = (String) node.returns.accept(this, dt);
        }
        
        if (node.type.s.equals("void") && returns != null){
            System.out.println("Method Return Type error: method " + node.name.s + " has return type void, but contains return expression of type "+returns);
            num_errors++;
            return "void";
        }
        if (node.type.s.equals("void") && returns == null){
            return "void";
        }
        if (!returns.equals(this.format_type(node.type.s, node.is_array))) {
            System.out.println("Method Return Type error: " + returns + " != " + this.format_type(node.type.s, node.is_array)+" in method"+node.name.s);
            num_errors++;
        }

        return "void"; 
    }


    public Object visit(MethodDeclList node, Object data){ 
        MethodDecl m=node.m;
        MethodDeclList mlist=node.mlist;
        node.m.accept(this, data);
        if (node.mlist != null) {
            node.mlist.accept(this, data);
        }
        

        return "void"; 
    }   


    public Object visit(Minus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "int"; 
    }

    public Object visit(NewArray node, Object data){ 
        // not in miniC
        Exp e=node.e;
        node.e.accept(this, data);

        return data; 
    }


    public Object visit(NewObject node, Object data){ 
        // not in miniC
        if (!(node.i instanceof Call)){
            System.out.println("Unsupported new object creation format");
            return "void";
        }
        if (!(((Call) node.i).e1 instanceof IdentifierExp)){
            System.out.println("Unsupported new object creation format");
            return "void";
        }
        
        return ((IdentifierExp) ((Call) node.i).e1).s; 
    }
    public Object visit(ExpStatement node, Object data){ 
        node.e.accept(this, data);
        return "void"; 
    }

    public Object visit(Not node, Object data){ 
        String t = (String) node.e.accept(this, data);
        if (!t.equals("boolean")){
            System.out.println("Type error: boolean 'not' cannot be applied to type " + t);
            num_errors++;  
        }

        return "boolean"; 
    }


    public Object visit(Plus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;  
        }

        return "int"; 
    }


    public Object visit(Print node, Object data){ 
        Exp e=node.e;
        String t1 = (String) node.e.accept(this, data);
        if (!t1.equals("int")) {
            System.out.println("Print Type error: " + t1 + " is not a valid type for print");
            num_errors++;
        }

        return "void"; 
    }


    public Object visit(Program node, Object data){ 
        // not in miniC
        MainClass m=node.m;
        ClassDeclList c=node.c;
        node.m.accept(this, data);
        if (node.c != null){
            node.c.accept(this, data);
        }
        

        return data; 
    }


    public Object visit(StatementList node, Object data){ 
        Statement s=node.s;
        StatementList slist=node.slist;
        node.s.accept(this, data);
        if (node.slist != null){
            node.slist.accept(this, data);
        }
        

        return "void"; 
    }


    public Object visit(This node, Object data){ 
        // not in miniC
        return data; 
    }



    public Object visit(Times node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "int"; 
    }


    public Object visit(True node, Object data){ 
        return "boolean"; 
    }


    public Object visit(VarDecl node, Object data){ 
        
        if (node.t.s.equals("boolean")) {
            return this.format_type(node.t.s, node.is_array);
        } 
        if (node.t.s.equals("int")) {
            return this.format_type(node.t.s, node.is_array);
        }
        for (String key : this.st.classes.keySet()){
            if (node.t.s.equals(key)){
                return this.format_type(node.t.s, node.is_array);
            }
        }
      
        System.out.println("Unknown Type, Type error: " + node.t.s + " is not a valid type");
        num_errors++;
            
        
        return "void";

    }


    public Object visit(VarDeclList node, Object data){ 
        VarDecl v=node.v;
        VarDeclList vlist=node.vlist;
        node.v.accept(this, data);
        if (node.vlist != null) {
            node.vlist.accept(this, data);
        }
        return "void"; 
    }

    public Object visit(While node, Object data){ 
        // not in miniC
        Exp e=node.e;
        Statement s=node.s;
        node.e.accept(this, data);
        if (node.s != null){
            node.s.accept(this, data);
        }

        return data; 
    }
    public Object visit(Throw node, Object data){ 
        return null;
    }
    public Object visit(StringExp node, Object data){ 
        return "String";
    }
    public Object visit(Return node, Object data){ 
        //return ...
        return null;
    }
    public Object visit(Or node, Object data){
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("boolean") || !t2.equals("boolean")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "boolean"; 
    }
    public Object visit(Null node, Object data){ 
         return null; 
    }

    public Object visit(NotEquals node, Object data){ 
         Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "boolean"; 
    }

    public Object visit(Multiply node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "int"; 
    }

    public Object visit(Modulo node, Object data){ 
         Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "int"; 
    }

    public Object visit(LessThanOrEqual node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "boolean"; 
    }

    public Object visit(InstanceOf node, Object data){ 
        //a istanceof b
        return "boolean";
    }

    public Object visit(Inline node, Object data){ 
        //conditional ? if_true : if_false
        String t = (String) node.conditional.accept(this, data);
        if (!t.equals("boolean")){
            System.out.println("Type error: inline conditional must be boolean");
            num_errors++;
            return "void";
        }
        String t1 = (String) node.conditional.accept(this, data);
        String t2 = (String) node.conditional.accept(this, data);
        if (!t1.equals(t2)){
            System.out.println("Inline conditional returns must have the same type");
            num_errors++;
            return "void";
        }
        return t1;
    }
    
    public Object visit(GreaterThanOrEqual node, Object data){
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "boolean"; 
    }

    public Object visit(GreaterThan node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "boolean"; 
    }

    public Object visit(Exponent node, Object data){
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "int"; 
    }

    public Object visit(Equals node, Object data){
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "boolean"; 
    }

    public Object visit(Divide node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "int"; 
    }

    public Object visit(Continue node, Object data){
        //continue statement
        return null;
    }

    public Object visit(CharacterExp node, Object data){ 
         return null; 
    }

    public Object visit(Break node, Object data){
        return null;
    }

    public MethodDecl attr_method_obj(Attribute node, Object data){
        String attr = node.i.s;
        String t = (String) node.e.accept(this, data);
        return this.st.methods.get(t + "$" + attr);
    }
    public String attr_method_obj_path(Attribute node, Object data){
        String attr = node.i.s;
        String t = (String) node.e.accept(this, data);
        return t + "$" + attr;
    }

    public Object visit(Attribute node, Object data){ 
        String attr = node.i.s;
        String t = (String) node.e.accept(this, data);
        if (!this.st.methods.containsKey(t + "$" + attr) && !this.st.variables.containsKey(t + "$" + attr)){
            System.out.println("Object '"+t+"' has no attribute '"+attr+"'");
            num_errors++;
            return "void";
        }
        return this.st.typeName.get(t + "$" + attr);
    }
    public Object visit(InPlaceOp node, Object data){ 
        //node.i++ or node.i--
        //use node.is_increment to decide between the above
        return null;
    }
    public Object visit(For node, Object data){ 
        /*
        for (node.type node.i = node.e; node.e1; node.op){
             node.for_block
        }
        */
        node.type.accept(this, data);
        node.i.accept(this, data);
        node.e.accept(this, data);
        node.e1.accept(this, data);
        node.op.accept(this, data);
        if (node.for_block != null){
             node.for_block.accept(this, data);
        }
        return data; 
    }

}

