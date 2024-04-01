
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
# James_main_t->-24(%rbp)
# James_main_s->-32(%rbp)
#make space for locals on stack
subq $32, %rsp

#0
pushq $0
#a = 0;

popq %rax
movq %rax, -16(%rbp)

#3
pushq $3
#t = 3;

popq %rax
movq %rax, -24(%rbp)

# b = new int[10];

movq $88, %rdi
callq _malloc
movq %rax, -8(%rbp)
movq $0, %rcx
movq $10, %rdx
movq %rdx, (%rax, %rcx, 8)

# b[0] = 100;

#100
pushq $100
#0
pushq $0
popq %rcx
incq %rcx
movq -8(%rbp), %rax
popq %rdx
movq %rdx, (%rax, %rcx, 8)

# b[t] = 200;

#200
pushq $200
#t
pushq -24(%rbp) #  t
popq %rcx
incq %rcx
movq -8(%rbp), %rax
popq %rdx
movq %rdx, (%rax, %rcx, 8)

# b[t]
#b
pushq -8(%rbp) #  b
#t
pushq -24(%rbp) #  t
popq %rcx
incq %rcx
popq %rax
pushq (%rax, %rcx, 8)
#a = b[t];

popq %rax
movq %rax, -16(%rbp)

#a
pushq -16(%rbp) #  a
# System.out.println(a)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf

# b[0]
#b
pushq -8(%rbp) #  b
#0
pushq $0
popq %rcx
incq %rcx
popq %rax
pushq (%rax, %rcx, 8)
# b[t]
#b
pushq -8(%rbp) #  b
#t
pushq -24(%rbp) #  t
popq %rcx
incq %rcx
popq %rax
pushq (%rax, %rcx, 8)
# plus:b[0] + b[t]
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
# System.out.println(b[0] + b[t])
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf

# b.length
#b
pushq -8(%rbp) #  b

popq %rax
movq $0, %rcx
pushq (%rax,%rcx, 8)
# System.out.println(b.length)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf
# calculate return value
#a
pushq -16(%rbp) #  a
# epilogue
popq %rax
addq $32, %rsp
movq %rbp, %rsp
popq %rbp
retq
L_.str:
.asciz	"%d\n"
.subsections_via_symbols

