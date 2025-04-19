#!/bin/bash
# compilami.sh (C) Papadopol Lucian Ioan 2025 All Rights reserved CC BY-NC-ND 3.0 IT
# Questo script compila l'intero progetto Java "BattleShip AI".
# I file di test presenti in src/test/ sono esclusi.
# I file compilati sono nella cartella bin/.
#
# Istruzioni per l'uso:
# 1. Java oppure OpenJDK devono essere installati.
# 2. Apri il terminale e naviga nella directory in cui si trova compilami.sh
# 3. Rendi lo script eseguibile con il comando:
#       chmod 777 compilami.sh
# 4. Esegui lo script con:
#       ./compilami.sh
#
# Lo script esegue i seguenti passaggi:
#   - Elimina se esiste la cartella bin/ per una pulizia completa della compilazione precedente.
#   - Crea la cartella bin/ dove salvare i file compilati.
#   - Trova e compila tutti i file .java presenti nella cartella src/, escludendo quelli in src/test/.


echo "Pulizia della cartella bin/ se esistente..."
rm -rf bin
mkdir -p bin

echo "Compilazione dei file Java dal percorso src/..."

javac -d bin $(find src -type f -name "*.java" ! -path "src/test/*")
COMPILATION_RESULT=$?

if [ $COMPILATION_RESULT -eq 0 ]; then
    echo "Compilazione completata con successo."
else
    echo "Compilazione fallita. Controlla gli errori sopra riportati."
    exit 1
fi
