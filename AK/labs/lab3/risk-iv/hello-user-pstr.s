; буфер приветствия (Pascal string), адрес 0x00, заполнен '_'

    .data

greet_buf:    .byte  '________________________________'
input_addr:   .word  0x80
output_addr:  .word  0x84
overflow_val: .word  0xCCCCCCCC
question:     .byte  'What is your name?\n'
hello_pfx:    .byte  'Hello, '
name_buf:     .byte  '______________________'          ; сырой буфер имени пользователя

    .text
    .org     0x90                                       ; код за зоной memory-mapped IO

_start:
    addi     sp, zero, 0x600                             ; вершина стека вызовов

    lui      a0, %hi(question)
    addi     a0, a0, %lo(question)
    addi     a1, zero, 19                                ; длина "What is your name?\n"
    jal      ra, print_str

    lui      a0, %hi(name_buf)
    addi     a0, a0, %lo(name_buf)
    addi     a1, zero, 22                                ; максимальная длина имени
    jal      ra, read_name
    mv       t0, a0                                      ; t0 <- длина имени или -1

    addi     t1, zero, -1
    beq      t0, t1, main_overflow

    addi     t2, t0, 8                                   ; len("Hello, " + "!") == 8
    lui      t3, %hi(greet_buf)
    addi     t3, t3, %lo(greet_buf)
    sb       t2, 0(t3)                                   ; greet_buf[0] <- длина строки
    addi     t3, t3, 1

    lui      t4, %hi(hello_pfx)
    addi     t4, t4, %lo(hello_pfx)
    addi     t5, zero, 7

main_copy_prefix:
    beqz     t5, main_copy_name_init
    lb       t6, 0(t4)
    sb       t6, 0(t3)
    addi     t3, t3, 1
    addi     t4, t4, 1
    addi     t5, t5, -1
    j        main_copy_prefix

main_copy_name_init:
    lui      t4, %hi(name_buf)
    addi     t4, t4, %lo(name_buf)
    mv       t5, t0

main_copy_name:
    beqz     t5, main_copy_name_done
    lb       t6, 0(t4)
    sb       t6, 0(t3)
    addi     t3, t3, 1
    addi     t4, t4, 1
    addi     t5, t5, -1
    j        main_copy_name

main_copy_name_done:
    addi     t6, zero, '!'
    sb       t6, 0(t3)

    lui      a0, %hi(greet_buf)
    addi     a0, a0, %lo(greet_buf)
    addi     a0, a0, 1                                   ; пропускаем байт длины
    mv       a1, t2
    jal      ra, print_str

    halt

main_overflow:
    lui      t0, %hi(overflow_val)
    addi     t0, t0, %lo(overflow_val)
    lw       t0, 0(t0)
    lui      t1, %hi(output_addr)
    addi     t1, t1, %lo(output_addr)
    lw       t1, 0(t1)
    sw       t0, 0(t1)
    halt

; print_str(a0=ptr, a1=len) -- рекурсивно печатает len байт из ptr
print_str:
    addi     sp, sp, -4
    sw       ra, 0(sp)

    beqz     a1, print_str_ret
    lb       t0, 0(a0)
    mv       a2, t0
    jal      ra, putchar

    addi     a0, a0, 1
    addi     a1, a1, -1
    jal      ra, print_str

print_str_ret:
    lw       ra, 0(sp)
    addi     sp, sp, 4
    jalr     zero, ra, 0

; putchar(a2=char) -- пишет один символ в порт вывода
putchar:
    lui      t0, %hi(output_addr)
    addi     t0, t0, %lo(output_addr)
    lw       t0, 0(t0)
    sb       a2, 0(t0)
    jalr     zero, ra, 0

; getchar() -- читает один символ из порта ввода в a0
getchar:
    lui      t0, %hi(input_addr)
    addi     t0, t0, %lo(input_addr)
    lw       t0, 0(t0)
    lw       a0, 0(t0)
    jalr     zero, ra, 0

; read_name(a0=dest, a1=max_len) -- читает строку до '\n'; a0 <- длина или -1 при переполнении
read_name:
    addi     sp, sp, -4
    sw       ra, 0(sp)

    mv       t1, a0                                      ; t1 <- указатель записи
    mv       t2, a1                                      ; t2 <- максимальная длина
    addi     t3, zero, 0                                 ; t3 <- счётчик символов

read_name_loop:
    jal      ra, getchar
    mv       t4, a0
    addi     t5, zero, 10                                ; '\n'
    beq      t4, t5, read_name_done
    bge      t3, t2, read_name_overflow                   ; count >= max_len, а это не '\n'
    sb       t4, 0(t1)
    addi     t1, t1, 1
    addi     t3, t3, 1
    j        read_name_loop

read_name_overflow:
    addi     a0, zero, -1
    j        read_name_ret

read_name_done:
    mv       a0, t3

read_name_ret:
    lw       ra, 0(sp)
    addi     sp, sp, 4
    jalr     zero, ra, 0
