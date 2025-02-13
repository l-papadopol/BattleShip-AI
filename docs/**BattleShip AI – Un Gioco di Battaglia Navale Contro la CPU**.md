## **BattleShip AI – Un Gioco di Battaglia Navale Contro la CPU**

### **Motivazioni e Obiettivi**

Il progetto proposto ha l’obiettivo di sviluppare un gioco digitale di Battaglia Navale in modalità 1 vs 1 contro una CPU. 

L’idea nasce dalla volontà di riprodurre l’esperienza del classico gioco da tavolo, aggiungendoci un'pò di brio.

L’obiettivo principale è offrire un’esperienza fluida e coinvolgente, con una CPU capace di adottare strategie di attacco "intelligenti"" e adattive. 

Il progetto sarà sviluppato con un design modulare, per facilitare futuri miglioramenti o ottimizzazioni.

---

### **Funzionalità Minimali Ritenute Obbligatorie**

- Implementazione delle regole "ufficiali del gioco Battaglia Navale per partite 1 vs 1
- Griglia di gioco 10x10, con gestione della disposizione casuale delle navi da parte della CPU.
- Modalità single-player contro una CPU, con tre livelli di difficoltà:
  - Facile → Attacco casuale senza memoria dei colpi.
  - Medio → Se colpisce, cerca celle adiacenti per affondare la nave.
  - Difficile → Utilizza pattern di attacco ottimizzati per colpire le navi con maggiore efficienza.
- Interfaccia grafica semplice e intuitiva, con una rappresentazione della griglia chiara e ben organizzata.
- Sistema di validazione dei colpi, per garantire la correttezza del gioco.

---

### **Funzionalità Opzionali**

- Resistenza variabile delle navi: alcune unità necessitano di più colpi per essere affondate.
- Proiettili speciali (limitati a 10) che distruggono istantaneamente una sezione della nave colpita.
- Eventi casuali, come radar temporaneo, mine marine e possibilità di riparare navi.
- Potenziamenti sbloccabili durante la partita (colpo extra, radar, riparazione parziale).

---

### **Challenge Principali**

- Sviluppo di un’IA multi-livello, con diverse strategie di attacco:
  - IA Casuale (Facile) → Attacca senza memoria, scegliendo coordinate casuali.
  - IA Strategica (Media) → Dopo aver colpito una nave, cerca le celle adiacenti per affondarla.
  - IA con Pattern Ottimizzati (Difficile) → Utilizza pattern di attacco per colpire navi più velocemente, analizzando le partite giocate.
- Gestione della resistenza delle unità, bilanciando il numero di colpi necessari per affondare ogni nave.
- Garantire un corretto sistema di validazione per il posizionamento delle navi e l’efficacia degli attacchi.
- Implementare un’interfaccia grafica semplice ma chiara, che permetta di visualizzare la griglia di gioco in modo efficace.
- Progettare il codice in modo modulare, per rendere possibile l’aggiunta futura di nuove modalità o migliorie alla CPU, sfruttando tutte le potenzialità offerte dalla OOP.

---

### **Suddivisione del Lavoro**

L’intero progetto sarà sviluppato da Papadopol Lucian-Ioan, occupandomi personalmente di tutte le fasi di progettazione e implementazione.
