while True:
    bits = input("Введите 7 бит (например: 1011001): ")

    if len(bits) != 7 or any(b not in '01' for b in bits):
        print("Ошибка! Введите ровно 7 символов (0 и 1).")
    else:
        b = [int(bit) for bit in bits]

        cols = ['r1', 'r2', 'i1', 'r3', 'i2', 'i3', 'i4']
        r1 = b[0]
        r2 = b[1]
        i1 = b[2]
        r3 = b[3]
        i2 = b[4]
        i3 = b[5]
        i4 = b[6]

        s1 = r1 ^ i1 ^ i2 ^ i4
        s2 = r2 ^ i1 ^ i3 ^ i4
        s3 = r3 ^ i2 ^ i3 ^ i4
        synd = s1 + s2 * 2 + s3 * 4

        if synd != 0:
            print("> В сообщении ошибка!")
            print("Ошибка в символе", cols[synd - 1])
            b[synd - 1] = 1 - b[synd - 1]

        else:
            print("Ошибок не обнаружено.")

        print("Правильное сообщение (инфобиты):", f"{b[2]}{b[4]}{b[5]}{b[6]}")

    choice = input("Выполнить еще одну конвертацию? (y/n): ").strip().lower()
    if choice not in ['y', 'yes', 'д', 'да']:
        break
    print("\n")

