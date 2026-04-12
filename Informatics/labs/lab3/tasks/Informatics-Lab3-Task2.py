# Author = Vasidov Muhammadsaid Abdufattokhovich
# Group = P3132
# Date = 2025-10-23

# Вариант - 507982 % 5 = 2
import re

def transform_numbers(text: str) -> str:
    """
    Заменяет все целые числа в тексте на f(x) = x^3 - 13.
    Правило: находим числа, которые не являются частью слова.
    """
    pat = re.compile(r'(?<!\w)(\d+)(?!\w)')
    # (?<!\w) - проверяет, перед числом нет символа, который является частью слова
    # \d+ — одна или более цифр
    # (?!\w) - проверяет, после числа также не стоит символ слова
    
    def repl(m):
        s = m.group(1)
        x = int(s)
        return str(x**3 - 13)
    
    return pat.sub(repl, text)


tests = ["15 + 22 = 37", "Numbers: -3, 0, +4.", "file123.txt and 007 and -0005", 
        "Mix: x=-2;y=10z;100%", "Very big: 12345678901234567890", "No numbers here!"]

print("=== РЕЗУЛЬТАТЫ ТЕСТОВ ===\n")
for inp in range(len(tests)):
    out = transform_numbers(tests[inp])
    print(f"Test {inp+1}")
    print("Вход: ", tests[inp])
    print("Выход:", out)
    print("-" * 40)

while True:
    inp = input("Введите текст (или 'q' для выхода): ")
    if inp.lower() == 'q':
        break
    res = transform_numbers(inp)
    print("Преобразованный текст:", res)
    print("-" * 40)