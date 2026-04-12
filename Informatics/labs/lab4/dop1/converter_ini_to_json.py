import json

def parse_value(value):
    """Преобразует строку в правильный тип"""
    if value.lower() == 'true':
        return True
    if value.lower() == 'false':
        return False
    try:
        return int(value)
    except ValueError:
        return value

def ini_to_json(ini_path, json_path):
    data = {}
    schedule = []
    cur_section = None
    cur_item = {}
    
    with open(ini_path, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            
            if not line:
                continue
            
            if line.startswith('[') and line.endswith(']'):
                if cur_section and cur_section.startswith('schedule.'):
                    schedule.append(cur_item)
                    cur_item = {}
                cur_section = line[1:-1]
                continue
            
            # Ключ-значение
            if '=' in line:
                key, value = line.split('=', 1)
                key = key.strip()
                value = value.strip()
                
                parsed_value = parse_value(value)
                
                if cur_section == 'DEFAULT':
                    data[key] = parsed_value
                
                # Если секция расписание
                elif cur_section and cur_section.startswith('schedule.'):
                    # Проверяет, есть ли точка в ключ
                    if '.' in key:
                        parent, child = key.split('.', 1)
                        # Проверяет создан ли такой ключ
                        if parent not in cur_item:
                            cur_item[parent] = {}
                        cur_item[parent][child] = parsed_value
                    else:
                        cur_item[key] = parsed_value
    
    if cur_item:
        schedule.append(cur_item)
    
    data['schedule'] = schedule
    
    with open(json_path, 'w', encoding='utf-8') as f:
        json.dump(data, f, ensure_ascii=False, indent=4)
    
    print(f"{ini_path} -> {json_path}")

if __name__ == "__main__":
    print("=== Конвертация из файла INI -> JSON ===")
    ini_to_json('data/schedule-mycode.ini', 'data/schedule-mycode.json')