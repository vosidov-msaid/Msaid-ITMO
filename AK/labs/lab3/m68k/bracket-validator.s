; bracket_validator: проверка вложенности (), [], {}; результат 1 / -1

    .data

input_addr:        .word  0x80          ; адрес порта ввода
output_addr:       .word  0x84          ; адрес порта вывода
call_stack_top:    .word  0x600         ; вершина стека вызовов (A7)
bracket_stack_top: .word  0x400         ; вершина стека скобок (A2)

    .text
    .org     0x90                       ; код размещаем за зоной memory-mapped IO

_start:
    movea.l  call_stack_top, A7         ; Скопировать значение call_stack_top в A7
    movea.l  (A7), A7

    movea.l  input_addr, A0             ; Скопировать значение input_addr в A0
    movea.l  (A0), A0

    movea.l  output_addr, A1            ; Скопировать значение output_addr в A1
    movea.l  (A1), A1

    movea.l  bracket_stack_top, A2      ; Скопировать значение bracket_stack_top в A2
    movea.l  (A2), A2

    move.l   0, D5                      ; глубина стека скобок
    move.l   0, D6                      ; флаг несовпадения
    move.l   0, D7                      ; счётчик прочитанных символов строки

read_loop:
    cmp.l    0x40, D7                   ; лимит длины строки (buf_size)
    bge      overflow

    move.l   (A0), D0                   ; следующий символ входного потока
    cmp.l    10, D0                     ; '\n' -- конец строки
    beq      finish

    add.l    1, D7
    jsr      process_char
    jmp      read_loop

finish:
    cmp.l    0, D6
    bne      invalid
    cmp.l    0, D5                      ; остались непарные открывающие скобки
    bne      invalid

    move.l   1, D2
    jmp      write_result

invalid:
    move.l   -1, D2
    jmp      write_result

overflow:
    move.l   0xCCCCCCCC, D2

write_result:
    move.l   D2, (A1)
    halt

; process_char: классифицирует символ D0 и вызывает push_open / check_close
process_char:
    cmp.l    '(', D0
    beq      process_char_open
    cmp.l    '[', D0
    beq      process_char_open
    cmp.l    '{', D0
    beq      process_char_open
    cmp.l    ')', D0
    beq      process_char_close
    cmp.l    ']', D0
    beq      process_char_close
    cmp.l    '}', D0
    beq      process_char_close
    rts

process_char_open:
    jsr      push_open
    rts

process_char_close:
    jsr      check_close
    rts

; push_open: кладёт открывающую скобку D0 на стек скобок
push_open:
    move.l   D0, -(A2)
    add.l    1, D5
    rts

; check_close: снимает со стека скобку и сверяет пару с D0
check_close:
    cmp.l    0, D5
    bne      check_close_pop
    move.l   1, D6                      ; закрывающая скобка без пары
    rts

check_close_pop:
    move.l   (A2)+, D1
    sub.l    1, D5

    cmp.l    ')', D0
    bne      check_close_bracket
    cmp.l    '(', D1
    beq      check_close_ok
    jmp      check_close_bad

check_close_bracket:
    cmp.l    ']', D0
    bne      check_close_brace
    cmp.l    '[', D1
    beq      check_close_ok
    jmp      check_close_bad

check_close_brace:
    cmp.l    '{', D1                    ; D0 здесь гарантированно '}'
    beq      check_close_ok

check_close_bad:
    move.l   1, D6

check_close_ok:
    rts
