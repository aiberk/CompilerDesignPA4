import syntaxtree.*;
import java.util.HashMap;

//import org.omg.CORBA.SystemException;


import java.util.HashMap;

public class CodeGen_Visitor implements Visitor {

    private HashMap<String, String> labelMap = new HashMap<String, String>();
    private HashMap<String, String> varMap = new HashMap<String, String>();
    private HashMap<String, String> paramMap = new HashMap<String, String>();
    private String currClass = "";
    private String currMethod = "";
    private int labelNum = 0;
    private int stackOffset = 0;
    private int formalOffset = 0;
    private Visitor ppVisitor = new PP_Visitor();


    public Object visit(And node, Object data){ 
    
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq $1, %rdx\n"
        + "je "+ label1 + "\n"
        + "pushq $0"
        + "jmp "+label2
        + label1+":\n"  
        + "pushq %rax\n"
        + label2+":\n";  
    } 

    public Object visit(ArrayAssign node, Object data){ 
        String varName = currClass+"_"+currMethod+"_"+node.i.s;
        String location = (String) varMap.get(varName);
        String e1 = (String)node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e2 + e1
        + "popq %rcx\n"
        + "incq %rcx\n"
        + "movq "+location + ", %rax\n"
        + "popq %rdx\n"
        + "movq %rdx, (%rax, %rcx, 8)\n";
        
    } 

    public Object visit(ArrayLength node, Object data){ 
        // not in MiniC
        Exp e=node.e;
        node.e.accept(this, data);
        return "#Array Length not implemented\n"; 
    } 

    public Object visit(ArrayLookup node, Object data){ 
        String e1 = (String)node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rcx\n"
        + "incq %rcx\n"
        + "popq %rax\n"
        + "pushq (%rax, %rcx, 8)\n";
    } 

    public Object visit(Assign node, Object data){ 
       String varName = currClass+"_"+currMethod+"_"+node.i.s;
       String location = varMap.get(varName);
       if (node.e instanceof NewObject){
            if (((NewObject) node.e).i instanceof ArrayLookup){
                int n = Integer.parseInt(((IntegerLiteral) ((ArrayLookup) ((NewObject) node.e).i).e2).s);
                return "# "+node.accept(ppVisitor, 0) + "\n"
                + "movq $"+8*(n+1)+", %rdi\n"
                + "callq _malloc\n"
                + "movq %rax, "+location+"\n"
                + "movq $0, %rcx\n"
                + "movq $"+n+", %rdx\n"
                + "movq %rdx, (%rax, %rcx, 8)\n";
            }


            return "# New Object assign not implemented";
        }

        //node.i.accept(this, data);
        String expCode = (String) node.e.accept(this, data);

        String result = 
            
            expCode
            + "#"+node.accept(ppVisitor,0)+"\n"
            + "popq %rax\n"
            + "movq %rax, "+location+"\n";
        
        return result; 
    } 

    public Object visit(Block node, Object data){ 
        StatementList slist=node.slist;
        String result="";
        if (node.slist != null){    
            result = (String) node.slist.accept(this, data);
        }
        return result; 
    } 

    public Object visit(BooleanType node, Object data){ 
        // no code generated
        return "# no code for BooleanType\n";
    } 

    public Object visit(Call node, Object data){         
        Exp e1 = node.e1;
        Identifier i = node.i;
        ExpList e2=node.e2;
        if (e1.accept(ppVisitor,0).equals("System.out.println")){
            if (e2.elist != null){
                return "# sys out not supported for more than one argument, skipping...";
            }
            return (String) e2.e.accept(this, data)
            + "# " + node.accept(ppVisitor,0) + "\n"
            + "popq %rsi\n"
            + "leaq	L_.str(%rip), %rdi\n"
            + "callq _printf\n";

        }
        if (node.e1 != null){
            node.e1.accept(this, data);
        }
        if (node.e2 != null){
            node.e2.accept(this, data);
        }

        return "# Call not implemented\n";
    } 

