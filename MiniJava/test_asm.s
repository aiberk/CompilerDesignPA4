.section	__TEXT,__text,regular,pure_instructions
.build_version macos, 13, 0	sdk_version 13, 0





.globl _main
_main:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $32, %rsp
# copy formals in registers to stack frame
# initialize local variables to zero

movq $0, -8(%rbp)  # James__a->-8(%rbp)
movq $0, -16(%rbp)  # James__j->-16(%rbp)
movq $0, -24(%rbp)  # James__k->-24(%rbp)

#3
pushq $3
#10
pushq $10
popq %rdi
popq %rsi
# calling arrayDemo
callq _arrayDemo
pushq %rax
#a = arrayDemo(10, 3);

popq %rax
movq %rax, -8(%rbp)
# calculate return value
#a
pushq -8(%rbp) #  a
# epilogue
popq %rax
addq $32, %rsp
popq %rbp
retq



.globl _arrayDemo
_arrayDemo:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $32, %rsp
# copy formals in registers to stack frame
movq %rdi, -8(%rbp)  # formal James__s->-8(%rbp)
movq %rsi, -16(%rbp)  # formal James__t->-16(%rbp)
# initialize local variables to zero

movq $0, -24(%rbp)  # James__a->-24(%rbp)
movq $0, -32(%rbp)  # James__b->-32(%rbp)

#0
pushq $0
#a = 0;

popq %rax
movq %rax, -24(%rbp)

# b = new int[t];

#t
pushq -16(%rbp) #  t
popq %rax
movq %rax, %rdx
incq %rax
imulq $8, %rax
movq %rdx, %rdi
callq _malloc
movq %rax, -32(%rbp)
movq $0, %rcx
movq %rdx, (%rax, %rcx, 8)

# b[0] = 100;

#100
pushq $100
#0
pushq $0
popq %rcx
incq %rcx
movq -32(%rbp), %rax
popq %rdx
movq %rdx, (%rax, %rcx, 8)

# b[t] = 200;

#200
pushq $200
#t
pushq -16(%rbp) #  t
popq %rcx
incq %rcx
movq -32(%rbp), %rax
popq %rdx
movq %rdx, (%rax, %rcx, 8)

# b[t]
#b
pushq -32(%rbp) #  b
#t
pushq -16(%rbp) #  t
popq %rcx
incq %rcx
popq %rax
pushq (%rax, %rcx, 8)
#a = b[t];

popq %rax
movq %rax, -24(%rbp)

#a
pushq -24(%rbp) #  a
# System.out.println(a)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf
# calculate return value
#a
pushq -24(%rbp) #  a
# epilogue
popq %rax
addq $32, %rsp
popq %rbp
retq


L_.str:
.asciz	"%d\n"
.subsections_via_symbols

