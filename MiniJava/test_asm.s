.section	__TEXT,__text,regular,pure_instructions
.build_version macos, 13, 0	sdk_version 13, 0





.globl _main
_main:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $16, %rsp
# copy formals in registers to stack frame
# initialize local variables to zero

movq $0, -8(%rbp)  # James__c->-8(%rbp)

#20
pushq $20
#c = 20;

popq %rax
movq %rax, -8(%rbp)

#10
pushq $10
popq %rdi
# calling fib
callq _fib
pushq %rax
#c = fib(10);

popq %rax
movq %rax, -8(%rbp)

#1000
pushq $1000
#c
pushq -8(%rbp) #  c
# multiply:1000 * c
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
# System.out.println(1000 * c)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf

#200
pushq $200
#100
pushq $100
#100
pushq $100
popq %rdi
popq %rsi
popq %rdx
# calling test
callq _test
pushq %rax
#c = test(100, 100, 200);

popq %rax
movq %rax, -8(%rbp)

#200
pushq $200
#150
pushq $150
#100
pushq $100
popq %rdi
popq %rsi
popq %rdx
# calling test
callq _test
pushq %rax
#c = test(100, 150, 200);

popq %rax
movq %rax, -8(%rbp)

#200
pushq $200
#10
pushq $10
#100
pushq $100
popq %rdi
popq %rsi
popq %rdx
# calling test
callq _test
pushq %rax
#c = test(100, 10, 200);

popq %rax
movq %rax, -8(%rbp)

#200
pushq $200
#300
pushq $300
#100
pushq $100
popq %rdi
popq %rsi
popq %rdx
# calling test
callq _test
pushq %rax
#c = test(100, 300, 200);

popq %rax
movq %rax, -8(%rbp)
# calculate return value
#c
pushq -8(%rbp) #  c
# epilogue
popq %rax
addq $16, %rsp
popq %rbp
retq



.globl _test
_test:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $32, %rsp
# copy formals in registers to stack frame
movq %rdi, -8(%rbp)  # formal James__a->-8(%rbp)
movq %rsi, -16(%rbp)  # formal James__b->-16(%rbp)
movq %rdx, -24(%rbp)  # formal James__c->-24(%rbp)
# initialize local variables to zero

movq $0, -32(%rbp)  # James__d->-32(%rbp)

# conditional set up
#b
pushq -16(%rbp) #  b
#a
pushq -8(%rbp) #  a
popq %rdi
popq %rsi
# calling equal
callq _equal
pushq %rax
#conditional logic start
popq %rax
cmpq $1, %rax
je L17
jmp _L18
L17:
# body of if statement

#0
pushq $0
#d = 0;

popq %rax
movq %rax, -32(%rbp)
jmp L22
_L18:
# (a < b)&&!(c < b)
# a < b
#a
pushq -8(%rbp) #  a
#b
pushq -16(%rbp) #  b
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L1
pushq $0
jmp L2
L1:
pushq $1
L2:
# !(c < b)
# c < b
#c
pushq -24(%rbp) #  c
#b
pushq -16(%rbp) #  b
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L3
pushq $0
jmp L4
L3:
pushq $1
L4:
popq %rax
cmpq $1, %rax
je L5
pushq $1
jmp L6
L5:
pushq $0
L6:
popq %rdx
popq %rax
cmpq $1, %rdx
je L7
pushq $0
jmp L8
L7:
pushq %rax
L8:
popq %rax
cmpq $1, %rax
je __L18
jmp _L19
__L18:
#body of elif statements

#111
pushq $111
#d = 111;

popq %rax
movq %rax, -32(%rbp)
jmp L22
_L19:
# !(a < b)
# a < b
#a
pushq -8(%rbp) #  a
#b
pushq -16(%rbp) #  b
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L9
pushq $0
jmp L10
L9:
pushq $1
L10:
popq %rax
cmpq $1, %rax
je L11
pushq $1
jmp L12
L11:
pushq $0
L12:
popq %rax
cmpq $1, %rax
je __L19
jmp _L20
__L19:
#body of elif statements

#222
pushq $222
#d = 222;

