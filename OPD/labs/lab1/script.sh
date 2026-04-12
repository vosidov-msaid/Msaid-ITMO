#!/bin/bash

chmod -R 700 lab0 && rm -rf lab0

mkdir lab0; cd lab0

# Step 1
echo -e '-- Step 1\n...'

touch politoed0 rufflet7 trapinch2

mkdir -p mightyena5/pansage
mkdir -p solosis9/graveler
mkdir -p stunky8/slowpoke
mkdir stunky8/horsea
touch mightyena5/krabby
touch mightyena5/vanillite
touch mightyena5/gible
touch solosis9/pidgeot solosis9/blaziken solosis9/lotad stunky8/liepard

echo -e 'Способности  Torrent Hyper Cutter Shell\nArmor' > mightyena5/krabby
echo 'Способности  Icicle Spear Harden Astonish Uproar Icy' > mightyena5/vanillite
echo 'Wind Mist Avalanche Taunt Mirror Shot Acid Armor Ice Beam Hail Mirror' >> mightyena5/vanillite
echo 'Coat Blizzard Sheer Cold' >> mightyena5/vanillite
echo 'Живет  Cave' > mightyena5/gible
echo 'Desert' >> mightyena5/gible
echo -e 'Способности  Torrent Damp Water Absorb' > politoed0
echo -e 'Тип\nпокемона  NORMAL FLYING' > rufflet7
echo -e 'Живет  Forest' > solosis9/pidgeot
echo -e 'Тип диеты\nOmnivore' > solosis9/blaziken
echo -e 'Тип диеты  Herbivore' > solosis9/lotad
echo -e 'weight=82.7 height=43.0\natk=9 def=5' > stunky8/liepard
echo 'Возможности  Overland=6 Burrow=8 Jump=1 Power=1' > trapinch2
echo 'Intelligence=3 Sinker=0' >> trapinch2

echo -e '-- Step 1 completed!\n========================'

# Step 2
echo -e '-- Step 2\n...'

# u - user
# g - group
# o - other
# r - 4
# w - 2
# x - 1

chmod u=wx,g=rw,o=x mightyena5
chmod u=rx,g=x,o=w mightyena5/pansage
chmod 066 mightyena5/krabby
chmod 620 mightyena5/vanillite
chmod u=rw,go= mightyena5/gible
chmod 664 politoed0
chmod 006 rufflet7
chmod 330 solosis9
chmod a=r solosis9/pidgeot
chmod 335 solosis9/graveler
chmod 004 solosis9/blaziken
chmod u=r,g=,o=r solosis9/lotad
chmod 317 stunky8
chmod u=rwx,g=wx,o=rwx stunky8/slowpoke
chmod ug=rx,o=x stunky8/horsea
chmod 062 stunky8/liepard
chmod 664 trapinch2

echo -e '-- Step 2 completed!\n========================'

# Step 3
echo -e '-- Step 3\n...'

chmod 761 mightyena5        # было 361

chmod 466 mightyena5/krabby # было 066

chmod 730 solosis9          # было 330

chmod 735 solosis9/graveler  # было 335
chmod 404 solosis9/blaziken  # было 004

ln -s stunky8 Copy_39
cat mightyena5/krabby solosis9/lotad > politoed0_22
cat politoed0 > mightyena5/giblepolitoed

cp politoed0 stunky8/slowpoke/
cp -r solosis9 stunky8/slowpoke/
ln trapinch2 mightyena5/gibletrapinch
ln -s politoed0 stunky8/liepardpolitoed

echo -e '-- Step 3 completed!\n========================'

echo -e '--- Step 4\n...'
chmod 717 stunky8

# 4.1

for f in **/*e; do [ -f "$f" ] && wc -l "$f"; done > /tmp/error1.txt
echo -e "\nSORT-1: Записан в файл /tmp/error1.txt"

# 4.2
echo -e "\nSORT-2:"
ls -l $(grep -rl "li" ./ 2>/dev/null) 2>/dev/null | sort -k6,7r

# 4.3
echo -e "\nSORT-3:"
chmod 406 rufflet7

cat -n rufflet7 | grep -i "re"

chmod 006 rufflet7

# 4.4
echo -e "\nSORT-4:"
ls -Rp solosis9 | grep -v "/$" | grep -v ":$" | sort

# 4.5
echo "$(cat politoed0 | wc -m)" >> politoed0
echo -e "\nSORT-5: $(cat politoed0)"

# 4.6
echo -e "\nSORT-6:"

chmod 762 stunky8/liepard

for f in $(ls **/l* | sort); do [ -f "$f" ] && echo "=== $f ===" && cat -n "$f"; done
chmod 062 stunky8/liepard

echo -e '-- Step 4 completed!\n========================'

# Step 5
echo -e '-- Step 5\n...'

rm politoed0
rm mightyena5/gible
rm Copy_*
rm mightyena5/gibletrapin*
rm -rf solosis9
rmdir mightyena5/pansage

echo -e '-- Step 5 completed!\n========================'