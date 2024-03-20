/*
  Pretty Printer Demo: https://www.loom.com/share/9bc62050e4754f328c372f34824a2795?sid=06fac518-4bcc-41bd-93dd-64bfb0323a70
  Pretty Printer Creation Explantion: https://www.loom.com/share/2214772f2527470fadd4cc7f532db369?sid=d60e4834-6f6e-49cd-8bfe-dbe306b289ac
  Teammembers: James Petullo and Aby Iberkleid
*/

import syntaxtree.*;


 public class PP_Visitor implements Visitor
 {
 
   private String indentString(int indent) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4*indent; ++i) {
        sb.append(' ');
        }
        return sb.toString();
   }

   public Object visit(Program node, Object data){
     return null;
   }

   public Object visit(MainClass node, Object data){
     return null;
   }
   public Object visit(ClassDecl node, Object data){
     int indent = (int) data;
     String ind = indentString(indent);
     String v = "";
     if (node.v != null){
          v = (String) node.v.accept(this, indent+1);
     }
     return ind + "class "+node.i.s + "\n"+ind + "{\n" + v + node.m.accept(this, indent+1)+"\n"+ind + "}";
   }
   public Object visit(VarDecl node, Object data){
        int indent = (int) data; 
        String t = (String) node.t.accept(this,indent);
        String i = (String) node.i.accept(this,indent);
        return indentString(indent)+t + (node.is_array ? "[]" : "")+" "+i +";\n";
   }
   public Object visit(MethodDecl node, Object data){
        // 
        int indent = (int) data; 
        String ind = indentString(indent);
        String type = (String) node.type.accept(this,indent) +  (node.is_array ? "[]" : "");
        String name = (String) node.name.accept(this,indent);
        String args = "";
        if (node.args!=null){
            args = (String) node.args.accept(this,indent);
        }
        String vars = "";
        if (node.vars!=null){
            vars = (String) node.vars.accept(this,indent+1);
        }
        String statementList = "";
        if (node.statements!=null){
            statementList = (String) node.statements.accept(this,indent+1);
        }

        String returns = "";
        if (node.returns != null){
             returns = "\n"+indentString(indent + 1) + "return "+(String) node.returns.accept(this,indent) +";\n";
        }
     
        return ind + "public " + type + " "+name+"("+args+"){\n"+vars + statementList + ind + returns + "\n"+ind+"}";
        
   }
   public Object visit(Formal node, Object data){
        // 
        int indent = (int) data; 
        String type = (String) node.t.accept(this,indent);
        String id = (String) node.i.accept(this,indent);
        return type+(node.is_array ? "[]" : "")+" "+id;
   }

   public Object visit(IntArrayType node, Object data){
     return null;
   }
   public Object visit(IntegerType node, Object data){
        // 
        int indent = (int) data; 
        return "int ";
   }
   public Object visit(BooleanType node, Object data){
        // 
        int indent = (int) data; 
        return "bool ";
   }
   public Object visit(IdentifierType node, Object data){
     return null;
   }
   public Object visit(Block node, Object data){
        // 
        int indent = (int) data; 
        String result="";
        if (node.slist!=null){
            result = (String) node.slist.accept(this,indent+1);
        }
        
        return indentString(indent)+"{\n"+
               result+
               indentString(indent)+"}\n";
   }
   public Object visit(If node, Object data){
        // 
        int indent = (int) data; 
        String ind = indentString(indent);
        String expr = (String)node.e.accept(this,indent+1);
        String if_block = "";
        if (node.if_block != null){
             if_block = (String) node.if_block.accept(this,indent+1);
        }
        String elif_block = "";
        if (node.elif_block != null){
             elif_block = (String) node.elif_block.accept(this,indent);
        }
        String else_block = "";
        if (node.else_block != null){
             else_block = ind + "else{\n" + (String) node.else_block.accept(this,indent+1) + "\n" + ind + "}\n";
        }
          return ind + "if ("+expr + "){\n" + if_block + "\n" + ind + "}" + elif_block + "\n" + else_block; 
   }
   public Object visit(While node, Object data){
     int indent = (int) data; 
     String ind = indentString(indent);
     String expr = (String)node.e.accept(this,indent+1);
     String b = "";
     if (node.s != null){
          b = (String)node.s.accept(this,indent+1);
     }
     return ind + "while ("+expr + "){\n" + b + "\n" + ind+"}\n";

   }
   public Object visit(Print node, Object data){
        // 
        int indent = (int) data; 
        String e = (String) node.e.accept(this,indent);
        //--indent;
        return indentString(indent) + "print("+e+");\n";
   }
   public Object visit(Assign node, Object data){
        // 
        int indent = (int) data; 
        String i = (String) node.i.accept(this,indent);
        String e = (String) node.e.accept(this,indent);
        //--indent;
        return indentString(indent)+i+" = "+e+";\n";
   }

   
   public Object visit(ArrayAssign node, Object data){ 
     int indent = (int) data; 
     String i = (String) node.i.accept(this,indent);
     String e1 = (String) node.e1.accept(this,indent);
     String e2 = (String) node.e2.accept(this,indent);
     return  indentString(indent) + i + "[" + e1 + "] = " + e2 + ";\n";
   } 


   public Object visit(And node, Object data){
     int indent = (int) data; 
     String e1 = (String) node.e1.accept(this,indent);
     String e2 = (String) node.e2.accept(this,indent);
     return  e1 + "&&" + e2;
   }

   public Object visit(LessThan node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" < "+e2;
   }
   public Object visit(Plus node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" + "+e2;
   }
   public Object visit(Minus node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" - "+e2;
   }
   public Object visit(Times node, Object data){
        // 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" * "+e2;
   }
   public Object visit(ArrayLookup node, Object data){
     String e1 = (String) node.e1.accept(this,data);
     String e2 = (String) node.e2.accept(this,data);
     return e1 +"[" + e2 + "]";
   }
   public Object visit(ArrayLength node, Object data){
     return null;
   }
   public Object visit(Call node, Object data){
        // 
        int indent = (int) data; 
        //String e1 = (String) node.e1.accept(this,indent);
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = "";
        if (node.e2 != null){
             e2 = (String) node.e2.accept(this,indent);
        }

        return e1+"("+e2+")";
   }
   public Object visit(IntegerLiteral node, Object data){
        int indent = (int) data; 
        return node.s;
   }
   public Object visit(True node, Object data){
        // 
        int indent = (int) data; 
        //--indent;
        return "true";
   }
   public Object visit(False node, Object data){
        // 
        int indent = (int) data; 
        //--indent;
        return "false";
   }
   public Object visit(IdentifierExp node, Object data){

        int indent = (int) data; 
        String s = node.s;
        //--indent;
        return s;
   }
   public Object visit(This node, Object data){
     return null;
   }
   public Object visit(NewArray node, Object data){
     return null;
   }
   public Object visit(NewObject node, Object data){
     String i = (String) node.i.accept(this, data);
     return "new "+i;
   }

   public Object visit(Not node, Object data){
     String e = (String) node.e.accept(this, data);
     return "!"+e;
   }
   public Object visit(Identifier node, Object data){

        int indent = (int) data; 
        String s = node.s;
        //--indent;
        return s;
   }

   public Object visit(ExpGroup node, Object data){
     String e="";
     if (node.e!=null){
         e = (String) node.e.accept(this,data);
     }
     return "("+e+")";
   }

   public Object visit(ClassDeclList node, Object data){
        int indent = (int) data; 
     String c = (String) node.c.accept(this,indent);
        if (node.clist!=null){
            String c1 = (String) node.clist.accept(this,indent);
            c = c1 + "\n"+c;
        }

        return c;
   }
   public Object visit(ExpList node, Object data){
        // 
        int indent = (int) data; 
        String e = (String) node.e.accept(this,indent);
        if (node.elist!=null){
            String e1 = (String) node.elist.accept(this,indent);
            e = e1 + ", "+e;
        }

        return e;
   }
   public Object visit(ElseIf node, Object data){
        // 
        int indent = (int) data; 
        String ind = indentString(indent);
        String e = (String) node.e.accept(this,indent);
        String block = "";
        if (node.block != null){
             block = (String) node.block.accept(this,indent + 1);
        }
        String sig = "else if ("+e+"){\n"+block + "\n"+ind+"}\n";
        if (node.n!=null){
            String sig1 = (String) node.n.accept(this,indent);
            sig = sig1 + ind+sig;
        }

        return sig;
   }

   public Object visit(FormalList node, Object data){
        // 
        int indent = (int) data; 
        String f = (String) node.f.accept(this,indent);
        if (node.flist!=null){
            String f1 = (String) node.flist.accept(this,indent);
            f = f1 +", "+f;
        }
        //--indent;
        return f;
   }
   public Object visit(MethodDeclList node, Object data){
        // 
        int indent = (int) data; 
        String m = (String) node.m.accept(this,indent);
        if (node.mlist!=null){
            String m2 = (String) node.mlist.accept(this,indent);
            m = m2 +"\n"+m; 
        }
        return m;
   }
   public Object visit(StatementList node, Object data){
        // 
        int indent = (int) data; 
        String result="";
        
        if (node.slist!=null){
            String s = (String) node.slist.accept(this,indent);
            result = result+s;
        }
        String t = (String) node.s.accept(this,indent);
        //--indent;
        result = result+t;
        return result;
   }
   public Object visit(VarDeclList node, Object data){
        // 
        int indent = (int) data; 
        String v = (String) node.v.accept(this,indent);
        
        if (node.vlist!=null){
            String vlist = (String) node.vlist.accept(this,indent);
            v = vlist + v;
        }
        
        return v;
   }
   public Object visit(Throw node, Object data){ 
        //throw node.e
        return null;
    }
    public Object visit(StringExp node, Object data){
        //"node.s" 
        return node.s;
    }
    public Object visit(Return node, Object data){ 
        //return ...
        return null;
    }
    public Object visit(Or node, Object data){
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" || "+e2;
    }
    public Object visit(Null node, Object data){ 
         return "null";
    }

    public Object visit(NotEquals node, Object data){ 
         int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" != "+e2;
    }

    public Object visit(Multiply node, Object data){ 
         int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" * "+e2;
    }

    public Object visit(Modulo node, Object data){ 
         int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" % "+e2; 
    }

    public Object visit(LessThanOrEqual node, Object data){ 
         int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" <= "+e2;
    }

    public Object visit(InstanceOf node, Object data){ 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" instanceof "+e2;
    }

    public Object visit(Inline node, Object data){ 
        //node.conditional ? node.if_true : node.if_false
        int indent = (int) data; 
        String c = (String) node.conditional.accept(this,indent);
        String if_true = (String) node.if_true.accept(this,indent);
        String if_false = (String) node.if_true.accept(this,indent);
        return c + " ? " + if_true + " : "+ if_false;
    }
    
    public Object visit(GreaterThanOrEqual node, Object data){
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" >= "+e2;
    }

    public Object visit(GreaterThan node, Object data){ 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" > "+e2;
    }

    public Object visit(Exponent node, Object data){
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+"**"+e2;
    }

    public Object visit(Equals node, Object data){
         int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" == "+e2;
    }

    public Object visit(Divide node, Object data){ 
        int indent = (int) data; 
        String e1 = (String) node.e1.accept(this,indent);
        String e2 = (String) node.e2.accept(this,indent);
        return e1+" / "+e2;
    }

    public Object visit(Continue node, Object data){
        //continue statement
        int indent = (int) data;
        return indentString(indent) + "continue;\n";
    }

    public Object visit(CharacterExp node, Object data){ 
         return null; 
    }

    public Object visit(Break node, Object data){
        int indent = (int) data;
        return indentString(indent) + "break;\n";
    }

    public Object visit(Attribute node, Object data){ 
         int indent = (int) data;
         String i = (String) node.i.accept(this,indent);
        String e = (String) node.e.accept(this,indent);
        return e + "."+i;
    }
    public Object visit(InPlaceOp node, Object data){ 
        //node.i++ or node.i--
        //use node.is_increment to decide between the above
        int indent = (int) data;
        String i = (String) node.i.accept(this,indent);
        return i +(node.is_increment ? "++" : "--");
    }

    
    public Object visit(ExpStatement node, Object data){ 
        //node.i++ or node.i--
        //use node.is_increment to decide between the above
        int indent = (int) data;
        String i = indentString(indent) + (String) node.e.accept(this,indent) + ";\n";
        return i;
    }
    public Object visit(For node, Object data){ 
        /*
        for (node.type node.i = node.e; node.e1; node.op){
             node.for_block
        }
        */
        int indent = (int) data;
        String ind = indentString(indent);
        String type = (String) node.type.accept(this,indent);
        String i = (String) node.i.accept(this,indent);
        String e = (String) node.e.accept(this,indent);
        String e1 = (String) node.e1.accept(this,indent);
        String op = (String) node.op.accept(this,indent);
          String for_block = "";
          if (node.for_block != null){
               for_block = (String) node.for_block.accept(this,indent + 1);
          }

          return ind+"for ("+type + " "+i +" = "+e +"; "+e1+"; "+op+"){\n"+op+"\n"+ind+"}";
        
    }
}

 

 