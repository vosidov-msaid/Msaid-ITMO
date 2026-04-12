import json
import xml.etree.ElementTree as ET
from xml.dom import minidom

def dict_to_xml(tag, d):
    elem = ET.Element(tag)
    for key, val in d.items():
        if isinstance(val, dict):
            child = dict_to_xml(key, val)
            elem.append(child)
        elif isinstance(val, list):
            for item in val:
                child = dict_to_xml(key, item) if isinstance(item, dict) else ET.Element(key)
                if not isinstance(item, dict):
                    child.text = str(item)
                elem.append(child)
        else:
            child = ET.Element(key)
            child.text = str(val)
            elem.append(child)
    return elem

def json_file_to_pretty_xml_file(json_file_path, xml_file_path, root_tag="root"):
    with open(json_file_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
    
    root = dict_to_xml(root_tag, data)
    xml_str = ET.tostring(root, encoding='utf-8')
    
    parsed = minidom.parseString(xml_str)
    pretty_xml_str = parsed.toprettyxml(indent="  ", encoding='utf-8')
    
    with open(xml_file_path, 'wb') as f:
        f.write(pretty_xml_str)

if __name__ == "__main__":
    print("=== Конвертация из файла JSON -> XML ===")
    json_file_to_pretty_xml_file('data/schedule.json', 'data/schedule.xml', root_tag='data')
