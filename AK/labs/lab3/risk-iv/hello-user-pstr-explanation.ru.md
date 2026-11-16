# Объяснение `hello-user-pstr.s` (RISC-IV-32, вариант `hello_user_pstr`)

## Идея задачи

Программа спрашивает имя пользователя, читает строку до `\n`, и либо печатает
`Hello, <имя>!` в виде **Pascal-строки** (байт длины + данные), либо, если имя
длиннее лимита — пишет «мусорное» значение `0xCCCCCCCC` в порт вывода
(сигнал переполнения).

## Секция `.data` (строки 1–11)

```asm
greet_buf:    .byte  '________________________________'
input_addr:   .word  0x80
output_addr:  .word  0x84
overflow_val: .word  0xCCCCCCCC
question:     .byte  'What is your name?\n'
hello_pfx:    .byte  'Hello, '
name_buf:     .byte  '______________________'
```

- `greet_buf` — буфер под итоговую Pascal-строку, зарезервирован с запасом
  (подчёркивания — просто заполнитель, перезаписывается при выполнении).
- `input_addr` / `output_addr` — это *указатели на указатели*: по адресу
  `0x80` лежит адрес самого порта ввода, а не сами данные — двухуровневая
  адресация memory-mapped I/O в Wrench.
- `overflow_val` — «магическая» константа-маркер переполнения.
- `question` — текст вопроса, 20 символов с `\n` (в коде используется длина 19,
  см. ниже).
- `hello_pfx` — префикс `"Hello, "`, 7 байт.
- `name_buf` — сырой буфер для введённого имени, 22 байта.

## `_start` (строки 16–36)

```asm
addi sp, zero, 0x600
```
Инициализация стека — вершина для `jal`/`ra`-вызовов (нужно из-за рекурсии в
`print_str`).

```asm
lui  a0, %hi(question)
addi a0, a0, %lo(question)
addi a1, zero, 19
jal  ra, print_str
```
Печатает вопрос `"What is your name?\n"` через `print_str(a0=ptr, a1=len=19)`.

```asm
lui  a0, %hi(name_buf)
addi a0, a0, %lo(name_buf)
addi a1, zero, 22
jal  ra, read_name
mv   t0, a0                 ; t0 <- длина имени, либо -1 при переполнении
```
Читает имя в `name_buf`, максимум 22 символа. `read_name` возвращает в `a0`
либо фактическую длину, либо `-1`.

```asm
addi t1, zero, -1
beq  t0, t1, main_overflow   ; если -1 — переполнение, уходим в обработчик
```

## Сборка результирующей Pascal-строки (строки 33–68)

```asm
addi t2, t0, 8               ; t2 = len(имя) + len("Hello, ") + len("!")
lui  t3, %hi(greet_buf)
addi t3, t3, %lo(greet_buf)
sb   t2, 0(t3)                ; greet_buf[0] <- длина всей строки (Pascal string)
addi t3, t3, 1                ; t3 теперь указывает на начало данных
```

`t2 = len(имя) + 7 + 1` — длина `"Hello, "` (7) плюс `"!"` (1).

Далее два цикла копирования байт за байтом.

**Копирование префикса `"Hello, "`** (`main_copy_prefix`, 7 байт):
```asm
main_copy_prefix:
    beqz t5, main_copy_name_init  ; t5 — счётчик, изначально 7
    lb   t6, 0(t4)                 ; t4 — указатель на hello_pfx
    sb   t6, 0(t3)
    addi t3, t3, 1
    addi t4, t4, 1
    addi t5, t5, -1
    j    main_copy_prefix
```

**Копирование имени** (`main_copy_name`, `t0` байт — реальная длина имени):
```asm
main_copy_name_init:
    lui t4, %hi(name_buf)
    addi t4, t4, %lo(name_buf)
    mv  t5, t0                     ; t5 <- длина имени

main_copy_name:
    beqz t5, main_copy_name_done
    lb   t6, 0(t4)
    sb   t6, 0(t3)
    addi t3, t3, 1
    addi t4, t4, 1
    addi t5, t5, -1
    j    main_copy_name
```
Тот же паттерн цикла, что и для префикса.

**Завершение** (`main_copy_name_done`):
```asm
addi t6, zero, '!'
sb   t6, 0(t3)                     ; дописываем '!' в конец
```

Печать результата:
```asm
lui  a0, %hi(greet_buf)
addi a0, a0, %lo(greet_buf)
addi a0, a0, 1                      ; пропускаем байт длины — печатаем только текст
mv   a1, t2                         ; длина = len(имя)+8
jal  ra, print_str
halt
```

