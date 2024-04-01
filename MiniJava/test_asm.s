
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
# James_main_b->-8(%rbp)
# James_main_a->-16(%rbp)
#make space for locals on stack
subq $16, %rsp

#10
pushq $10
#a = 10;

popq %rax
movq %rax, -16(%rbp)

# 10 < 8 ? 24 : 24
# 10 < 8
#10
pushq $10
#8
pushq $8
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L1
pushq $0
jmp L2
L1:
pushq $1
L2:
#24
pushq $24
#21
pushq $21
popq %rax
popq %rdx
popq %rbx
cmpq $1, %rbx
je L3
pushq %rax
jmp L4
L3:
pushq %rdx
L4:
#b = 10 < 8 ? 24 : 24;

popq %rax
movq %rax, -8(%rbp)

#b
pushq -8(%rbp) #  b
# System.out.println(b)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf
# calculate return value
# epilogue
popq %rax
addq $16, %rsp
movq %rbp, %rsp
popq %rbp
retq
L_.str:
.asciz	"%d\n"
.subsections_via_symbols

