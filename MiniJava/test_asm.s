
.section	__TEXT,__text,regular,pure_instructions
.build_version macos, 13, 0	sdk_version 13, 0
.globl _main
.p2align	4, 0x90
_main:
# formals
# prologue
pushq %rbp
movq %rsp, %rbp
#locals
# James_main_temp->-8(%rbp)
# James_main_n->-16(%rbp)
# James_main_b->-24(%rbp)
# James_main_a->-32(%rbp)
# James_main_i->-40(%rbp)
#make space for locals on stack
subq $40, %rsp

#0
pushq $0
#a = 0;

popq %rax
movq %rax, -32(%rbp)

#1
pushq $1
#b = 1;

popq %rax
movq %rax, -24(%rbp)

#1
pushq $1
#i = 1;

popq %rax
movq %rax, -40(%rbp)

#20
pushq $20
#n = 20;

popq %rax
movq %rax, -16(%rbp)

# while loop set up
# i < n
#i
pushq -40(%rbp) #  i
#n
pushq -16(%rbp) #  n
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
pushq -32(%rbp) #  a
#b
pushq -24(%rbp) #  b
# plus:a + b
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#temp = a + b;

popq %rax
movq %rax, -8(%rbp)

#b
pushq -24(%rbp) #  b
#a = b;

popq %rax
movq %rax, -32(%rbp)

#temp
pushq -8(%rbp) #  temp
#b = temp;

popq %rax
movq %rax, -24(%rbp)

#b
pushq -24(%rbp) #  b
# System.out.println(b)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf

#i
pushq -40(%rbp) #  i
#1
pushq $1
# plus:i + 1
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#i = i + 1;

popq %rax
movq %rax, -40(%rbp)

# i < n
#i
pushq -40(%rbp) #  i
#n
pushq -16(%rbp) #  n
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
# epilogue
popq %rax
addq $40, %rsp
movq %rbp, %rsp
popq %rbp
retq
L_.str:
.asciz	"%d\n"
.subsections_via_symbols