    public Object visit(ClassDecl node, Object data){ 
        // not in MiniC
        Identifier i = node.i;
        VarDeclList v=node.v;
        MethodDeclList m=node.m;

        String theLabel = i.s + "_"+(labelNum++);
        labelMap.put(i.s, theLabel);
        System.out.println(theLabel+":");
        currClass = i.s;

        
        node.i.accept(this, data);
        String v_c = "";
        if (node.v != null){
            v_c = (String) node.v.accept(this, data);
        }
        String m_c = "";
        if (node.m != null){
            m_c = (String) node.m.accept(this, data);
        }

        return v_c + "\n" + m_c;
    } 

    public Object visit(ClassDeclList node, Object data){ 
        // not in MiniC
        ClassDecl c=node.c;
        ClassDeclList clist=node.clist;
        String code = (String) node.c.accept(this, data);
        if (node.clist != null){
            code += "\n"+ (String) node.clist.accept(this, data);
        }

        return code;
    } 

    public Object visit(ExpGroup node, Object data){ 
        Exp e=node.e;
        String code = (String) node.e.accept(this, data);

        return code; 
    } 

    public Object visit(ExpList node, Object data){ 
        // not implemented yet, only need for Call
        Exp e=node.e;
        ExpList elist=node.elist;
        node.e.accept(this, data);
        if (node.elist != null){
            node.elist.accept(this, data);
        }
        return "#ExpList not implemented \n"; 
    }

    public Object visit(False node, Object data){ 
        return "#"+node.accept(ppVisitor,0)+"\n"
        + "pushq $0\n";
    } 

    public Object visit(Formal node, Object data){ 
        // find location of formal in stack
        Identifier i=node.i;

        formalOffset += 8;
        String varName = currClass+"_"+currMethod+"_"+node.i.s;
        String location = formalOffset + "(%rbp)";
        varMap.put(varName, location);

        //node.i.accept(this, data);
        //node.t.accept(this, data);

        return "# " + varName+"->"+location+"\n"; 
    }

    public Object visit(FormalList node, Object data){ 
        // find locations in stack for all formals, return no code
        Formal f=node.f;
        FormalList flist=node.flist;
        String result="";
        result = (String) node.f.accept(this, data);
        if (node.flist != null) {
            result+="\n"+ (String) node.flist.accept(this, data);
        }
        
        

        return "# "+result+"\n"; 
    }

    public Object visit(Identifier node, Object data){ 
        // no code generated for identifiers
        String s=node.s;  

        return "#no code for identifiers\n"; 
    }

    public Object visit(IdentifierExp node, Object data){ 
        // lookup location of identifier in stack
        // push that value onto the stack
        String s=node.s;
        // lookup the location of the identifier
        String varName = currClass+"_"+currMethod+"_"+s;
        String location = varMap.get(varName);
        // push it into the stack
        String result = 
            "#"+node.accept(ppVisitor,0)+"\n"
            + "pushq "+location+" #"
            + "  "+node.accept(ppVisitor, 0)
            +"\n";

        return result ; 
    }

    public Object visit(IdentifierType node, Object data){ 
        // not in MiniC
        String s=node.s;

        return data; 
    }

    public Object visit(If node, Object data){ 
        // not implemented yet
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

        return "# If not implemented yet\n"; 
    }

    public Object visit(ElseIf node, Object data){
        
        node.e.accept(this,data);
        if (node.block != null){
            node.block.accept(this,data);
        }
        if (node.n != null){
             node.n.accept(this,data);
        }

        return "# elseif not implemented yet\n";
    }

    public Object visit(IntArrayType node, Object data){ 
        // not in MiniC
        return "# not in MiniC\n"; 
    }

    public Object visit(IntegerLiteral node, Object data){ 
        // push the constant into the stack
        String i=node.s;

        String result = 
        "#"+node.accept(ppVisitor,0)+"\n"
        + "pushq $" + i+"\n";

        return result; 
    }

    public Object visit(IntegerType node, Object data){ 
        // no code generated
        return "#no code generated for Type decls\n"; 
    }

