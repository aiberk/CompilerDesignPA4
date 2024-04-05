gcc demo_while.c -S -c -O0
gcc -c demo_while.s -o demo_while.o
gcc demo_while.o -o demo_while
./demo_while