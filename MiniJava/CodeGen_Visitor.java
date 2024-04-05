import syntaxtree.*;
import java.util.HashMap;
import java.util.ArrayList;

//import org.omg.CORBA.SystemException;


import java.util.HashMap;

public class CodeGen_Visitor implements Visitor {

    private HashMap<String, String> labelMap = new HashMap<String, String>();
    private HashMap<String, String> varMap = new HashMap<String, String>();
    private HashMap<String, String> paramMap = new HashMap<String, String>();
    private String currClass = "";
    private String currMethod = "";
    private int labelNum = 0;
    private int stackOffset = -24;
    private int formalOffset = 0;
    private int numFormals = 0;
    private Visitor ppVisitor = new PP_Visitor();
    private String[] regFormals= //registers for first six formals...
        {"%rdi","%rsi","%rdx","%rcx","%r8","%r9"}; 


    public Object visit(And node, Object data){ 
    
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq $1, %rdx\n"
        + "je "+ label1 + "\n"
        + "pushq $0\n"
        + "jmp "+label2+"\n"
        + label1+":\n"  
        + "pushq %rax\n"
        + label2+":\n";  
    } 

    public Object visit(ArrayAssign node, Object data){ 
        String varName = currClass+"_"+currMethod+"_"+node.i.s;
        String location = (String) varMap.get(varName);
        String e1 = (String)node.e1.accept(this, data);
        //varName[e1] = e2
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
                if (((ArrayLookup) ((NewObject) node.e).i).e2 instanceof IntegerLiteral){
                    int n = Integer.parseInt(((IntegerLiteral) ((ArrayLookup) ((NewObject) node.e).i).e2).s);
                    return "# "+node.accept(ppVisitor, 0) + "\n"
                    + "movq $"+8*(n+1)+", %rdi\n"
                    + "callq _malloc\n"
                    + "movq %rax, "+location+"\n"
                    + "movq $0, %rcx\n"
                    + "movq $"+n+", %rdx\n"
                    + "movq %rdx, (%rax, %rcx, 8)\n";
                }
                return "# "+node.accept(ppVisitor, 0) + "\n"
                    + (String) ((ArrayLookup) ((NewObject) node.e).i).e2.accept(this, data)
                    + "popq %rax\n"
                    + "movq %rax, %rdx\n"
                    + "incq %rax\n"
                    + "imulq $8, %rax\n"
                    + "movq %rdx, %rdi\n"
                    + "callq _malloc\n"
                    + "movq %rax, "+location+"\n"
                    + "movq $0, %rcx\n"
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
    public int getNumArgs(ExpList e){
        /*
         * for the ith argument to the call, store it in
         * regFormals[i] starting with i=0
         */
        if (e==null){
            return 0;
        } else {
            return 1 + getNumArgs(e.elist);
        }
    }

    public Object visit(Call node, Object data){         
        Exp e1 = node.e1;
        Identifier i = node.i;
        ExpList e2=node.e2;
        String f_name = (String) e1.accept(ppVisitor,0);
        if (f_name.equals("System.out.println")){
            if (e2.elist != null){
                return "# sys out not supported for more than one argument, skipping...";
            }
            return (String) e2.e.accept(this, data)
            + "# " + node.accept(ppVisitor,0) + "\n"
            + "popq %rsi\n"
            + "leaq	L_.str(%rip), %rdi\n"
            + "callq _printf\n";

        }
        int numArgs = getNumArgs(e2);

        String evalArgsCode = "";
        String storeArgsCode = "";
        String makeCall = "";

        if (node.e2 != null){
            evalArgsCode = (String) node.e2.accept(this, data);
        }

        for (int j =0; j<numArgs; j++){
            storeArgsCode += 
              "popq "+regFormals[j]+"\n";
        }

        makeCall = 
              "# calling "+f_name+"\n"
            + "callq _"+f_name+"\n"  // using function name as label with "_" prefix
            + "pushq %rax\n"; // push the result of the function on the stack

        return 
            evalArgsCode +
            storeArgsCode +
            makeCall;

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
        String result="";

        result = (String) node.e.accept(this, data);
        if (node.elist != null){
            result += (String) node.elist.accept(this, data);
        }
        return result;  
    }

    public Object visit(False node, Object data){ 
        return "#"+node.accept(ppVisitor,0)+"\n"
        + "pushq $0\n";
    } 

    public Object visit(Formal node, Object data){ 
        // find location of formal in stack
        Identifier i=node.i;
        Identifier t=node.t;


        String varName = currClass+"_"+currMethod+"_"+i.s;
        String location;

        formalOffset += 8;
        location = formalOffset + "(%rbp)";

        // if (numFormals < 6){
        //     stackOffset -= 8;
        //     location = (stackOffset) + "(%rbp)";
        //     /*
        //      * up to the first six formals are passed in registers
        //      * and we will the copy them to the stack frame as locals
        //      */
        // } else {
        //     location = (2+numFormals-6)*8 + "(%rbp)";
        //     /*
        //      * formals from 7 on are store in offsets 16,24,32, ... above rbp
        //      */
        // }
        // numFormals += 1;
        // formalOffset += 8;
        
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

    public void populate_conditionals(ArrayList<ArrayList<String>> conditionals, ElseIf elif, Object data){
        if (elif.n != null){
            this.populate_conditionals(conditionals, elif.n, data);
        }
        ArrayList<String>cond = new ArrayList<String>();
        cond.add((String) elif.e.accept(this, data));
        if (elif.block != null){
            cond.add((String) elif.block.accept(this, data));
        }
        else{
            cond.add("");
        }
        conditionals.add(cond);
    }
    public Object visit(If node, Object data){ 
        // not implemented yet
        String e = (String) node.e.accept(this, data);
        String if_block = "";
        String else_block = "";
        ArrayList<ArrayList<String>> conditionals = new ArrayList<ArrayList<String>>();
        ArrayList<String>cond = new ArrayList<String>();
        cond.add(e);
        if (node.if_block != null){
            cond.add((String) node.if_block.accept(this,data));
        }
        else{
            cond.add("");
        }
        conditionals.add(cond);
        if (node.elif_block != null){
            populate_conditionals(conditionals, node.elif_block, data);
        }
        String conditional_src = "# conditional set up\n";
        for (int i = 0; i < conditionals.size(); i++){
            String l1 = "L"+this.labelNum;
            String l2 = "_L"+(this.labelNum + 1);
            if (i == 0){
                conditional_src += conditionals.get(i).get(0)
                +"#conditional logic start\n"
                + "popq %rax\n"
                + "cmpq $1, %rax\n"
                + "je "+ l1 + "\n"
                + "jmp "+l2+"\n"
                + l1+":\n"
                + "# body of if statement\n"
                + conditionals.get(i).get(1)
                + "jmp XXXX\n";
                this.labelNum += 1;
            }
            else{
                String _l1 = "__L"+this.labelNum;
                conditional_src += "_"+l1+":\n"
                + conditionals.get(i).get(0)
                + "popq %rax\n"
                + "cmpq $1, %rax\n"
                + "je "+ _l1 + "\n"
                + "jmp "+l2+"\n"
                + _l1 + ":\n"
                + "#body of elif statements\n"
                + conditionals.get(i).get(1)
                + "jmp XXXX\n";
                this.labelNum += 1;

            }

        }
        if (node.else_block != null){
            conditional_src += "_L"+this.labelNum+":\n"
            + (String)node.else_block.accept(this,data)
            + "jmp XXXX\n";
            this.labelNum += 1;
        }
        conditional_src += "XXXX:\n";
        conditional_src = conditional_src.replace("XXXX", "L"+this.labelNum);
        //System.out.println("testing conditional src here");
        //System.out.println(conditional_src);
        this.labelNum += 1;
        return conditional_src;
        

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
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq %rdx, %rax\n"
        + "jl "+ label1 + "\n"
        + "pushq $0\n"
        + "jmp "+label2+"\n"
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
    public int getFormalsLocations(FormalList fs){
        /*
         * getFormals returns the number of formals in the FormalList
         * and uses varMap to assign a stack frame location to each formal variable
         */
        if (fs==null){
            return 0;
        } else {
            int n = getFormalsLocations(fs.flist);
            String varName = currClass+"_"+currMethod+"_"+fs.f.i.s;
            int offset = 8*(n+1);
            String location = "-"+offset+"(%rbp)";
            varMap.put(varName,location);
            return n+1;
        }
    }

    public String copyFormals(FormalList fs,int n){
        /*
         * fs.f is the last Formal in the list and
         * fs.flist is the list of earlier formals
         * n is the number of formals in the FormalList
         * so regFormals[n-1] is the register it is stored in
         */
        if (fs==null){
            return "";
        } else {
            String code = copyFormals(fs.flist,n-1);
            String formalName = currClass+"_"+currMethod+"_"+fs.f.i.s;
            String location = varMap.get(formalName);
            String register = regFormals[n-1];
            code += 
                "movq "+register+", "+location
                     +"  # formal "+formalName+"->"+location+"\n";
            return code;
        }
    }

    public int getLocalsLocations(VarDeclList vs,int numFormals){
        /*
         * getLocals returns the number of local variables s in the VarList
         * and uses varMap to assign a stack frame location to each local variable
         */
        if (vs==null){
            return 0;
        } else {
            int n = getLocalsLocations(vs.vlist,numFormals);
            String varName = currClass+"_"+currMethod+"_"+vs.v.i.s;
            int offset = 8*(n+1+numFormals);
            String location = "-"+offset+"(%rbp)";
            varMap.put(varName,location);
            return n+1;
        }
    }

    public String initializeLocals(VarDeclList vs){
        /*
         * look up location of each local variable
         * and store zero in that location
         */
        if (vs==null){
            return "\n";
        }else {
            String varName = currClass+"_"+currMethod+"_"+vs.v.i.s;
            String location = varMap.get(varName);
            String initCode = initializeLocals(vs.vlist);
            initCode +=
               "movq $0, "+location+"  # "+varName+"->"+location+"\n";
            return initCode;
        }
    }

    public Object visit(MethodDecl node, Object data){ 
        // generate code for a function declaration

        String prologueCode="";
        String statementCode = "";
        String expressionCode = "";
        String epilogueCode = "";

        String formals="# formals\n";
        String locals="#locals\n";

        int numFormals = getFormalsLocations(node.args);
        int numLocals = getLocalsLocations(node.vars, numFormals);
        // create label for this method and store in labelMap

        String fullMethodName = "_"+node.name.s;
        /*
        String fullMethodName = "";
        if ((currClass+"_"+node.name.s).equals("James_main")){
            fullMethodName = "_main";
        }
        else{
            fullMethodName = currClass+"_"+node.name.s;
        }
        */
        String theLabel = fullMethodName;
        labelMap.put(fullMethodName, theLabel);

        stackOffset = -8*(numFormals+numLocals); // reserve space for formals/locals

        //node.t.accept(this, data);
        //node.i.accept(this, data);

        // if (node.f != null){
        //     node.f.accept(this, data);
        // }

        String copyFormalsCode = 
            copyFormals(node.args,numFormals);
        
        String initializeLocalsCode =
            initializeLocals(node.vars);

        

        //node.t.accept(this, data);
        //node.i.accept(this, data);
        if (node.statements != null){
            statementCode = (String) node.statements.accept(this, data);
        }

        if (node.returns != null){
            expressionCode = (String) node.returns.accept(this, data);
        }

        int stackChange = (- stackOffset);
        stackChange = ((int)Math.ceil(stackChange/16.0))*16;

        
          prologueCode = 
         "\n\n\n"
        +".globl "+theLabel+"\n"
        + theLabel+":\n"   
        + "# prologue\n"
        + "pushq %rbp\n"
        + "movq %rsp, %rbp\n"
        + "# make space for locals on stack, must be multiple of 16\n"
        + "subq $"+(stackChange)+", %rsp\n"
        + "# copy formals in registers to stack frame\n" 
        + copyFormalsCode
        + "# initialize local variables to zero\n"
        + initializeLocalsCode;
        

        epilogueCode =
        "# epilogue\n"
        +   "popq %rax\n"
        +   "addq $"+(stackChange)+", %rsp\n"
        +   "popq %rbp\n"
        +   "retq\n";

        return 
          prologueCode 
          + statementCode 
          +"# calculate return value\n"
          + expressionCode
          +epilogueCode; 
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
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e
        + "popq %rax\n"
        + "cmpq $1, %rax\n"
        + "je "+ label1 + "\n"
        + "pushq $1\n"
        + "jmp "+label2+"\n"
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

    
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        String e1 = (String) node.e.accept(this, data);
        return "# while loop set up\n"
        + e
        + "popq %rax\n"
        + "cmpq $1, %rax\n"
        + "je "+ label1 + "\n"
        + "jmp "+label2+"\n"
        + label1+":\n"
        + "# body of while loop\n"
        + scode +"\n"
        + e1+"\n"
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
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq $1, %rdx\n"
        + "je "+ label1 + "\n"
        + "pushq %rax\n"
        + "jmp "+label2+"\n"
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
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rax\n"
        + "popq %rdx\n"
        + "cmpq %rdx, %rax\n"
        + "jne "+ label1 + "\n"
        + "pushq $0\n"
        + "jmp "+label2 + "\n"
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
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq %rdx, %rax\n"
        + "jle "+ label1 + "\n"
        + "pushq $0" + "\n"
        + "jmp "+label2+"\n"
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

        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2 + e3
        + "popq %rax\n"
        + "popq %rdx\n"
        + "popq %rbx\n"
        + "cmpq $1, %rbx\n"
        + "je "+ label1 + "\n"
        + "pushq %rax\n"
        + "jmp "+label2+"\n"
        + label1+":\n"  
        + "pushq %rdx\n"
        + label2+":\n";
    }
    
    public Object visit(GreaterThanOrEqual node, Object data){
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq %rdx, %rax\n"
        + "jge "+ label1 + "\n"
        + "pushq $0\n"
        + "jmp "+label2+"\n"
        + label1+":\n"  
        + "pushq $1\n"
        + label2+":\n"; 
    }

    public Object visit(GreaterThan node, Object data){ 
        String e1 = (String) node.e1.accept(this, data);
        String e2 = (String) node.e2.accept(this, data);
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rdx\n"
        + "popq %rax\n"
        + "cmpq %rdx, %rax\n"
        + "jg "+ label1 + "\n"
        + "pushq $0\n"
        + "jmp "+label2+"\n"
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
        String label1 = "L"+this.labelNum;
        this.labelNum += 1;
        String label2 = "L"+this.labelNum;
        this.labelNum += 1;

        return "# "+node.accept(ppVisitor, 0) + "\n"
        + e1 + e2
        + "popq %rax\n"
        + "popq %rdx\n"
        + "cmpq %rdx, %rax\n"
        + "je "+ label1 + "\n"
        + "pushq $0\n"
        + "jmp "+label2+"\n"
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