## Dev Plan

Find javaCC.jar
Move into dev folder

get cal.jj https://github.com/tjhickey724/compiler_design/blob/main/notes/parsing/cal.jj

Move to same folder

add test.txt with infix fomrulata
java -classpath javacc.jar javacc cal.jj

Add Tokens:

TOKEN:
{
< IDENTIFIER: <LETTER> (<LETTER>|<DIGIT>)\* >
| < LETTER: ["a"-"z", "A"-"Z"] >
| < DIGIT: ["0"-"9"] >
| < CLASS: "class" >
| < PUBLIC: "public" >
| < STATIC: "static" >
| < VOID: "void" >
| < MAIN: "main" >
| < STRING: "String" >
| < INT: "int" >
| < BOOLEAN: "boolean" >
| < IF: "if" >
| < ELSE: "else" >
| < WHILE: "while" >
| < RETURN: "return" >
| < SEMICOLON: ";" >
| < COMMA: "," >
| < DOT: "." >
| < EQUALS: "=" >
| < LBRACE: "{" >
| < RBRACE: "}" >
| < LBRACKET: "[" >
| < RBRACKET: "]" >
}

Add Grammar Rule:

void ClassDeclaration(): {}
{
<CLASS> <IDENTIFIER> <LBRACE> ( MethodDeclaration() )\* <RBRACE>
}

void MethodDeclaration(): {}
{
<PUBLIC> Type() <IDENTIFIER> <LPAREN> ( ParameterList() )? <RPAREN> <LBRACE> ( Statement() )\* <RBRACE>
}

void Statement(): {}
{
<IF> <LPAREN> Expression() <RPAREN> Statement() (<ELSE> Statement())?
| <WHILE> <LPAREN> Expression() <RPAREN> Statement()
| <RETURN> Expression() <SEMICOLON>
| Assignment()
| <LBRACE> ( Statement() )\* <RBRACE>
}

void Assignment(): {}
{
<IDENTIFIER> <EQUALS> Expression() <SEMICOLON>
}

void ParameterList(): {}
{
Type() <IDENTIFIER> ( <COMMA> Type() <IDENTIFIER> )\*
}

void Type(): {}
{
<INT> ( <LBRACKET> <RBRACKET> )?
| <BOOLEAN>
| <IDENTIFIER>
}

// Extend Expression() as needed
void Expression(): {}
{
<NUMBER> // This is a simplified placeholder
}

// Extend your main method to parse ClassDeclaration

Modify the main method to parse ClassDeclaration:

public static void main(String[] args) throws FileNotFoundException
{
if ( args.length < 1 ) {
System.out.println("Please pass in the filename for a parameter.");
System.exit(1);
}

    cal parser = new cal(new FileInputStream(args[0]));
    try {
        parser.ClassDeclaration();
        System.out.println("Parsing successful!");
    } catch (ParseException e) {
        System.out.println("Parsing failed.");
        e.printStackTrace();
    }

}

## Identifiers and Literals

1. **Identifiers**

   - Variable names, not starting with `_` and not containing `$`.

2. **Integer Literals**

   - Numbers like `1234`, not starting with `0` (except for `0` itself), no suffixes (`1234L` is invalid), and `2147483648` is not allowed.

3. **String Literals**

   - Text enclosed in double quotes, like `"hello"`. Supported escape sequences are `\f`, `\n`, `\r`, `\t`, `\"`, `\'`, `\\`.

4. **Character Literals**
   - Single characters, like `'X'`, with type `int`. Same escape sequences as string literals.

### Reserved Words

5. **class**

   - Cannot be declared as `public`.

6. **extends**

7. **public**

   - Only allowed in method headers.

8. **return**

   - Only as the last statement of a value-returning function.

9. **boolean**

10. **int**

11. **void**

12. **if**

13. **else**

14. **while**

15. **break**

    - No labeled break statements.

16. **for**

    - No 'foreach' style loops.

17. **switch**

    - The switch-expression must have type `int`.

18. **case**

19. **default**

20. **new**

    - Object creation (e.g., `new MyClass()`) without parameters and array creation (e.g., `new int[4]`).

21. **null**

22. **true**

23. **false**

24. **this**

25. **super**

    - Only in method calls.

26. **instanceof**

### Unused Reserved Words

27. **Unused Reserved Words**
    - List of reserved words not supported in MiniJava (e.g., `abstract`, `assert`, `byte`, etc.).

### Grouping and Separator Characters

28. **Commas (,)**

    - To separate method parameters.

29. **Parentheses ((), {})**

    - For grouping, casts, parameters, and statement grouping.

30. **Colon (:)**
    - For case labels.

### Operators

31. **Assignment (=)**

    - Does not produce a value.

32. **Increment (++) and Decrement (--)**

    - Do not produce a value; only for plain variables.

33. **Logical Operators (||, &&, !=, ==, <=, >=, <, >)**

34. **Arithmetic Operators (+, -, \*, /, %)**

35. **Unary Operators (!, instanceof, (type) cast, array-element access [ ], instance variable/method access .)**

### Unused Operators

36. **Bitwise and Compound Assignment Operators**
    - Not supported in MiniJava.

### Other Restrictions

37. **Single Source File Requirement**

    - The entire program must be in one source file.

38. **Variable and Access Restrictions**

    - No `length` for instance variables, no public instance variables, no multiple variable declarations, no brace-enclosed array initializers, etc.

39. **Function and Class Restrictions**

    - No function overloading, no inner/anonymous classes, no generics, no finalizers, no C-style array declarations.

40. **Constructors**
    - No explicit constructor declarations; only the default constructor is available.

## Missing

- Imports
- For loops
- constructors
- do-while
- try-catch
- throw
- generic typing
- generic for each
- iterator objects
- classes
