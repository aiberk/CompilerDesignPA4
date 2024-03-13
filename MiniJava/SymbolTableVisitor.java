import syntaxtree.*;
import java.util.HashMap;

/*
 * SymbolTableVisitor class
 * This is the visitor that will be used to build the symbol table.
 * It has the method to traverse the syntax tree for each node type,
 * For each declaration it will add the appropriate information to the symbol table.
 * It will use prefixes for the identifiers to handle overloading.
 * Inside a class, the prefix will be the class name.
 * Inside a method, the prefix will be the method name.
 * The prefixes will be separated by a dollar sign, e.g. MainClass$main$var1
 * The symbol table is in instance variable and visible to all methods in the class.
 * 
 * The data parameter will be the current prefix, initially the empty string ""
 */
public class SymbolTableVisitor implements Visitor {

  public SymbolTable symbolTable = new SymbolTable();

  public static String format_type(String tname, boolean is_array){
    return tname + (is_array ? "[]" : "");
  }

  public Object visit(And node, Object data){ 
    Exp e1=node.e1;
    Exp e2=node.e2;
    node.e1.accept(this, data);
    node.e2.accept(this, data);
    return data; 
  } 

  public Object visit(ArrayAssign node, Object data){ 
    Identifier i = node.i;
    Exp e1=node.e1;
    Exp e2=node.e2;
    node.i.accept(this,data);
    node.e1.accept(this, data);
    node.e2.accept(this, data);
    return data; 
  } 

  public Object visit(ArrayLength node, Object data){ 
    Exp e=node.e;
    node.e.accept(this, data);
    return data; 
  } 

  public Object visit(ArrayLookup node, Object data){ 
    Exp e1=node.e1;
    Exp e2=node.e2;
    node.e1.accept(this, data);
    node.e2.accept(this, data);
    return data; 
  } 

  public Object visit(Assign node, Object data){ 
    node.i.accept(this, data);
    node.e.accept(this, data);
    return data; 
  } 

  public Object visit(Block node, Object data){ 
    StatementList slist=node.slist;
    node.slist.accept(this, data);
    return data; 
  } 

  public Object visit(BooleanType node, Object data){ 
    return data;
  } 

  public Object visit(Call node, Object data){ 
    Exp e1 = node.e1;
    Identifier i = node.i;
    ExpList e2=node.e2;
    node.e1.accept(this, data);
    //node.i.accept(this, data);
    if (node.e2 != null){
      node.e2.accept(this, data);
    }


    return data;
  } 
  
  public Object visit(ClassDecl node, Object data){ 
    String dt = (String) data + ((( String)data).length() == 0 ? node.i.s : "$" + node.i.s);
    symbolTable.classes.put(dt, node);
    node.i.accept(this, dt);
    if (node.v != null){
        node.v.accept(this, dt);
    }
    if (node.m != null){
        node.m.accept(this, dt);
    }

    return data;
  } 
  
  public Object visit(ClassDeclList node, Object data){ 
    node.c.accept(this, data);
    if (node.clist != null){
      node.clist.accept(this, data);
    }

    return data;
  } 
  
  public Object visit(ExpGroup node, Object data){ 
    Exp e=node.e;
    node.e.accept(this, data);

    return data; 
  } 

  public Object visit(ExpList node, Object data){ 
    node.e.accept(this, data);
    if (node.elist != null){
      node.elist.accept(this, data);
    }

    return data; 
  }

  public Object visit(False node, Object data){ 
    return data;
} 

    public Object visit(Formal node, Object data){ 
        String dt = (String) data + ((( String)data).length() == 0 ? node.i.s : "$" + node.i.s);
        node.t.accept(this, dt);
        node.i.accept(this, dt);
        symbolTable.signatures.put(dt, node);
        symbolTable.typeName.put(dt, this.format_type(node.t.s, node.is_array));
        return data;
    }

    public Object visit(FormalList node, Object data){ 
        node.f.accept(this, data);
        if (node.flist != null) {
          node.flist.accept(this, data);
        }

        return data;
    }

    public Object visit(Identifier node, Object data){ 
        String s=node.s;  

        return data; 
    }

    public Object visit(IdentifierExp node, Object data){ 
        String s=node.s;

        return data; 
    }

    public Object visit(IdentifierType node, Object data){ 
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

        return data; 
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
        return data; 
    }

    public Object visit(IntegerLiteral node, Object data){ 
        String s=node.s;

        return data; 
    }

    public Object visit(IntegerType node, Object data){ 
        return data; 
    }

    public Object visit(LessThan node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        return data;
    }

    public Object visit(MainClass node, Object data){ 
        node.i.accept(this, data);
        node.s.accept(this, data);

        return data; 
    }

