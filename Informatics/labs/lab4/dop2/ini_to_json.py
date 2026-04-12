import json
from configparser import ConfigParser

def parse_value(val):
    val = val.strip()

    if val.lower() == "true":
        return True
    if val.lower() == "false":
        return False

    if val.lstrip('-').isdigit():
        try:
            return int(val)
        except ValueError:
            pass

    try:
        return float(val)
    except ValueError:
        pass

    return val


def insert_nested(data, key, value):
    parts = key.split(".")
    cur = data
    for p in parts[:-1]:
        if p not in cur or not isinstance(cur[p], dict):
            cur[p] = {}
        cur = cur[p]
    cur[parts[-1]] = value


def ini_to_json(ini_file, json_file):
    config = ConfigParser()
    config.read(ini_file, encoding="utf-8")

    result = {}

    for key, value in config.defaults().items():
        result[key] = parse_value(value)

    sections_by_group = {}

    raw_sections = getattr(config, "_sections", {})

    for section, raw_map in raw_sections.items():
        clean_map = {k: v for k, v in raw_map.items() if k != "__name__"}

        if "." in section and section.rsplit(".", 1)[1].isdigit():
            name, idx = section.rsplit(".", 1)
            idx = int(idx)
            sections_by_group.setdefault(name, {})[idx] = clean_map
        else:
            result[section] = clean_map

    for name, elems in sections_by_group.items():
        items = []
        for idx in sorted(elems.keys()):
            raw_entry = elems[idx]
            entry = {}
            for k, v in raw_entry.items():
                insert_nested(entry, k, parse_value(v))
            items.append(entry)
        result[name] = items

    for key, section in list(result.items()):
        if isinstance(section, dict):
            new_section = {}
            for k, v in section.items():
                insert_nested(new_section, k, parse_value(v))
            result[key] = new_section

    with open(json_file, "w", encoding="utf-8") as f:
        json.dump(result, f, indent=4, ensure_ascii=False)


if __name__ == "__main__":
    print("=== Конвертация из файла INI -> JSON (библиотека Configparser) ===")
    ini_to_json("data/schedule-configparser.ini", "data/schedule-configparser.json")
