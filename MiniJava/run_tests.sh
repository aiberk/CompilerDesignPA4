java -classpath javacc.jar javacc MiniJava.jj
javac MiniJava.java
java MiniJava
gcc -c test_asm.s -o test_asm.o
gcc test_asm.o -o test_asm
./test_asm