
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
# James_main_c->-8(%rbp)
# James_main_k->-16(%rbp)
# James_main_b->-24(%rbp)
# James_main_a->-32(%rbp)
#make space for locals on stack
subq $32, %rsp

#10
pushq $10
#a = 10;

popq %rax
movq %rax, -32(%rbp)

#20
pushq $20
#b = 20;

popq %rax
movq %rax, -24(%rbp)

# c = new int[10];

movq $88, %rdi
callq _malloc
movq %rax, -8(%rbp)

# c[3] = a + b;

#a
pushq -32(%rbp) #  a
#b
pushq -24(%rbp) #  b
# plus:a + b
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#3
pushq $3
popq %rcx
incq %rcx
movq -8(%rbp), %rax
popq %rdx
movq %rdx, (%rax, %rcx, 8)

# c[3]
#c
pushq -8(%rbp) #  c
#3
pushq $3
popq %rcx
incq %rcx
popq %rax
pushq (%rax, %rcx, 8)
#k = c[3];

popq %rax
movq %rax, -16(%rbp)

#k
pushq -16(%rbp) #  k
# System.out.println(k)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf
# calculate return value
# epilogue
popq %rax
addq $32, %rsp
movq %rbp, %rsp
popq %rbp
retq
L_.str:
.asciz	"%d\n"
.subsections_via_symbols

