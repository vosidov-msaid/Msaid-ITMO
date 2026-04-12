#!/bin/bash
rm -rf out && mkdir out
javac -encoding UTF-8 -cp "lib/Pokemon.jar:src" -d out $(find src -name "*.java") || exit 1
echo -e "Manifest-Version: 1.0\nMain-Class: Main\nClass-Path: lib/Pokemon.jar" > out/MANIFEST.MF
jar cfm project.jar out/MANIFEST.MF -C out .
echo "Создан project.jar"