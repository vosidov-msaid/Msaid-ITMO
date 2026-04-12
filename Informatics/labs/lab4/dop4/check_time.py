import subprocess
import time

print("======== CONVERTER FILE JSON -> INI ========\n")

print("My converter: \n")
start1 = time.time()
subprocess.run(['python', 'main/converter_json_to_ini.py'])
finish1 = time.time()
print("TIME: " + str(finish1 - start1) + "\n")

print("Library Configparser: \n")
start1 = time.time()
subprocess.run(['python', 'dop2/json_to_ini.py'])
finish1 = time.time()
print("TIME: " + str(finish1 - start1) + "\n\n")

print("======== CONVERTER FILE INI -> JSON ========\n")

print("My converter: \n")
start1 = time.time()
subprocess.run(['python', 'dop1/converter_ini_to_json.py'])
finish1 = time.time()
print("TIME: " + str(finish1 - start1) + "\n")

print("Library Configparser: \n")
start1 = time.time()
subprocess.run(['python', 'dop2/ini_to_json.py'])
finish1 = time.time()
print("TIME: " + str(finish1 - start1))