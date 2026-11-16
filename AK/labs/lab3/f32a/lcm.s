\ lcm(a, b): least common multiple of two positive integers.
    .data

input_addr:   .word 0x80
output_addr:  .word 0x84
divisor_ptr:  .word 0x88      \ scratch RAM address used by +/ as mem[B]

a_val:        .word 0x00
b_val:        .word 0x00
wa:           .word 0x00      \ working copies, mutated by the gcd loop
wb:           .word 0x00
g_val:        .word 0x00

    .text
    .org 0x100                \ keep code clear of the IO cells and the divisor scratch cell

_start:
    @p input_addr a! @ @      \ two reads: first is a, second is b
    !p b_val
    !p a_val

    @p a_val inv 1 + -if domain_fail   \ negate + nonneg-test
    @p b_val inv 1 + -if domain_fail

    @p a_val !p wa
    @p b_val !p wb

gcd_loop:
    @p wb if gcd_done
    @p wa @p wb divmod drop
    @p wb !p wa                \ wa <- old wb
    !p wb
    gcd_loop ;

gcd_done:
    @p wa !p g_val

    @p a_val @p g_val divmod swap drop  \ divmod leaves [q,r]
    @p b_val
    mul_sat

    @p output_addr a! !
    halt

domain_fail:
    -1
    @p output_addr a! !
    halt

    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

swap:
    over >r >r drop r> r>
    ;

    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

divmod:
    @p divisor_ptr b!
    !b
    a!
    0 0
    31 >r
divmod_loop:
    +/
    next divmod_loop
    ;

    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


mul_sat:
    a!
    0
    31 >r
mul_sat_loop:
    +*
    next mul_sat_loop
    swap drop                  \ +* pushes S then T, so T ends up on top, not S
    if mul_sat_checklow
    0xCCCCCCCC
    mul_sat_done ;
mul_sat_checklow:
    a                          \ mres-low; its sign bit decides overflow since mres-high was 0
    dup -if mul_sat_ok
    drop 0xCCCCCCCC
    mul_sat_done ;
mul_sat_ok:
mul_sat_done:
    ;
