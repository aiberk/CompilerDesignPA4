/* Generated By:JavaCC: Do not edit this line. MiniJava.java */
import syntaxtree.*;
import java.io.*;

public class MiniJava implements MiniJavaConstants {

  /** Main entry point. */
  public static void main(String args[]) throws ParseException, FileNotFoundException {
    FileReader fileReader = new FileReader("PA03demo.java");

    MiniJava parser = new MiniJava(fileReader);
    parser.Program();
    /*
    try {
      MethodDeclList n = parser.Program();

      System.out.println("\n\nPretty Printing the Abstract Syntax Tree");
      Visitor v1 = new AST_Visitor();  // pretty prints the Abstract Syntax Tree
      n.accept(v1, 0);

      System.out.println("\n\nDone!");

    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    */
  }

/* Program Syntax */
  static final public void Program() throws ParseException {
    StatementBlock();
    jj_consume_token(0);
  }

  static final public void StatementBlock() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CLASS:
      case PUBLIC:
      case RETURN:
      case IF:
      case WHILE:
      case BREAK:
      case FOREACH:
      case FOR:
      case NEW:
      case NULL:
      case TRUE:
      case FALSE:
      case CONTINUE:
      case PRIVATE:
      case PROTECTED:
      case THROW:
      case COMMENT:
      case IDENTIFIER:
      case INTEGER:
      case CHARACTER:
      case STRING:
      case LPAREN:
      case BOOL_NEG:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NEW:
      case NULL:
      case TRUE:
      case FALSE:
      case IDENTIFIER:
      case INTEGER:
      case CHARACTER:
      case STRING:
      case LPAREN:
      case BOOL_NEG:
        ExprVal1();
        jj_consume_token(SEMICOLON);
                             System.out.print(";");
        break;
      case RETURN:
        ReturnStatement();
        break;
      case CLASS:
        ClassDeclaration();
        break;
      case PUBLIC:
      case PRIVATE:
      case PROTECTED:
        MethodDeclaration();
        break;
      case WHILE:
        WhileLoop();
        break;
      case FOR:
        ForLoop();
        break;
      case COMMENT:
        CommentIgnore();
        break;
      case FOREACH:
        ForEachLoop();
        break;
      case BREAK:
        BreakStatement();
        break;
      case CONTINUE:
        ContinueStatement();
        break;
      case THROW:
        ThrowStatement();
        break;
      case IF:
        IfBlock();
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void CommentIgnore() throws ParseException {
    jj_consume_token(COMMENT);
  }

  static final public void ReturnStatement() throws ParseException {
    jj_consume_token(RETURN);
              System.out.print("return ");
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NEW:
    case NULL:
    case TRUE:
    case FALSE:
    case IDENTIFIER:
    case INTEGER:
    case CHARACTER:
    case STRING:
    case LPAREN:
    case BOOL_NEG:
      ExprVal1();
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    jj_consume_token(SEMICOLON);
                                                                       System.out.print(";");
  }

  static final public void BreakStatement() throws ParseException {
    jj_consume_token(BREAK);
            System.out.print("break ");
    jj_consume_token(SEMICOLON);
                                                      System.out.print(";");
  }

  static final public void ContinueStatement() throws ParseException {
    jj_consume_token(CONTINUE);
               System.out.print("break ");
    jj_consume_token(SEMICOLON);
                                                         System.out.print(";");
  }

  static final public void ThrowStatement() throws ParseException {
    jj_consume_token(THROW);
            System.out.print("throw ");
    ExprVal2();
    jj_consume_token(SEMICOLON);
                                                                 System.out.print(";");
  }

  static final public void IfBlock() throws ParseException {
    jj_consume_token(IF);
          System.out.print("if ");
    jj_consume_token(LPAREN);
                                              System.out.print("( ");
    ExprVal2();
    jj_consume_token(RPAREN);
                                                                                            System.out.print(") ");
    jj_consume_token(LBRACE);
              System.out.print("{");
    StatementBlock();
    jj_consume_token(RBRACE);
                                                                 System.out.print("}");
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ELSEIF:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      jj_consume_token(ELSEIF);
               System.out.print("else if ");
      jj_consume_token(LPAREN);
                                                        System.out.print("( ");
      ExprVal2();
      jj_consume_token(RPAREN);
                                                                                                      System.out.print(") ");
      jj_consume_token(LBRACE);
              System.out.print("{");
      StatementBlock();
      jj_consume_token(RBRACE);
                                                                 System.out.print("}");
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ELSE:
      jj_consume_token(ELSE);
             System.out.print("else ");
      jj_consume_token(LBRACE);
              System.out.print("{");
      StatementBlock();
      jj_consume_token(RBRACE);
                                                                 System.out.print("}");
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
  }

