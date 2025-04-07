# BattleShip AI

BattleShip AI è un progetto Java che implementa il classico gioco "Battaglia Navale" con regole rivisitate ed una forma di pseudo-intelligenza artificiale.  

---

## Caratteristiche

- Griglia di gioco quadrata 10x10.

- Posizionamento navi manuale per l'utente, automatico e casuale per il PC.

- Navi di diverse dimensioni: 5, 4, 4, 3, 3, 3, 2, 2, 1. 

- Ogni nave è composta da celle ed ogni cella per essere affondata deve subire 4/4 di danno quindi affondare un intera nave, ad esempio da 5 caselle, richiede di influggere ben 20 punti danno totali.

- Orientamento delle navi: orizzontale o verticale.

- Tre tipi di proiettili:
  
  - **Normale**: 1 unità di danno.
  
  - **Potente**: 3 unità di danno.
  
  - **Speciale**: danno massimo istantaneo.

- Sistema di bonus casuali durante il gioco che aumenta il numero di proiettili speciali o potenti.

- Turni alternati tra giocatore e PC.

- Condizione di vittoria: tutte le navi dell'avversario sono affondate.

- Indicazione grafica dello stato delle navi colpite.

- Livelli di difficoltà:
  
  - **Facile**: colpi completamente casuali.
  
  - **Media**: colpi basati su pattern di attacco ottimizzati.
  
  - **Diffcile**: pseudo-IA con ricerca e distruzione sistematica della flotta nemica.

- Interfacce utente:
  
  - **TUI** su console.
  
  - **GUI** realizzata con Swing.

- Testing: unit test (JUnit 5) organizzati separatamente nella cartella `src/test/`.

- Documentazione: JavaDoc completa e relazione tecnica fornite nella cartella `docs/`

---

## Struttura del progetto

```
src/
├── main
│   └── Main.java
├── controller
│   ├── AITurnStrategy.java
│   ├── GameController.java
│   ├── ai
│   │   ├── EasyStrategy.java
│   │   ├── MediumStrategy.java
│   │   └── HardStrategy.java
│   └── handlers
│       ├── PcTurnHandler.java
│       ├── PlayerTurnHandler.java
│       ├── ProjectileHandler.java
│       └── ShipPlacingHandler.java
├── model
│   ├── Battle.java
│   ├── ModelInterface.java
│   ├── builders
│   │   └── ShipBuilder.java
│   └── entities
│       ├── Grid.java
│       ├── GridSquare.java
│       ├── Player.java
│       ├── Projectile.java        # Classe astratta
│       ├── PowerProjectile.java
│       ├── SpecialProjectile.java
│       ├── StandardProjectile.java
│       └── Ship.java
├── view
│   ├── Gui.java
│   ├── Tui.java
│   ├── ViewInterface.java
│   └── components
│       ├── GridPanel.java
│       └── Messages.java
├── test
│   └── (JUnit test classes, esclusi dalla compilazione normale)
├── compilami.sh     # Script per la compilazione automatica
docs/
├── index.html       # Entry point della documentazione JavaDoc
└── relazione.pdf    # Relazione tecnica descrittiva del progetto
```

---

## Come Compilare ed Eseguire

Puoi compilare il progetto usando lo script `compilami.sh` che ho fornito:

### 1. Compilazione con `compilami.sh`

```bash
# Rendi lo script eseguibile
chmod 777 compilami.sh

# Esegui lo script
./compilami.sh
```

Questo script:

- Cancella ed eventualmente ricrea la cartella `bin/` per pulire l'ambiente prima di compilare nuovamente il progetto.

- Compila **solo** i file `.java` presenti sotto `src/`, **escludendo** automaticamente i file di test in `src/test/`.

### 2. Esecuzione del gioco

Dopo la compilazione, puoi avviare il gioco con:

```bash
java -cp bin main.Main
```

### 3. Requisiti

- Il progetto è stato sviluppato su Debian Linux con Eclipse  e "gira" con OpenJDK 21.0.6

---

## Documentazione

Sono disponibili i seguenti documenti utili nella cartella `docs/`:

- **JavaDoc Generata** – Documentazione tecnica automatica delle classi.

- **Relazione del Progetto** – Documento descrittivo del progetto, delle scelte progettuali e delle implementazioni, relazione finale per l'esame di Programmazione e Modellazione ad Oggetti.

---

## Licenza

Questo progetto è distribuito sotto la licenza **CC BY-NC-ND 3.0 IT**.  
Se ne consente l'uso e la distribuzione a fini non commerciali, con attribuzione e senza opere derivate.

---

## Contatti

Autore: **Papadopol Lucian Ioan** e-mail: l.papadopol@campus.uniurb.it 
Per qualsiasi informazione o collaborazione, non esitare a contattarmi!
