
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
# a < 10 || a * 2 == 20
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
# a * 2 == 20
#a
pushq -8(%rbp) #  a
#2
pushq $2
# multiply:a * 2
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
#20
pushq $20
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
pushq %rax
jmp L6
L5:
pushq $1
L6:
popq %rax
cmpq $1, %rax
je L7
jmp L8
L7:
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

# a < 10 || a * 2 == 20
# a < 10
#a
pushq -8(%rbp) #  a
#10
pushq $10
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L9
pushq $0
jmp L10
L9:
pushq $1
L10:
# a * 2 == 20
#a
pushq -8(%rbp) #  a
#2
pushq $2
# multiply:a * 2
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
#20
pushq $20
popq %rax
popq %rdx
cmpq %rdx, %rax
je L11
pushq $0
jmp L12
L11:
pushq $1
jmp L12
L12:
popq %rdx
popq %rax
cmpq $1, %rdx
je L13
pushq %rax
jmp L14
L13:
pushq $1
L14:

popq %rax
cmpq $1, %rax
je L7
jmp L8
L8:
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

