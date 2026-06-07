def show_encodings(text: str):
    koi8 = text.encode('koi8-r')
    utf8 = text.encode('utf-8')
    utf16_be = text.encode('utf-16-be')

    def to_hex(bs: bytes) -> str:
        return ' '.join(f'{b:02X}' for b in bs)

    print(f'Исходный текст: {text}')
    print(f'KOI8-R : {to_hex(koi8)}')
    print(f'UTF-8  : {to_hex(utf8)}')
    print(f'UTF-16: {to_hex(utf16_be)}')

while True:
    word = input("> ")
    show_encodings(word)