  static final public void MethodDeclaration() throws ParseException {
 Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PUBLIC:
      jj_consume_token(PUBLIC);
               System.out.print("public ");
      break;
    case PRIVATE:
      jj_consume_token(PRIVATE);
                 System.out.print("private ");
      break;
    case PROTECTED:
      jj_consume_token(PROTECTED);
                   System.out.print("protected ");
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STATIC:
      jj_consume_token(STATIC);
               System.out.print("static ");
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VOID:
      jj_consume_token(VOID);
             System.out.print("void ");
      break;
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
                                                           System.out.print(t.image+" ");
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    t = jj_consume_token(IDENTIFIER);
                    System.out.print(t.image+" ");
    jj_consume_token(LPAREN);
                                                              System.out.print("(");
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      CommaSeparatedSignatureList();
      break;
    default:
      jj_la1[8] = jj_gen;
      ;
    }
    jj_consume_token(RPAREN);
                                                                                                                                 System.out.print(")");
    jj_consume_token(LBRACE);
              System.out.print("{");
    StatementBlock();
    jj_consume_token(RBRACE);
                                                                 System.out.print("}");
  }

  static final public void CommaSeparatedSignatureList() throws ParseException {
    DeclarationIdentifiers();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_3;
      }
      jj_consume_token(COMMA);
                                      System.out.print(",");
      DeclarationIdentifiers();
    }
  }

  static final public void DeclarationIdentifiers() throws ParseException {
 Token t;
    t = jj_consume_token(IDENTIFIER);
                    System.out.print(t.image+" ");
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACKET:
      jj_consume_token(LBRACKET);
                                                                System.out.print("[");
      jj_consume_token(RBRACKET);
                                                                                                  System.out.print("]");
      break;
    default:
      jj_la1[10] = jj_gen;
      ;
    }
    t = jj_consume_token(IDENTIFIER);
                                                                                                                                           System.out.print(t.image+" ");
  }

  static final public void WhileLoop() throws ParseException {
    jj_consume_token(WHILE);
             System.out.print("while ");
    jj_consume_token(LPAREN);
                                                    System.out.print("( ");
    ExprVal2();
    jj_consume_token(RPAREN);
                                                                                                  System.out.print(") ");
    jj_consume_token(LBRACE);
              System.out.print("{");
    StatementBlock();
    jj_consume_token(RBRACE);
                                                                 System.out.print("}");
  }

  static final public void ForLoop() throws ParseException {
    jj_consume_token(FOR);
           System.out.print("for ");
    jj_consume_token(LPAREN);
                                                System.out.print("( ");
    ExprVal1();
    jj_consume_token(SEMICOLON);
                                                                                                 System.out.print("; ");
    ExprVal1();
    jj_consume_token(SEMICOLON);
                                                                                                                                                  System.out.print("; ");
    ExprVal1();
    jj_consume_token(RPAREN);
                                                                                                                                                                                                System.out.print(") ");
    jj_consume_token(LBRACE);
                                                                                                                                                                                                                                   System.out.print("{");
    StatementBlock();
    jj_consume_token(RBRACE);
                                                                                                                                                                                                                                                                                      System.out.print("}");
  }

  static final public void ForEachLoop() throws ParseException {
 Token t;
    jj_consume_token(FOREACH);
               System.out.print("for ");
    jj_consume_token(LPAREN);
                                                    System.out.print("( ");
    t = jj_consume_token(IDENTIFIER);
                                                                                             System.out.print(t.image+" ");
    t = jj_consume_token(IDENTIFIER);
                                                                                                                                             System.out.print(t.image+" ");
    jj_consume_token(COLON);
                                                                                                                                                                                      System.out.print(": ");
    ExprVal1();
    jj_consume_token(RPAREN);
                                                                                                                                                                                                                                    System.out.print(") ");
    jj_consume_token(LBRACE);
                                                                                                                                                                                                                                                                       System.out.print("{");
    StatementBlock();
    jj_consume_token(RBRACE);
                                                                                                                                                                                                                                                                                                                          System.out.print("}");
  }

  static final public void ClassDeclaration() throws ParseException {
 Token t;
    jj_consume_token(CLASS);
             System.out.print("class ");
    t = jj_consume_token(IDENTIFIER);
                                                          System.out.print(t.image+" ");
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EXTENDS:
      jj_consume_token(EXTENDS);
                                                                                                      System.out.print("extends ");
      ClassExtendsList();
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
    jj_consume_token(LBRACE);
              System.out.print("{");
    StatementBlock();
    jj_consume_token(RBRACE);
                                                                 System.out.print("}");
  }

  static final public void ClassExtendsList() throws ParseException {
 Token t;
    t = jj_consume_token(IDENTIFIER);
                   System.out.print(t.image+" ");
    ClassExtendsListP();
  }

  static final public void ClassExtendsListP() throws ParseException {
 Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COMMA:
      jj_consume_token(COMMA);
             System.out.print(", ");
      t = jj_consume_token(IDENTIFIER);
                                                     System.out.print(t.image+" ");
      ClassExtendsListP();
      break;
    default:
      jj_la1[12] = jj_gen;

    }
  }

  static final public void ExprVal1() throws ParseException {
    ExprVal2();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS_EQUALS:
    case MINUS_EQUALS:
    case FORWARD_SLASH_EQUALS:
    case PERCENT_EQUALS:
    case ASSIGNMENT:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ASSIGNMENT:
        jj_consume_token(ASSIGNMENT);
                               System.out.print("=");
        break;
      case PLUS_EQUALS:
        jj_consume_token(PLUS_EQUALS);
                         System.out.print("+=");
        break;
      case MINUS_EQUALS:
        jj_consume_token(MINUS_EQUALS);
                          System.out.print("-=");
        break;
      case FORWARD_SLASH_EQUALS:
        jj_consume_token(FORWARD_SLASH_EQUALS);
                                  System.out.print("/=");
        break;
      case PERCENT_EQUALS:
        jj_consume_token(PERCENT_EQUALS);
                            System.out.print("%=");
        break;
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      ExprVal1();
      break;
    default:
      jj_la1[14] = jj_gen;
      ;
    }
  }

  static final public void ExprVal2() throws ParseException {
    ExprVal3();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case QUESTIONMARK:
      jj_consume_token(QUESTIONMARK);
                                System.out.print("?");
      ExprVal3();
      jj_consume_token(COLON);
                                                                            System.out.print(":");
      ExprVal2();
      break;
    default:
      jj_la1[15] = jj_gen;
      ;
    }
  }

  static final public void ExprVal3() throws ParseException {
    ExprVal4();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OR:
      jj_consume_token(OR);
                      System.out.print("||");
      ExprVal3();
      break;
    default:
      jj_la1[16] = jj_gen;
      ;
    }
  }

  static final public void ExprVal4() throws ParseException {
    ExprVal8();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
      jj_consume_token(AND);
                       System.out.print("&&");
      ExprVal4();
      break;
    default:
      jj_la1[17] = jj_gen;
      ;
    }
  }

  static final public void ExprVal8() throws ParseException {
    ExprVal9();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT_EQUALS:
    case EQUALS:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQUALS:
        jj_consume_token(EQUALS);
                           System.out.print("==");
        break;
      case NOT_EQUALS:
        jj_consume_token(NOT_EQUALS);
                                                                    System.out.print("!=");
        break;
      default:
        jj_la1[18] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      ExprVal8();
      break;
    default:
      jj_la1[19] = jj_gen;
      ;
    }
  }

  static final public void ExprVal9() throws ParseException {
    ExprVal11();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INSTANCEOF:
    case LE:
    case GE:
    case LT:
    case GT:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LT:
        jj_consume_token(LT);
                        System.out.print("<");
        break;
      case LE:
        jj_consume_token(LE);
                                                         System.out.print("<=");
        break;
      case GT:
        jj_consume_token(GT);
                                                                                         System.out.print(">");
        break;
      case GE:
        jj_consume_token(GE);
                                                                                                                        System.out.print(">=");
        break;
      case INSTANCEOF:
        jj_consume_token(INSTANCEOF);
                                                                                                                                                                System.out.print("instanceof");
        break;
      default:
        jj_la1[20] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      ExprVal9();
      break;
    default:
      jj_la1[21] = jj_gen;
      ;
    }
  }

  static final public void ExprVal11() throws ParseException {
    ExprVal12();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
    case MINUS:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        jj_consume_token(PLUS);
                          System.out.print("+");
        break;
      case MINUS:
        jj_consume_token(MINUS);
                                                              System.out.print("-");
        break;
      default:
        jj_la1[22] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      ExprVal11();
      break;
    default:
      jj_la1[23] = jj_gen;
      ;
    }
  }

  static final public void ExprVal12() throws ParseException {
    ExprVal13__();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STAR:
    case FORWARD_SLASH:
    case PERCENT:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case STAR:
        jj_consume_token(STAR);
                            System.out.print("*");
        break;
      case FORWARD_SLASH:
        jj_consume_token(FORWARD_SLASH);
                                                                       System.out.print("/");
        break;
      case PERCENT:
        jj_consume_token(PERCENT);
                                                                                                            System.out.print("%");
        break;
      default:
        jj_la1[24] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      ExprVal12();
      break;
    default:
      jj_la1[25] = jj_gen;
      ;
    }
  }

  static final public void ExprVal13__() throws ParseException {
    ExprVal13();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EXPONENT:
      jj_consume_token(EXPONENT);
      ExprVal13__();
      break;
    default:
      jj_la1[26] = jj_gen;
      ;
    }
  }

  static final public void ExprVal13() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NEW:
      jj_consume_token(NEW);
           System.out.print("new");
      ExprVal14();
      break;
    case NULL:
    case TRUE:
    case FALSE:
    case IDENTIFIER:
    case INTEGER:
    case CHARACTER:
    case STRING:
    case LPAREN:
    case BOOL_NEG:
      ExprVal14();
      break;
    default:
      jj_la1[27] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void ExprVal14() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BOOL_NEG:
      jj_consume_token(BOOL_NEG);
                System.out.print("!");
      ExprVal16();
      break;
    case NULL:
    case TRUE:
    case FALSE:
    case IDENTIFIER:
    case INTEGER:
    case CHARACTER:
    case STRING:
    case LPAREN:
      ExprVal15();
      break;
    default:
      jj_la1[28] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void ExprVal15() throws ParseException {
    ExprVal16();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INCREMENT:
    case DECREMENT:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INCREMENT:
        jj_consume_token(INCREMENT);
                              System.out.print("++");
        break;
      case DECREMENT:
        jj_consume_token(DECREMENT);
                                                                       System.out.print("--");
        break;
      default:
        jj_la1[29] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[30] = jj_gen;
      ;
    }
  }

  static final public void ExprVal16() throws ParseException {
 Token t, t1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAREN:
      jj_consume_token(LPAREN);
              System.out.print("(");
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NEW:
      case NULL:
      case TRUE:
      case FALSE:
      case IDENTIFIER:
      case INTEGER:
      case CHARACTER:
      case STRING:
      case LPAREN:
      case BOOL_NEG:
        ExprVal2();
        break;
      default:
        jj_la1[31] = jj_gen;
        ;
      }
      jj_consume_token(RPAREN);
                                                              System.out.print(")");
      ExprVal16P();
      break;
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
                      System.out.print(t.image+" ");
      if (jj_2_1(2)) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LBRACKET:
          jj_consume_token(LBRACKET);
                     System.out.print("[");
          jj_consume_token(RBRACKET);
                                                        System.out.print("]");
          break;
        default:
          jj_la1[32] = jj_gen;
          ;
        }
        t1 = jj_consume_token(IDENTIFIER);
                                                                                                   System.out.print(t1.image+" ");
      } else {
        ExprVal16P();
      }
      break;
    case NULL:
    case TRUE:
    case FALSE:
    case INTEGER:
    case CHARACTER:
    case STRING:
      ExprBase();
      break;
    default:
      jj_la1[33] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void ExprVal16P() throws ParseException {
 Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACKET:
      jj_consume_token(LBRACKET);
                System.out.print("[");
      ExprVal2();
      jj_consume_token(RBRACKET);
                                                               System.out.print("]");
      ExprVal16P();
      break;
    case DOT:
      jj_consume_token(DOT);
           System.out.print(".");
      t = jj_consume_token(IDENTIFIER);
                                                   System.out.print(t.image+" ");
      ExprVal16P();
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
              System.out.print("(");
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NEW:
        case NULL:
        case TRUE:
        case FALSE:
        case IDENTIFIER:
        case INTEGER:
        case CHARACTER:
        case STRING:
        case LPAREN:
        case BOOL_NEG:
          ;
          break;
        default:
          jj_la1[34] = jj_gen;
          break label_4;
        }
        CommaSeparatedExprList();
      }
      jj_consume_token(RPAREN);
                                                                            System.out.print(")");
      ExprVal16P();
      break;
    default:
      jj_la1[35] = jj_gen;

    }
  }

  static final public void CommaSeparatedExprList() throws ParseException {
    ExprVal2();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[36] = jj_gen;
        break label_5;
      }
      jj_consume_token(COMMA);
                         System.out.print(",");
      ExprVal2();
    }
  }

  static final public void ExprBase() throws ParseException {
 Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STRING:
      t = jj_consume_token(STRING);
                System.out.print(t.image+" ");
      break;
    case INTEGER:
      t = jj_consume_token(INTEGER);
                   System.out.print(t.image+" ");
      break;
    case CHARACTER:
      t = jj_consume_token(CHARACTER);
                     System.out.print(t.image+" ");
      break;
    case NULL:
      t = jj_consume_token(NULL);
               System.out.print(t.image+" ");
      break;
    case TRUE:
      t = jj_consume_token(TRUE);
               System.out.print(t.image+" ");
      break;
    case FALSE:
      t = jj_consume_token(FALSE);
                System.out.print(t.image+" ");
      break;
    default:
      jj_la1[37] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_3R_6() {
    if (jj_scan_token(LBRACKET)) return true;
    if (jj_scan_token(RBRACKET)) return true;
    return false;
  }

  static private boolean jj_3_1() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_6()) jj_scanpos = xsp;
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public MiniJavaTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  /** Whether we are looking ahead. */
  static private boolean jj_lookingAhead = false;
  static private boolean jj_semLA;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[38];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static private int[] jj_la1_3;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
      jj_la1_init_2();
      jj_la1_init_3();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1e365a0,0x1e365a0,0x1e00000,0x800,0x1000,0x80,0x0,0x200,0x0,0x0,0x0,0x40,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x4000000,0x4000000,0x0,0x0,0x0,0x0,0x0,0x1e00000,0x1c00000,0x0,0x0,0x1e00000,0x0,0x1c00000,0x1e00000,0x0,0x0,0x1c00000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x7c218002,0x7c218002,0x78000000,0x0,0x0,0x18000,0x40000,0x8000000,0x8000000,0x80000000,0x0,0x0,0x80000000,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x78000000,0x78000000,0x0,0x0,0x78000000,0x0,0x78000000,0x78000000,0x0,0x80000000,0x70000000,};
   }
   private static void jj_la1_init_2() {
      jj_la1_2 = new int[] {0x20000001,0x20000001,0x20000001,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x4,0x0,0x0,0x1d80,0x1d80,0x80000000,0x8000,0x10000,0x60000,0x60000,0x780000,0x780000,0x1800000,0x1800000,0x1c000000,0x1c000000,0x2000000,0x20000001,0x20000001,0x6000,0x6000,0x20000001,0x4,0x1,0x20000001,0x40000005,0x0,0x0,};
   }
   private static void jj_la1_init_3() {
      jj_la1_3 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[1];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public MiniJava(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MiniJava(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MiniJavaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public MiniJava(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MiniJavaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public MiniJava(MiniJavaTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(MiniJavaTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = jj_lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List jj_expentries = new java.util.ArrayList();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Iterator it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          exists = true;
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.add(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[97];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 38; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
          if ((jj_la1_2[i] & (1<<j)) != 0) {
            la1tokens[64+j] = true;
          }
          if ((jj_la1_3[i] & (1<<j)) != 0) {
            la1tokens[96+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 97; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
