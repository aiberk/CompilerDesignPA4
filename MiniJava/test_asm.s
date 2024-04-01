
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

# conditional set up
# (a + 1 < 0)&&(a - 1 == 0)
# a + 1 < 0
#a
pushq -8(%rbp) #  a
#1
pushq $1
# plus:a + 1
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#0
pushq $0
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L1
pushq $0
jmp L2
L1:
pushq $1
L2:
# a - 1 == 0
#a
pushq -8(%rbp) #  a
#1
pushq $1
# minus:a - 1
popq %rdx
popq %rax
subq %rdx, %rax
pushq %rax
#0
pushq $0
popq %rax
popq %rdx
cmpq %rdx, %rax
je L3
pushq $0
jmp L4
L3:
pushq $1
jmp L4
L4:
popq %rdx
popq %rax
cmpq $1, %rdx
je L5
pushq $0
jmp L6
L5:
pushq %rax
L6:
popq %rax
cmpq $1, %rax
je L7
jmp L8
L7:
# body of if statement

#1
pushq $1
# System.out.println(1)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf
jmp L9
L8:
# body of else statement

#0
pushq $0
# System.out.println(0)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf
jmp L9
L9:
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

