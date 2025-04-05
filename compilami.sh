#!/bin/bash
# compilami.sh
# Questo script compila l'intero progetto Java "BattleShip AI" a partire dalla cartella src/,
# escludendo i file di test presenti in src/test/.
# I file compilati vengono depositati nella cartella bin/.
#
# Istruzioni per l'uso:
# 1. Assicurati di avere installato Java (versione 8 o superiore).
# 2. Posiziona questo script nella cartella principale del progetto o nella cartella src/.
# 3. Apri il terminale e naviga nella directory in cui si trova compilami.sh.
# 4. Rendi lo script eseguibile con il comando:
#       chmod +x compilami.sh
# 5. Esegui lo script con:
#       ./compilami.sh
#
# Lo script esegue i seguenti passaggi:
#   - Elimina (se esiste) la cartella bin/ per una pulizia completa della compilazione precedente.
#   - Crea la cartella bin/ per depositare i file compilati.
#   - Trova e compila tutti i file .java presenti nella cartella src/, ESCLUDENDO quelli in src/test/.
#   - Se la compilazione ha successo, verrà stampato un messaggio di conferma.
#   - In caso di errori, lo script interrompe la compilazione e restituisce un codice di errore.

echo "Pulizia della cartella bin/ (se esistente)..."
rm -rf bin
mkdir -p bin

echo "Compilazione dei file Java dal percorso src/ (escludendo src/test)..."
# Trova tutti i file .java nella cartella src, escludendo quelli nella cartella src/test, e li compila mettendo i .class in bin/
javac -d bin $(find src -type f -name "*.java" ! -path "src/test/*")
COMPILATION_RESULT=$?

if [ $COMPILATION_RESULT -eq 0 ]; then
    echo "Compilazione completata con successo."
else
    echo "Compilazione fallita. Controlla gli errori sopra riportati."
    exit 1
fi
