
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
# James_main_a->-8(%rbp)
#make space for locals on stack
subq $8, %rsp

#1
pushq $1
#a = 1;

popq %rax
movq %rax, -8(%rbp)

# while loop set up
# a < 10
#a
pushq -8(%rbp) #  a
#10
pushq $10
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
pushq -8(%rbp) #  a
# System.out.println(a)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf

#a
pushq -8(%rbp) #  a
#1
pushq $1
# plus:a + 1
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#a = a + 1;

popq %rax
movq %rax, -8(%rbp)

# a < 10
#a
pushq -8(%rbp) #  a
#10
pushq $10
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
L4:
# calculate return value
# epilogue
popq %rax
addq $8, %rsp
movq %rbp, %rsp
popq %rbp
retq
L_.str:
.asciz	"%d\n"
.subsections_via_symbols