    public Object visit(LessThan node, Object data){ 
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq %rax, %rdx\n"
        + "jl "+ label1 + "\n"
        + "pushq $0"
        + "jmp "+label2
        + label1+":\n"  
        + "pushq $1\n"
        + label2+":\n";
    }

    public Object visit(MainClass node, Object data){ 
        // not in MiniC
        Identifier i=node.i;
        Statement s=node.s;
        labelMap.put(i.s, "_main");
        System.out.println("_main:");

        node.i.accept(this, data);
        node.s.accept(this, data);

        return "# not implemented\n"; 
    }

    public Object visit(MethodDecl node, Object data){ 
        // generate code for a function declaration

        String prologue="";
        String statementCode = "";
        String expressionCode = "";
        String epilogue = "";

        String formals="# formals\n";
        String locals="#locals\n";



        formalOffset = 8;
        String theLabel = node.name.s ; //labelMap.get(currClass) + i.s;
        labelMap.put(currClass + node.name.s, theLabel);

        currMethod = node.name.s;  // set "global variable"
        stackOffset = 0;

        

        //node.t.accept(this, data);
        //node.i.accept(this, data);

        // assign locations for formals
        if (node.args != null){
            formals += (String) node.args.accept(this, data);
        }

        

        // assign locations for local variables
        if (node.vars != null){
            locals += (String) node.vars.accept(this, data);
        }

        if (node.statements != null){
            statementCode = (String) node.statements.accept(this, data);
        }

        if (node.returns != null){
            expressionCode = (String) node.returns.accept(this, data);
        }

        int stackChange = - stackOffset;

        prologue = 
        ".section	__TEXT,__text,regular,pure_instructions\n"
        + ".build_version macos, 13, 0	sdk_version 13, 0\n"
        +".globl "+"_"+theLabel+"\n"
        + ".p2align	4, 0x90\n"
        +"_"+theLabel+":\n"
        +formals
        + "# prologue\n"
        + "pushq %rbp\n"
        + "movq %rsp, %rbp\n"
        + locals
        + "#make space for locals on stack\n"
        + "subq $"+stackChange+", %rsp\n";

        epilogue =
        "# epilogue\n"
        +   "popq %rax\n"
        +   "addq $"+stackChange+", %rsp\n"
        +   "movq %rbp, %rsp\n"
        +   "popq %rbp\n"
        // maybe insert code her to print out the return value
        +   "retq\n"
        + "L_.str:\n"
	    + ".asciz	\"%d\\n\"\n"

        + ".subsections_via_symbols\n";

        return 
          prologue 
          + statementCode 
          +"# calculate return value\n"
          + expressionCode
          +epilogue; 
    }


    public Object visit(MethodDeclList node, Object data){ 
        // not implemented
        MethodDecl m=node.m;
        MethodDeclList mlist=node.mlist;
        String result = "";

        result = (String) node.m.accept(this, data);
        if (node.mlist != null) {
            result += (String) node.mlist.accept(this, data);
        }
        

        return result; 
    }   


    public Object visit(Minus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);

        String minusCode = 
        
          e1Code
        + e2Code
        + "# minus:"+node.accept(ppVisitor,0)+"\n"
        +   "popq %rdx\n"
        +   "popq %rax\n"
        +   "subq %rdx, %rax\n"
        +   "pushq %rax\n";

