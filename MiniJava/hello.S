	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 13, 0	sdk_version 13, 0
	.globl	_print                          ## -- Begin function print
	.p2align	4, 0x90
_print:                                 ## @print
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movl	%edi, -4(%rbp)
	movl	-4(%rbp), %esi
	leaq	L_.str(%rip), %rdi
	movb	$0, %al
	callq	_printf
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_fibs                           ## -- Begin function fibs
	.p2align	4, 0x90
_fibs:                                  ## @fibs
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$32, %rsp
	movl	%edi, -4(%rbp)
	movl	$0, -12(%rbp)
	movl	$1, -16(%rbp)
	movl	$1, -8(%rbp)
LBB1_1:                                 ## =>This Inner Loop Header: Depth=1
	movl	-8(%rbp), %eax
	cmpl	-4(%rbp), %eax
	jge	LBB1_3
## %bb.2:                               ##   in Loop: Header=BB1_1 Depth=1
	movl	-12(%rbp), %eax
	addl	-16(%rbp), %eax
	movl	%eax, -20(%rbp)
	movl	-16(%rbp), %eax
	movl	%eax, -12(%rbp)
	movl	-20(%rbp), %eax
	movl	%eax, -16(%rbp)
	movl	-16(%rbp), %edi
	callq	_print
	movl	-8(%rbp), %eax
	addl	$1, %eax
	movl	%eax, -8(%rbp)
	jmp	LBB1_1
LBB1_3:
	movl	-16(%rbp), %eax
	addq	$32, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_equal                          ## -- Begin function equal
	.p2align	4, 0x90
_equal:                                 ## @equal
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	movl	%edi, -4(%rbp)
	movl	%esi, -8(%rbp)
	movl	-4(%rbp), %ecx
	xorl	%eax, %eax
                                        ## kill: def $al killed $al killed $eax
	cmpl	-8(%rbp), %ecx
	movb	%al, -9(%rbp)                   ## 1-byte Spill
	jl	LBB2_2
## %bb.1:
	movl	-8(%rbp), %eax
	cmpl	-4(%rbp), %eax
	setl	%al
	xorb	$-1, %al
	movb	%al, -9(%rbp)                   ## 1-byte Spill
LBB2_2:
	movb	-9(%rbp), %al                   ## 1-byte Reload
	andb	$1, %al
	movzbl	%al, %eax
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_test                           ## -- Begin function test
	.p2align	4, 0x90
_test:                                  ## @test
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movl	%edi, -4(%rbp)
	movl	%esi, -8(%rbp)
	movl	%edx, -12(%rbp)
	movl	-4(%rbp), %edi
	movl	-8(%rbp), %esi
	callq	_equal
	cmpl	$0, %eax
	je	LBB3_2
## %bb.1:
	movl	$0, -16(%rbp)
	jmp	LBB3_13
LBB3_2:
	movl	-4(%rbp), %eax
	cmpl	-8(%rbp), %eax
	jge	LBB3_5
## %bb.3:
	movl	-12(%rbp), %eax
	cmpl	-8(%rbp), %eax
	jl	LBB3_5
## %bb.4:
	movl	$111, -16(%rbp)
	jmp	LBB3_12
LBB3_5:
	movl	-4(%rbp), %eax
	cmpl	-8(%rbp), %eax
	jl	LBB3_7
## %bb.6:
	movl	$222, -16(%rbp)
	jmp	LBB3_11
LBB3_7:
	movl	-8(%rbp), %eax
	cmpl	-12(%rbp), %eax
	jl	LBB3_9
## %bb.8:
	movl	$333, -16(%rbp)                 ## imm = 0x14D
	jmp	LBB3_10
LBB3_9:
	movl	$444, -16(%rbp)                 ## imm = 0x1BC
LBB3_10:
	jmp	LBB3_11
LBB3_11:
	jmp	LBB3_12
LBB3_12:
	jmp	LBB3_13
LBB3_13:
	movl	-16(%rbp), %edi
	callq	_print
	movl	-16(%rbp), %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_main                           ## -- Begin function main
	.p2align	4, 0x90
_main:                                  ## @main
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movl	$0, -4(%rbp)
	movl	$20, -8(%rbp)
	movl	$10, %edi
	callq	_fibs
	movl	%eax, -8(%rbp)
	imull	$1000, -8(%rbp), %edi           ## imm = 0x3E8
	callq	_print
	movl	$100, %esi
	movl	$200, %edx
	movl	%esi, %edi
	callq	_test
	movl	%eax, -8(%rbp)
	movl	$100, %edi
	movl	$150, %esi
	movl	$200, %edx
	callq	_test
	movl	%eax, -8(%rbp)
	movl	$100, %edi
	movl	$10, %esi
	movl	$200, %edx
	callq	_test
	movl	%eax, -8(%rbp)
	movl	$100, %edi
	movl	$300, %esi                      ## imm = 0x12C
	movl	$200, %edx
	callq	_test
	movl	%eax, -8(%rbp)
	movl	-8(%rbp), %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.section	__TEXT,__cstring,cstring_literals
L_.str:                                 ## @.str
	.asciz	"%10d\n"

.subsections_via_symbols