    public Object visit(MethodDecl node, Object data){ 
        String dt = (String) data + ((( String)data).length() == 0 ? node.name.s : "$" + node.name.s);
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
          node.returns.accept(this, dt);
        }
        symbolTable.typeName.put(dt, this.format_type(node.type.s, node.is_array));
        symbolTable.methods.put(dt, node);
        return data; 
    }


    public Object visit(MethodDeclList node, Object data){ 
        MethodDecl m=node.m;
        MethodDeclList mlist=node.mlist;
        node.m.accept(this, data);
        if (node.mlist != null) {
            node.mlist.accept(this, data);
        }

        return data; 
    }   


    public Object visit(Minus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        return data; 
    }

    public Object visit(NewArray node, Object data){ 
        Exp e=node.e;
        node.e.accept(this, data);

        return data; 
    }


    public Object visit(NewObject node, Object data){ 
        node.i.accept(this, data);

        return data; 
    }


    public Object visit(Not node, Object data){ 
        Exp e=node.e;
        node.e.accept(this, data);

        return data; 
    }


    public Object visit(Plus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        return data; 
    }


    public Object visit(Print node, Object data){ 
        Exp e=node.e;
        node.e.accept(this, data);

        return data; 
    }


    public Object visit(Program node, Object data){ 
        MainClass m=node.m;
        ClassDeclList c=node.c;
        node.m.accept(this, data);
        node.c.accept(this, data);

        return data; 
    }


    public Object visit(StatementList node, Object data){ 
        node.s.accept(this, data);
        if (node.slist != null){
          node.slist.accept(this, data);
        }

        return data; 
    }


    public Object visit(This node, Object data){ 
        return data; 
    }



    public Object visit(Times node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        return data; 
    }


    public Object visit(True node, Object data){ 
        return data; 
    }

    public static String getTypeName(Object node){
      if (node instanceof IdentifierType){
        return ((IdentifierType) node).s;
      }
      else if (node instanceof IntegerType){
        return "int";
      }
      else if (node instanceof BooleanType){
        return "boolean";
      }
      else if (node instanceof IntArrayType){
        return "int[]";
      }
      else {
        return "void";
      }
    }

    public Object visit(VarDecl node, Object data){ 
        String dt = (String) data + ((( String)data).length() == 0 ? node.i.s : "$" + node.i.s);
        node.t.accept(this, dt);
        node.i.accept(this, dt);
        symbolTable.variables.put(dt, node);
        symbolTable.typeName.put(dt, this.format_type(node.t.s, node.is_array));
        return data;
    }



  public Object visit(VarDeclList node, Object data){ 
    node.v.accept(this, data);
    if (node.vlist != null) {
      node.vlist.accept(this, data);
    }

    return data; 
  }

  public Object visit(While node, Object data){ 
    Exp e=node.e;
    Statement s=node.s;
    node.e.accept(this, data);
    if (node.s != null){
      node.s.accept(this, data);
    }

    return data; 
  }

  public Object visit(InPlaceOp node, Object data){ 
    //node.i++ or node.i--
    //use node.is_increment to decide between the above
    node.i.accept(this, data);
    return data; 
  }
  public Object visit(ExpStatement node, Object data){ 
      node.e.accept(this, data);
      return data; 
  }
  public Object visit(Attribute node, Object data){ 
      node.e.accept(this, data);
      node.i.accept(this, data);
      return data; 
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
    public Object visit(Continue node, Object data){
        //continue statement
        return data; 
    }

    public Object visit(CharacterExp node, Object data){ 
        return data; 
    }

    public Object visit(Break node, Object data){
        //break statement
        return data; 
    }
    public Object visit(Divide node, Object data){ 
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }
    public Object visit(Modulo node, Object data){ 
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }

    public Object visit(LessThanOrEqual node, Object data){ 
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }

    public Object visit(InstanceOf node, Object data){ 
        //a istanceof b
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }

    public Object visit(Inline node, Object data){ 
        //conditional ? if_true : if_false
        node.conditional.accept(this, data);
        node.if_true.accept(this, data);
        node.if_false.accept(this, data);
        return data; 
    }
    
    public Object visit(GreaterThanOrEqual node, Object data){
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }

    public Object visit(GreaterThan node, Object data){ 
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }

    public Object visit(Exponent node, Object data){
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }

    public Object visit(Equals node, Object data){
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }
    public Object visit(Throw node, Object data){ 
        //throw ...
        node.e.accept(this, data);
        return data; 
    }
    public Object visit(StringExp node, Object data){ 
        return data; 
    }
    public Object visit(Return node, Object data){ 
        node.e.accept(this, data);
        return data; 
    }
    public Object visit(Or node, Object data){
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }
    public Object visit(Null node, Object data){ 
        return data; 
    }

    public Object visit(NotEquals node, Object data){ 
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }

    public Object visit(Multiply node, Object data){ 
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    }
}



