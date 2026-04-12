# Author = Vasidov Muhammadsaid Abdufattokhovich
# Group = P3132
# Date = 2025-10-23

# Вариант - 507982 % 7 = 6

import re

def extract_id_values(html_text):
    """
    Функция для извлечения атрибута id из HTML-тегов.
    """
    
    # Поиск атрибута id=" " и id=' '
    pattern = r'\sid\s*=\s*["\']([^"\']*)["\']'
    # \s - наличие пробела
    # id - поиск атрибута
    # \s*=\s* - знак равенства с возможными пробелами вокруг
    # ["\'] — открывающая кавычка, одинарная или двойная
    # ([^"\']*) — любое количество символов, кроме кавычек (id)
    # ["\'] - закрывающая кавычка
    
    matches = re.findall(pattern, html_text, re.IGNORECASE)
    
    return matches

with open('isu-itmo.html', 'r', encoding='utf-8') as f:
    content = f.read()

for ids in extract_id_values(content):
    print(ids)