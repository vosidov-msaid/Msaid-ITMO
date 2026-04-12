chcp 65001
javac -encoding UTF-8 -d ./out/ Main.java model/*.java enum/*.java exceptions/*.java util/*.java
java -cp out Main

# Export to JAR
jar cfe Shorty.jar Main -C out .
java -jar Shorty.jar