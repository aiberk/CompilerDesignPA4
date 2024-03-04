# PA4: MiniJava

## TODOs

- make list of pr's to be done
- diviy up work
- clean up code base (new github repo)

| Task                                        | Status | Person |
| ------------------------------------------- | ------ | ------ |
| Part 1 ASTG                                 | N/A    | James  |
| Add MiniC to project                        | ✅     | Aby    |
| Make MiniC Run                              | ❌     |        |
| Integrate MiniC with Gavel                  | ❌     |        |
| Rename the MiniC to MiniJava.jj + AST rules | ❌     |        |
| Part 2 Pretty Print                         | N/A    | Aby    |
| Part 3 Symbol Table                         | N/A    | James  |
| Part 4 Type Checking                        | N/A    | Aby    |
| Part 0 MGMT and Making our lives easier     | N/A    | Aby    |
| JUnit Test for Inidivudal Showcasing Demos  | ❌     |        |
| Clean up project                            | ❌     |        |
| New github Repo                             | ❌     |        |
| Make tasks                                  | ❌     |        |

## Mini C ASTG Generator

### In this problem set you will extend the MiniC.jj ASTG generator to all of MiniJava

You should

1. download the MiniC folder from github, and rename it to be MiniJava
2. create a private github repository and upload the MiniC folder for your group to use
3. rename the MiniC.jj to MiniJava.jj and add rules so that it generates the Abstract Syntax Trees for full MiniJava
4. the AST_Visitor should work for full MiniJava with no changes...

Every team member should create a 60 second video showing you running your MiniJava AST_Visitor to print the Abstract Syntax Tree for a MiniJava
program which contains 2 classes, 2 methods, and uses method calls and array assignments and array lookups

You should also create a second 60 second video where you explain in detail what you had to do to generate AST for some MiniJava node, for example, the ClassDecl node.

Upload your MiniJava.jj file with a comment at the top containing links to your two videos
and the names of the people on your team.

## Pretty Printing

### Extend the PP_Visitor so that it pretty prints any MiniJava program.

Create a 60 second video show you running your MiniJava pretty printer on a program
and then compiling and running your pretty printed version to show that it generates the same
results as the original.

Create a 60 second video explaining in detail the changes you had to make
to at least one intereting visitor method to make this change.

Upload your PP_Visitor file with a comment at the top containing links to your two videos
and the names of the people on your team.

## Symbol Table

### Modify the SymbolTableVisitor and the SymbolTable.java class so that it creates and prints Symbol Tables for MiniJava

Create a 60 second video where you show how to run the SymbolTable generator and you create a SymbolTable for an
interesting program with all of the different types.

Create another 60 second video where you explain how you did this and give a detailed example of how you had to modify
at least one interesting visitor method to create the Symbol table.

Upload your SymbolTableVisitor.java file with a comment at the top containing links to your two videos
and the names of the people on your team.

## Type Checking

### Modify the TypeCheckingVisitor to handle all of MiniJava.

Create a 60 second video showing you running it on a program with every kind of type error

Create another 60 second video where you explain in detail how you needed to modify one of the
visitor methods to implement type checking. Pick an interesting node that isn't in the miniC typechecker.

Upload your TypeCheckingVisitor.java file with a comment at the top containing links to your two videos
and the names of the people on your team.
