import json
from configparser import ConfigParser

def flatten_dict(prefix, data, result):
    for key, value in data.items():
        new_key = f"{prefix}.{key}" if prefix else key

        if isinstance(value, dict):
            flatten_dict(new_key, value, result)
        else:
            result[new_key] = value


def json_to_ini(json_file, ini_file):
    with open(json_file, "r", encoding="utf-8") as f:
        data = json.load(f)

    config = ConfigParser()

    for key, value in data.items():
        if isinstance(value, list):
            for idx, item in enumerate(value):
                section = f"{key}.{idx}"
                flat = {}
                flatten_dict("", item, flat)
                config[section] = {k: str(v) for k, v in flat.items()}

        elif isinstance(value, dict):
            flat = {}
            flatten_dict("", value, flat)
            config[key] = {k: str(v) for k, v in flat.items()}

        else:
            config["DEFAULT"][key] = str(value)

    with open(ini_file, "w", encoding="utf-8") as f:
        config.write(f)


if __name__ == "__main__":
    print("=== Конвертация из файла JSON -> INI (библиотека Configparser) ===")
    json_to_ini("data/schedule.json", "data/schedule-configparser.ini")