        return minusCode; 
    }

    public Object visit(NewArray node, Object data){ 
        // not in MiniC
        Exp e=node.e;
        node.e.accept(this, data);

        return "# NewArray not implemented\n";
    }


    public Object visit(NewObject node, Object data){ 
        // not in MiniC
        node.i.accept(this, data);

        return "# NewObject not implemented\n"; 
    }

    public Object visit(ExpStatement node, Object data){ 
        return (String) node.e.accept(this, data);
    }


    public Object visit(Not node, Object data){ 
        // not in MiniC


        String e = (String) node.e.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e
        + "popq %rax\n"
        + "cmpq $1, %rax\n"
        + "je "+ label1 + "\n"
        + "pushq $1"
        + "jmp "+label2
        + label1+":\n"  
        + "pushq $0\n"
        + label2+":\n"; 
    }


    public Object visit(Plus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);

        String addCode = 
        
          e1Code
        + e2Code
        + "# plus:"+node.accept(ppVisitor,0)+"\n"
        +   "popq %rdx\n"
        +   "popq %rax\n"
        +   "addq %rdx, %rax\n"
        +   "pushq %rax\n";

        return addCode; 
    }


    public Object visit(Print node, Object data){ 
        Exp e=node.e;
        String expCode = (String) node.e.accept(this, data);

        String result = 
        
            expCode
        + "# "+node.accept(ppVisitor, 0) + "\n"
        +   "popq %rdi\n"
        +   "callq _print\n";

        return result;
    }


    public Object visit(Program node, Object data){ 
        // not in MiniC
        MainClass m=node.m;
        ClassDeclList c=node.c;
        node.m.accept(this, data);
        if (node.c != null){
            node.c.accept(this, data);
        }
        

        return "# Program not implemented\n"; 
    }


    public Object visit(StatementList node, Object data){ 
        Statement s=node.s;
        StatementList slist=node.slist;
        String result="";
        
        
        
        if (node.slist != null){
            result += (String) node.slist.accept(this, data);
        }
         result += "\n"+(String) node.s.accept(this, data);
        
        

        return result; 
    }


    public Object visit(This node, Object data){ 
        // not in MiniC
        return "# This not implemented\n";
    }



    public Object visit(Times node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);

        String timesCode = 
        
        e1Code
        + e2Code
        + "# times:"+node.accept(ppVisitor,0)+"\n"
        +   "popq %rdx\n"
        +   "popq %rax\n"
        +   "imulq %rdx, %rax\n"
        +   "pushq %rax\n";

        return timesCode; 
    }

    public Object visit(Multiply node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);

        String timesCode = 
        
        e1Code
        + e2Code
        + "# multiply:"+node.accept(ppVisitor,0)+"\n"
        +   "popq %rdx\n"
        +   "popq %rax\n"
        +   "imulq %rdx, %rax\n"
        +   "pushq %rax\n";

        return timesCode; 
    }


    public Object visit(True node, Object data){ 
        return "#"+node.accept(ppVisitor,0)+"\n"
        + "pushq $1\n";
    }


    public Object visit(VarDecl node, Object data){ 
        // find and store location of local variable
        // don't generate any code.

        stackOffset -= 8;
        String location = (stackOffset) + "(%rbp)";
        String varName = currClass+"_"+currMethod+"_"+node.i.s;
        varMap.put(varName, location);
 
        //node.t.accept(this, data);
        //node.i.accept(this, data);

        return "# "+varName+"->"+location+"\n";
    }


    public Object visit(VarDeclList node, Object data){ 
        VarDecl v=node.v;
        VarDeclList vlist=node.vlist;
        String vars="";
        vars += (String) node.v.accept(this, data);
        if (node.vlist != null) {
            vars += node.vlist.accept(this, data);
        }
        return vars; 
    }

    public Object visit(While node, Object data){ 
    
        String e = (String) node.e.accept(this, data);
        String scode ="";
        if (node.s != null){
            scode = (String) node.s.accept(this, data);
        }


        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e
        + "popq %rax\n"
        + "cmpq $1, %rax\n"
        + "je "+ label1 + "\n"
        + "jmp "+label2
        + label1+":\n"
        + "# body of while loop"
        + scode 
        + e
        + "popq %rax\n"
        + "cmpq $1, %rax\n"
        + "je "+ label1 + "\n"
        + "jmp "+label2+"\n"
        + label2+":\n";
    }
    public Object visit(Throw node, Object data){ 
        return "# throw not implemented";
    }
    public Object visit(StringExp node, Object data){ 
        return "# String not implemented";
    }
    public Object visit(Return node, Object data){ 
        //return ...
        return "# Return not implemented";
    }
    public Object visit(Or node, Object data){
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq $1, %rdx\n"
        + "je "+ label1 + "\n"
        + "pushq %rax"
        + "jmp "+label2
        + label1+":\n"  
        + "pushq $1\n"
        + label2+":\n";
    }
    public Object visit(Null node, Object data){ 
        return "#Null not implemented";
    }

    public Object visit(NotEquals node, Object data){ 
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rax\n"
        + "popq %rdx\n"
        + "cmpq %rdx, %rax\n"
        + "jne "+ label1 + "\n"
        + "pushq $0\n"
        + label1+":\n"  
        + "pushq $1\n"
        + "jmp "+label2+"\n"
        + label2+":\n";
    }

    

    public Object visit(Modulo node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        /*
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        */
        return "#Modulo not implemented";
    }

    public Object visit(LessThanOrEqual node, Object data){ 
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq %rax, %rdx\n"
        + "jle "+ label1 + "\n"
        + "pushq $0"
        + "jmp "+label2
        + label1+":\n"  
        + "pushq $1\n"
        + label2+":\n"; 
    }

    public Object visit(InstanceOf node, Object data){ 
    
        node.e1.accept(this,data);
        node.e2.accept(this,data);
    
        return "#InstanceOf not implemented";
    }

    public Object visit(Inline node, Object data){ 
        //conditional ? if_true : if_false
        String e1 = (String) node.conditional.accept(this, data);
        String e2 = (String) node.if_true.accept(this, data);
        String e3 = (String) node.if_false.accept(this, data);

        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2 + e3
        + "popq %rax\n"
        + "popq %rdx\n"
        + "popq %rbx\n"
        + "cmpq $1, %rbx\n"
        + "je "+ label1 + "\n"
        + "pushq %rax"
        + "jmp "+label2
        + label1+":\n"  
        + "pushq %rdx\n"
        + label2+":\n";
    }
    
    public Object visit(GreaterThanOrEqual node, Object data){
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq %rax, %rdx\n"
        + "jge "+ label1 + "\n"
        + "pushq $0"
        + "jmp "+label2
        + label1+":\n"  
        + "pushq $1\n"
        + label2+":\n"; 
    }

    public Object visit(GreaterThan node, Object data){ 
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq $1, %rdx\n"
        + "jg "+ label1 + "\n"
        + "pushq $0"
        + "jmp "+label2
        + label1+":\n"  
        + "pushq $1\n"
        + label2+":\n";
    }

    public Object visit(Exponent node, Object data){
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return "#Exponent not implemented";
    }

    public Object visit(Equals node, Object data){
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rax\n"
        + "popq %rdx\n"
        + "cmpq %rdx, %rax\n"
        + "je "+ label1 + "\n"
        + "pushq $0\n"
        + label1+":\n"  
        + "pushq $1\n"
        + "jmp "+label2+"\n"
        + label2+":\n";
    }

    public Object visit(Divide node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);

        String timesCode = 
        
        e1Code
        + e2Code
        + "# times:"+node.accept(ppVisitor,0)+"\n"
        +   "popq %rdx\n"
        +   "popq %rax\n"
        +   "idivq %rdx, %rax\n"
        +   "pushq %rax\n";

        return timesCode; 
    }

    public Object visit(Continue node, Object data){
        //continue statement
        return "#Continue not implemented";
    }

    public Object visit(CharacterExp node, Object data){ 
         return "#CharacterExp not implemented";
    }

    public Object visit(Break node, Object data){
        return "#Break not implemented";
    }

    public Object visit(Attribute node, Object data){ 
        if (node.i.s.equals("length")){
            return "# " + node.accept(ppVisitor,0)+"\n"
            + (String) node.e.accept(this, data) + "\n"
            + "popq %rax\n"
            + "movq $0, %rcx\n"
            + "pushq (%rax,%rcx, 8)\n";
        }
        return "# Attribute not implemented \n";
    }
    public Object visit(InPlaceOp node, Object data){ 
        //node.i++ or node.i--
        //use node.is_increment to decide between the above
        return "# InPlaceOp not implemented";
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
        return "# For not implemented";
    }

}