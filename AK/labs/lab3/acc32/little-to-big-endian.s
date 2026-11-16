; little_to_big_endian(n): reverse the byte order of a 32-bit word.
; Преобразование 32-разрядного целого числа из формата «little-endian» в формат «big-endian»

    .data

input_addr:      .word  0x80
output_addr:     .word  0x84
n:               .word  0x00
mask_ff:         .word  0xFF
shift_8:         .word  8
shift_16:        .word  16
shift_24:        .word  24
result:          .word  0x00
temp:            .word  0x00

    .text
    .org 0x90

_start:
    load         input_addr
    load_acc
    store        n

byte0_to_bits24:
    load         n
    and          mask_ff
    shiftl       shift_24
    store        result

byte1_to_bits16:
    load         n
    shiftr       shift_8
    and          mask_ff
    shiftl       shift_16
    store        temp
    load         result
    or           temp
    store        result

byte2_to_bits8:
    load         n
    shiftr       shift_16
    and          mask_ff
    shiftl       shift_8
    store        temp
    load         result
    or           temp
    store        result

byte3_to_bits0:
    load         n
    shiftr       shift_24
    and          mask_ff
    store        temp
    load         result
    or           temp
    store        result

done:
    load         result
    store_ind    output_addr
    halt
