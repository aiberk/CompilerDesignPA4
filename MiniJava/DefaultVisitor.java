import syntaxtree.*;
import java.util.HashMap;

/*
 * DefaultVisitor class
 * This is the default visitor that will be used to traverse the syntax tree.
 * It is used to build the symbol table and to check for type errors.
 * It is also used to generate the intermediate code.
 * It has the method to traverse the syntax tree for each node type,
 * but it does not do anything.
 */
public class DefaultVisitor implements Visitor {

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
        Identifier i=node.i;
        Exp e=node.e;
        node.i.accept(this, data);
        node.e.accept(this, data);
        return data; 
    } 

    public Object visit(Block node, Object data){ 
        StatementList slist=node.slist;
        if (node.slist != null){    
            node.slist.accept(this, data);
        }
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
        node.i.accept(this, data);
        if (node.e2 != null){
            node.e2.accept(this, data);
        }

        return data;
    } 

    public Object visit(ClassDecl node, Object data){ 
        Identifier i = node.i;
        VarDeclList v=node.v;
        MethodDeclList m=node.m;
        node.i.accept(this, data);
        if (node.v != null){
            node.v.accept(this, data);
        }
        if (node.m != null){
            node.m.accept(this, data);
        }
        return data;
    } 

    public Object visit(ClassDeclList node, Object data){ 
        ClassDecl c=node.c;
        ClassDeclList clist=node.clist;
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
        Exp e=node.e;
        ExpList elist=node.elist;
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
        Identifier i=node.i;
        Type t=node.t;
        node.i.accept(this, data);
        node.t.accept(this, data);

        return data; 
    }

    public Object visit(FormalList node, Object data){ 
        Formal f=node.f;
        FormalList flist=node.flist;
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
        int i=node.i;

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
        Identifier i=node.i;
        Statement s=node.s;
        node.i.accept(this, data);
        node.s.accept(this, data);

        return data; 
    }

    public Object visit(MethodDecl node, Object data){ 
        Type t=node.t;
        Identifier i=node.i;
        FormalList f=node.f;
        VarDeclList v=node.v;
        StatementList s=node.s;
        Exp e=node.e;
        node.t.accept(this, data);
        node.i.accept(this, data);
        if (node.f != null){
            node.f.accept(this, data);
        }
        if (node.v != null){
            node.v.accept(this, data);
        }
        if (node.s != null){
            node.s.accept(this, data);
        }
        node.e.accept(this, data);

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
        Identifier i=node.i;
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


    public Object visit(VarDecl node, Object data){ 
        Type t=node.t;
        Identifier i=node.i;
        node.t.accept(this, data);
        node.i.accept(this, data);

        return data;
    }


    public Object visit(VarDeclList node, Object data){ 
        VarDecl v=node.v;
        VarDeclList vlist=node.vlist;
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

    public Object visit(Throw node, Object data){ 
        
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

    public Object visit(Divide node, Object data){ 
        node.e1.accept(this, data);
        node.e2.accept(this, data);
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

    public Object visit(Attribute node, Object data){ 
        node.e.accept(this, data);
        node.i.accept(this, data);
        return data; 
    }

    public Object visit(ExpStatement node, Object data){ 
        node.e.accept(this, data);
        return data; 
    }
    public Object visit(InPlaceOp node, Object data){ 
        //node.i++ or node.i--
        //use node.is_increment to decide between the above
        node.i.accept(this, data);
        return data; 
    }
    public Object visit(For node, Object data){ 
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

