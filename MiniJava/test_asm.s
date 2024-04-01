.section	__TEXT,__text,regular,pure_instructions
.build_version macos, 13, 0	sdk_version 13, 0





.globl _main
_main:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $16, %rsp
# copy formals in registers to stack frame
# initialize local variables to zero

movq $0, -8(%rbp)  # James__j->-8(%rbp)

#16
pushq $16
popq %rdi
# calling fib
callq _fib
pushq %rax
#j = fib(16);

popq %rax
movq %rax, -8(%rbp)

#j
pushq -8(%rbp) #  j
# System.out.println(j)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf
# calculate return value
#100
pushq $100
# epilogue
popq %rax
addq $16, %rsp
popq %rbp
retq



.globl _fib
_fib:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $48, %rsp
# copy formals in registers to stack frame
movq %rdi, -8(%rbp)  # formal James__n->-8(%rbp)
# initialize local variables to zero

movq $0, -16(%rbp)  # James__i->-16(%rbp)
movq $0, -24(%rbp)  # James__a->-24(%rbp)
movq $0, -32(%rbp)  # James__b->-32(%rbp)
movq $0, -40(%rbp)  # James__temp->-40(%rbp)

#0
pushq $0
#a = 0;

popq %rax
movq %rax, -24(%rbp)

#1
pushq $1
#b = 1;

popq %rax
movq %rax, -32(%rbp)

#1
pushq $1
#i = 1;

popq %rax
movq %rax, -16(%rbp)

# while loop set up
# i < n
#i
pushq -16(%rbp) #  i
#n
pushq -8(%rbp) #  n
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L1
pushq $0
jmp L2
L1:
pushq $1
L2:
popq %rax
cmpq $1, %rax
je L3
jmp L4
L3:
# body of while loop

#a
pushq -24(%rbp) #  a
#b
pushq -32(%rbp) #  b
# plus:a + b
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#temp = a + b;

popq %rax
movq %rax, -40(%rbp)

#b
pushq -32(%rbp) #  b
#a = b;

popq %rax
movq %rax, -24(%rbp)

#temp
pushq -40(%rbp) #  temp
#b = temp;

popq %rax
movq %rax, -32(%rbp)

#i
pushq -16(%rbp) #  i
#1
pushq $1
# plus:i + 1
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#i = i + 1;

popq %rax
movq %rax, -16(%rbp)

# i < n
#i
pushq -16(%rbp) #  i
#n
pushq -8(%rbp) #  n
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L5
pushq $0
jmp L6
L5:
pushq $1
L6:

popq %rax
cmpq $1, %rax
je L3
jmp L4
L4:
# calculate return value
#b
pushq -32(%rbp) #  b
# epilogue
popq %rax
addq $48, %rsp
popq %rbp
retq


L_.str:
.asciz	"%d\n"
.subsections_via_symbols

