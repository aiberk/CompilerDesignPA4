
/*
 * Demo: https://www.loom.com/share/a777a1b0cf46424096070663e5c3e6fe?sid=5c529812-18d4-4e9e-846f-952b4e84ac75
 * Explanation: https://www.loom.com/share/2a1090d1a11040eb8f5a8a3ea705c2a4?sid=f9de8118-1fdf-4114-8987-56564954c6dc
 */
import syntaxtree.*;

public class C2Python_Visitor implements Visitor {

     private String indentString(int indent) {
          String space = " ";
          StringBuffer sb = new StringBuffer();
          for (int i = 0; i < 4 * indent; ++i) {
               sb.append(space);
          }
          return sb.toString();
     }

     public Object visit(Program node, Object data) {
          return null;
     }

     public Object visit(MainClass node, Object data) {
          return null;
     }

     public Object visit(ClassDecl node, Object data) {
          return null;
     }

     public Object visit(VarDecl node, Object data) {
          return null;
     }

     public Object visit(MethodDecl node, Object data) {
          int indent = (int) data;
          String type = (String) node.t.accept(this, indent);
          String id = (String) node.i.accept(this, indent);
          String formalList = "";
          if (node.f != null) {
               formalList = (String) node.f.accept(this, indent);
          }
          String varList = "";
          if (node.v != null) {
               varList = (String) node.v.accept(this, indent + 1);
          }
          String statementList = "";
          if (node.s != null) {
               statementList = (String) node.s.accept(this, indent + 1);
          }
          String expr = (String) node.e.accept(this, indent);
          String argument = formalList.substring(0, 1);
          return indentString(indent) + "def " + id + "(" + argument + ")" + ":\n" + statementList + "\n"
                    + indentString(indent + 1) + "return " + expr + "\n";

     }

     public Object visit(Formal node, Object data) {
          int indent = (int) data;
          String type = (String) node.t.accept(this, indent);
          String id = (String) node.i.accept(this, indent);
          return id + ":" + type;
     }

     public Object visit(IntArrayType node, Object data) {
          return null;
     }

     public Object visit(IntegerType node, Object data) {
          int indent = (int) data;
          return "int";
     }

     public Object visit(BooleanType node, Object data) {
          int indent = (int) data;
          return "bool";
     }

     public Object visit(IdentifierType node, Object data) {
          return null;
     }

     public Object visit(Block node, Object data) {
          int indent = (int) data;
          String result = "";
          if (node.slist != null) {
               result = (String) node.slist.accept(this, indent + 1);
          }

          return indentString(indent) + "\n" +
                    result +
                    indentString(indent) + "\n";
     }

     public Object visit(If node, Object data) {
          int indent = (int) data;
          String expr = (String) node.e.accept(this, indent + 1);
          String s1 = (String) node.s1.accept(this, indent + 1);
          String s2 = (String) node.s2.accept(this, indent + 1);
          return indentString(indent) +
                    "if " + expr + ":" +
                    s1 +
                    indentString(indent) + "else:" +
                    s2;
     }

     public Object visit(While node, Object data) {
          return null;
     }

     public Object visit(Print node, Object data) {
          int indent = (int) data;
          String e = (String) node.e.accept(this, indent);
          return indentString(indent) + "print(" + e + ")\n";
     }

     public Object visit(Assign node, Object data) {
          int indent = (int) data;
          String i = (String) node.i.accept(this, indent);
          String e = (String) node.e.accept(this, indent);
          return indentString(indent) + i + " = " + e + "\n";
     }

     public Object visit(ArrayAssign node, Object data) {
          return data;
     }

     public Object visit(And node, Object data) {
          return null;
     }

     public Object visit(LessThan node, Object data) {
          int indent = (int) data;
          String e1 = (String) node.e1.accept(this, indent);
          String e2 = (String) node.e2.accept(this, indent);
          return e1 + "<" + e2;
     }

     public Object visit(Plus node, Object data) {
          int indent = (int) data;
          String e1 = (String) node.e1.accept(this, indent);
          String e2 = (String) node.e2.accept(this, indent);
          return e1 + " + " + e2;
     }

     public Object visit(Minus node, Object data) {
          int indent = (int) data;
          String e1 = (String) node.e1.accept(this, indent);
          String e2 = (String) node.e2.accept(this, indent);
          return e1 + " - " + e2;
     }

     public Object visit(Times node, Object data) {
          int indent = (int) data;
          String e1 = (String) node.e1.accept(this, indent);
          String e2 = (String) node.e2.accept(this, indent);
          return e1 + " * " + e2;
     }

     public Object visit(ArrayLookup node, Object data) {
          return null;
     }

     public Object visit(ArrayLength node, Object data) {
          return null;
     }

     public Object visit(Call node, Object data) {
          int indent = (int) data;
          String i = (String) node.i.accept(this, indent);
          String e2 = (String) node.e2.accept(this, indent);
          return i + "(" + e2 + ")";
     }

     public Object visit(IntegerLiteral node, Object data) {
          int indent = (int) data;
          int i = node.i;
          return "" + i + "";
     }

     public Object visit(True node, Object data) {
          int indent = (int) data;
          return "True";
     }

     public Object visit(False node, Object data) {
          int indent = (int) data;
          return "False";
     }

     public Object visit(IdentifierExp node, Object data) {
          int indent = (int) data;
          String s = node.s;
          return "" + s + "";
     }

     public Object visit(This node, Object data) {
          return null;
     }

     public Object visit(NewArray node, Object data) {
          return null;
     }

     public Object visit(NewObject node, Object data) {
          return null;
     }

     public Object visit(Not node, Object data) {
          return null;
     }

     public Object visit(Identifier node, Object data) {
          int indent = (int) data;
          String s = node.s;
          return s;
     }

     public Object visit(ExpGroup node, Object data) {
          String e = "";
          if (node.e != null) {
               e = (String) node.e.accept(this, data);
          }
          return "(" + e + ")";
     }

     public Object visit(ClassDeclList node, Object data) {
          return null;
     }

     public Object visit(ExpList node, Object data) {
          int indent = (int) data;
          String e = (String) node.e.accept(this, indent);
          if (node.elist != null) {
               String e1 = (String) node.elist.accept(this, indent);
               e = e1 + "," + e;
          }
          return e;
     }

     public Object visit(FormalList node, Object data) {
          int indent = (int) data;
          String f = (String) node.f.accept(this, indent);
          if (node.flist != null) {
               String f1 = (String) node.flist.accept(this, indent);
               f = f1 + ", " + f;
          }

          return f;
     }

     public Object visit(MethodDeclList node, Object data) {
          int indent = (int) data;
          String m = (String) node.m.accept(this, indent);
          if (node.mlist != null) {
               String m2 = (String) node.mlist.accept(this, indent);
               m = m2 + "\n" + m;
          }
          return m;
     }

     public Object visit(StatementList node, Object data) {
          int indent = (int) data;
          String result = "";
          if (node.slist != null) {
               String s = (String) node.slist.accept(this, indent);
               result = result + s;
          }
          String t = (String) node.s.accept(this, indent);
          result = result + t;
          return result;
     }

     public Object visit(VarDeclList node, Object data) {
          int indent = (int) data;
          String v = (String) node.v.accept(this, indent);
          if (node.vlist != null) {
               String vlist = (String) node.vlist.accept(this, indent);
               v = vlist + v;
          }

          return v;
     }
}
