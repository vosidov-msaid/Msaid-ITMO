import json

def escape_value(value):
    """Преобразует любую тип значению в строку"""
    if isinstance(value, bool):
        return 'true' if value else 'false'
    return str(value)

def json_to_ini(json_path, ini_path):
    f = open(json_path, 'r', encoding='utf-8')
    data = json.load(f)

    lines = []
    
    lines.append("[DEFAULT]")

    # из JSON выделить только key-value значении 
    for key, value in data.items():
        if key != 'schedule':
            lines.append(f"{key} = {escape_value(value)}")
    lines.append("")

    # Проходит через список schedule
    for i, item in enumerate(data.get('schedule', [])):
        lines.append(f"[schedule.{i}]")
        for k, v in item.items():
            # Если внутри списка есть словарь
            if isinstance(v, dict):
                for sub_k, sub_v in v.items():
                    lines.append(f"{k}.{sub_k} = {escape_value(sub_v)}")
            else:
                lines.append(f"{k} = {escape_value(v)}")
        lines.append("")

    with open(ini_path, 'w', encoding='utf-8') as f:
        f.write("\n".join(lines))

    print(f"{json_path} -> {ini_path}")


if __name__ == "__main__":
    print("=== Конвертация из файла JSON -> INI ===")
    json_to_ini('data/schedule.json', 'data/schedule-mycode.ini')