
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

#20
pushq $20
#b = 20;

popq %rax
movq %rax, -8(%rbp)

#a
pushq -16(%rbp) #  a
#b
pushq -8(%rbp) #  b
# multiply:a * b
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
# System.out.println(a * b)
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

