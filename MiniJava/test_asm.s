
.globl _main
_main:
# formals
# # James_main_b->16(%rbp)

# # James_main_a->24(%rbp)


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
popq %rdi
callq _printf
# calculate return value
# epilogue
popq %rax
addq $16, %rsp
movq %rbp, %rsp
popq %rbp
retq

