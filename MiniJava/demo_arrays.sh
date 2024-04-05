gcc demo_arrays.c -S -c -O0
gcc -c demo_arrays.s -o demo_arrays.o
gcc demo_arrays.o -o demo_arrays
./demo_arrays