popq %rax
movq %rax, -32(%rbp)
jmp L22
_L20:
# !(b < c)
# b < c
#b
pushq -16(%rbp) #  b
#c
pushq -24(%rbp) #  c
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L13
pushq $0
jmp L14
L13:
pushq $1
L14:
popq %rax
cmpq $1, %rax
je L15
pushq $1
jmp L16
L15:
pushq $0
L16:
popq %rax
cmpq $1, %rax
je __L20
jmp _L21
__L20:
#body of elif statements

#333
pushq $333
#d = 333;

popq %rax
movq %rax, -32(%rbp)
jmp L22
_L21:

#444
pushq $444
#d = 444;

popq %rax
movq %rax, -32(%rbp)
jmp L22
L22:

#d
pushq -32(%rbp) #  d
# System.out.println(d)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf
# calculate return value
#d
pushq -32(%rbp) #  d
# epilogue
popq %rax
addq $32, %rsp
popq %rbp
retq



.globl _equal
_equal:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $16, %rsp
# copy formals in registers to stack frame
movq %rdi, -8(%rbp)  # formal James__a->-8(%rbp)
movq %rsi, -16(%rbp)  # formal James__b->-16(%rbp)
# initialize local variables to zero

# calculate return value
# !(a < b)&&!(b < a)
# !(a < b)
# a < b
#a
pushq -8(%rbp) #  a
#b
pushq -16(%rbp) #  b
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L23
pushq $0
jmp L24
L23:
pushq $1
L24:
popq %rax
cmpq $1, %rax
je L25
pushq $1
jmp L26
L25:
pushq $0
L26:
# !(b < a)
# b < a
#b
pushq -16(%rbp) #  b
#a
pushq -8(%rbp) #  a
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L27
pushq $0
jmp L28
L27:
pushq $1
L28:
popq %rax
cmpq $1, %rax
je L29
pushq $1
jmp L30
L29:
pushq $0
L30:
popq %rdx
popq %rax
cmpq $1, %rdx
je L31
pushq $0
jmp L32
L31:
pushq %rax
L32:
# epilogue
popq %rax
addq $16, %rsp
popq %rbp
retq



.globl _fib
_fib:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $48, %rsp
# copy formals in registers to stack frame
movq %rdi, -8(%rbp)  # formal James__n->-8(%rbp)
# initialize local variables to zero

movq $0, -16(%rbp)  # James__i->-16(%rbp)
movq $0, -24(%rbp)  # James__a->-24(%rbp)
movq $0, -32(%rbp)  # James__b->-32(%rbp)
movq $0, -40(%rbp)  # James__temp->-40(%rbp)

#0
pushq $0
#a = 0;

popq %rax
movq %rax, -24(%rbp)

#1
pushq $1
#b = 1;

popq %rax
movq %rax, -32(%rbp)

#1
pushq $1
#i = 1;

popq %rax
movq %rax, -16(%rbp)

# while loop set up
# i < n
#i
pushq -16(%rbp) #  i
#n
pushq -8(%rbp) #  n
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L33
pushq $0
jmp L34
L33:
pushq $1
L34:
popq %rax
cmpq $1, %rax
je L35
jmp L36
L35:
# body of while loop

#a
pushq -24(%rbp) #  a
#b
pushq -32(%rbp) #  b
# plus:a + b
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#temp = a + b;

popq %rax
movq %rax, -40(%rbp)

#b
pushq -32(%rbp) #  b
#a = b;

popq %rax
movq %rax, -24(%rbp)

#temp
pushq -40(%rbp) #  temp
#b = temp;

popq %rax
movq %rax, -32(%rbp)

#b
pushq -32(%rbp) #  b
# System.out.println(b)
popq %rsi
leaq	L_.str(%rip), %rdi
callq _printf

#i
pushq -16(%rbp) #  i
#1
pushq $1
# plus:i + 1
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#i = i + 1;

popq %rax
movq %rax, -16(%rbp)

# i < n
#i
pushq -16(%rbp) #  i
#n
pushq -8(%rbp) #  n
popq %rdx
popq %rax
cmpq %rdx, %rax
jl L37
pushq $0
jmp L38
L37:
pushq $1
L38:

popq %rax
cmpq $1, %rax
je L35
jmp L36
L36:
# calculate return value
#b
pushq -32(%rbp) #  b
# epilogue
popq %rax
addq $48, %rsp
popq %rbp
retq


L_.str:
.asciz	"%d\n"
.subsections_via_symbols

