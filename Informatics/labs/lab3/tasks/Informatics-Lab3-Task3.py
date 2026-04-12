import re

def change_adjective_cases_simple(text, target):
    # Разделяем текст на слова и знаки препинания
    w = re.findall(r'\w+|[^\w\s]', text, re.UNICODE)

    if target < 1 or target > len(w):
        print("Номер слова выходит за пределы текста")

    # Регулярка для прилагательных (по типичным русским окончаниям)
    adj_pat = re.compile(r'(ый|ий|ая|ое|ые|ого|его|ому|ему|ым|им|ом|ем|ой|ей)$', re.IGNORECASE)

    # Ищем прилагательное перед целевым словом
    adj_ind = None
    for i in range(target - 2, -1, -1):
        if adj_pat.search(w[i]):
            adj_ind = i
            break
    
    # Если нет прилагательных, то возвращает проста текст
    if adj_ind is None:
        return text

    target_adj = w[adj_ind]
    match = adj_pat.search(target_adj)
    if not match:
        return text

    ending = match.group(1)
    stem = target_adj[:-len(ending)]
    base = stem.lower()

    # Меняем все похожие прилагательные
    new_w = []
    for w in w:
        lw = w.lower()
        if lw.startswith(base) and adj_pat.search(lw) and lw != target_adj.lower():
            w_new = adj_pat.sub(ending, w)
            if w[0].isupper():
                w_new = w_new.capitalize()
            new_w.append(w_new)
        else:
            new_w.append(w)

    # Восстанавливаем пробелы
    result = ''
    for i, w in enumerate(new_w):
        if i == 0:
            result += w
        else:
            prev = new_w[i - 1]
            if re.match(r'\w', w) and re.match(r'\w', prev):
                result += ' ' + w
            elif w in '»)]':
                result += w
            elif w in ',.!?;:–':
                result += w
            elif prev in '«([„':
                result += w
            else:
                result += ' ' + w

    return result


# Тест 1
print("=== ТЕСТ 1 ===\n")
text = ("Футбольный клуб «Реал Мадрид» является 15-кратным обладателем главного футбольного "
        "европейского трофея – Лиги Чемпионов. Данный турнир организован Союзом европейских "
        "футбольных ассоциаций (УЕФА). Идея о континентальном футбольном турнире пришла к "
        "журналисту Габриэлю Ано в 1955 году.")

print(change_adjective_cases_simple(text, 2))
print("------------------------------------")


while True:
    num = int(input("Номер: "))
    inp = input("Введите текст (или 'q' для выхода): ")
    if inp.lower() == 'q':
        break
    res = change_adjective_cases_simple(inp, num)
    print("Преобразованный текст:", res)
    print("-" * 40)