## Обработчик переполнения (`main_overflow`, строки 78–86)

```asm
lui t0, %hi(overflow_val)
addi t0, t0, %lo(overflow_val)
lw  t0, 0(t0)                       ; t0 <- 0xCCCCCCCC

lui t1, %hi(output_addr)
addi t1, t1, %lo(output_addr)
lw  t1, 0(t1)                       ; t1 <- реальный адрес порта вывода

sw  t0, 0(t1)                       ; пишем 0xCCCCCCCC прямо в порт вывода
halt
```
При переполнении в порт вывода попадает не текст, а константа-маркер ошибки —
это и проверяет тестовый сценарий `hello-user-pstr-overflow.yaml`.

## Вспомогательные функции

### `print_str(a0=ptr, a1=len)` (строки 88–105)

Рекурсивно печатает `len` байт, начиная с `ptr`:

```asm
print_str:
    addi sp, sp, -4
    sw   ra, 0(sp)               ; сохраняем адрес возврата

    beqz a1, print_str_ret        ; len == 0 -> выход
    lb   t0, 0(a0)
    mv   a2, t0
    jal  ra, putchar               ; печатаем текущий байт

    addi a0, a0, 1
    addi a1, a1, -1
    jal  ra, print_str             ; рекурсивный вызов для остатка

print_str_ret:
    lw   ra, 0(sp)
    addi sp, sp, 4
    jalr zero, ra, 0
```
Рекурсия не хвостовая по реализации — каждый символ добавляет кадр в стек
(4 байта на сохранённый `ra`), поэтому глубина стека равна длине строки.

### `putchar(a2=char)` (строки 108–113)

```asm
putchar:
    lui t0, %hi(output_addr)
    addi t0, t0, %lo(output_addr)
    lw  t0, 0(t0)                  ; t0 <- адрес порта вывода
    sb  a2, 0(t0)                   ; пишем один символ
    jalr zero, ra, 0
```

### `getchar()` (строки 116–121)

```asm
getchar:
    lui t0, %hi(input_addr)
    addi t0, t0, %lo(input_addr)
    lw  t0, 0(t0)                  ; t0 <- адрес порта ввода
    lw  a0, 0(t0)                  ; читаем слово (не байт!) из порта
    jalr zero, ra, 0
```

### `read_name(a0=dest, a1=max_len)` (строки 124–153)

Ядро логики ввода — читает символы, пока не встретит `\n`, или пока не
превышен лимит:

```asm
read_name:
    addi sp, sp, -4
    sw   ra, 0(sp)

    mv   t1, a0                    ; t1 <- указатель записи
    mv   t2, a1                    ; t2 <- максимальная длина
    addi t3, zero, 0               ; t3 <- счётчик символов

read_name_loop:
    jal  ra, getchar
    mv   t4, a0
    addi t5, zero, 10              ; '\n'
    beq  t4, t5, read_name_done     ; встретили перевод строки -> готово

    bge  t3, t2, read_name_overflow ; count >= max_len, а это не '\n' -> overflow

    sb   t4, 0(t1)
    addi t1, t1, 1
    addi t3, t3, 1
    j    read_name_loop

read_name_overflow:
    addi a0, zero, -1
    j    read_name_ret

read_name_done:
    mv   a0, t3

read_name_ret:
    lw   ra, 0(sp)
    addi sp, sp, 4
    jalr zero, ra, 0
```

**Важный нюанс.** Проверка `bge t3, t2, read_name_overflow` стоит **после**
проверки на `\n`, поэтому:

- если введено ровно `max_len` символов, за которыми следует `\n` — это
  валидный случай (не overflow);
- если `max_len`-й введённый символ — не `\n`, а продолжение имени — это уже
  переполнение.

Отсюда и два разных тестовых сценария:
- `hello-user-pstr-maxlen.yaml` — имя ровно 22 символа, проходит;
- `hello-user-pstr-overflow.yaml` — 23+ символов, ловится как переполнение.

## Итоговая логика программы

1. Напечатать вопрос `"What is your name?\n"`.
2. Прочитать имя посимвольно до `\n`, максимум 22 символа (`read_name`).
3. Если уложились в лимит — собрать строку `"Hello, <имя>!"` как
   Pascal-строку (байт длины + тело) и напечатать тело (`main_copy_prefix` +
   `main_copy_name`).
4. Если лимит превышен — записать `0xCCCCCCCC` в порт вывода как индикатор
   ошибки (`main_overflow`).
