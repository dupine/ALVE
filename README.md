# ALVE (Artificial Life Virtual Environment)

![DNA Icon](https://www.antoniopino.it/images/logoAlve.png)

Una simulazione di vita artificiale basata su ecosistemi, evoluzione genetica realistica (mutazioni, crossover), speciazione e reti neurali per il controllo delle entità. Il progetto è sviluppato in Java utilizzando JavaFX per l'interfaccia grafica e la visualizzazione.

## Obiettivi del Progetto

*   Simulare un ecosistema complesso con diverse specie interagenti.
*   Implementare meccanismi di evoluzione genetica (DNA, mutazioni, crossover).
*   Dotare ogni entità (creatura) di una rete neurale per prendere decisioni autonome basate su percezioni sensoriali e stato interno.
*   Simulare necessità vitali (cibo, energia, riproduzione) e influenze ambientali (clima, terreno).
*   Fornire un'interfaccia grafica (JavaFX) per:
    *   Visualizzare la simulazione in tempo reale.
    *   Ispezionare i parametri, il DNA e lo stato di singole entità.
    *   Controllare parametri globali della simulazione (tempo, temperatura, ecc.).
    *   Interagire con la simulazione (aggiungere/rimuovere entità).

## Tecnologie Utilizzate

*   **Linguaggio:** Java (<!-- Inserisci la versione JDK, es. 17+ -->)
*   **Interfaccia Grafica:** JavaFX (<!-- Inserisci la versione, es. 17+ -->)
*   **Build System:** Maven <!-- O Gradle, se usi quello -->
*   **Controllo Versione:** Git

## Getting Started

Per eseguire una copia locale del progetto per sviluppo o test.

### Prerequisiti

Assicurati di avere installato:

*   **JDK (Java Development Kit):** Versione <!-- Inserisci versione, es. 17 --> o superiore.
*   **Maven:** Versione <!-- Inserisci versione, es. 3.6 --> o superiore (necessario per gestire le dipendenze e la build).
*   **Git:** Per clonare il repository.

### Installazione e Setup

1.  **Clona il repository:**
    ```bash
    git clone <URL_DEL_TUO_REPOSITORY_GIT>
    cd ALVE
    ```
    <!-- Sostituisci <URL_DEL_TUO_REPOSITORY_GIT> con l'URL reale quando lo avrai su GitHub/GitLab ecc. -->

2.  **Importa il progetto nel tuo IDE:**
    *   Apri il tuo IDE preferito (es. IntelliJ IDEA, Eclipse).
    *   Importa il progetto come progetto Maven esistente (cercando il file `pom.xml` nella root del progetto). L'IDE dovrebbe scaricare automaticamente le dipendenze definite nel `pom.xml` (inclusa JavaFX).

3.  **Build (Opzionale, l'IDE spesso lo fa automaticamente):**
    *   Puoi compilare il progetto dalla linea di comando usando Maven:
        ```bash
        mvn clean install
        ```

### Esecuzione

*   **Dall'IDE:** Trova la classe `Main` (probabilmente in `com.alve.alve0.Main` o simile) e eseguila come applicazione Java.
*   **Da Linea di Comando (con Maven):** Se hai configurato il plugin Maven per JavaFX, potresti essere in grado di eseguirlo con:
    ```bash
    mvn javafx:run
    ```
    <!-- Verifica la configurazione del plugin nel tuo pom.xml -->
*   **Da JAR Eseguibile (se configurato):** Se hai configurato Maven per creare un JAR eseguibile che include le dipendenze:
    ```bash
    java -jar target/ALVE-1.0-SNAPSHOT-jar-with-dependencies.jar
    ```
    <!-- Il nome del JAR potrebbe variare -->

## Roadmap e Sviluppo (Scaletta)

Ecco i passaggi pianificati per lo sviluppo:

*   [x] Cartelle progetto, file e struttura di collegamento fra i vari file <!-- Segna con 'x' quando completato -->
*   [x] Entità (oggetti) senza parametri che si muovono in maniera casuale per la mappa
*   [x] Entità con parametri, ogni entità è selezionabile ed è possibile visualizzare i suoi parametri
*   [x] E' possibile aggiungere entità dove si vuole con il nuovo menu presente a destra dello schermo
*   [ ] Implementazione Genetica Base (DNA, Attributi, Interprete Genoma)
*   [ ] Implementazione Sistema di Sopravvivenza (Energia, Metabolismo, Cibo) e Riproduzione (con Ereditarietà e Variazione Genetica)
*   [ ] Implementazione Intelligenza Artificiale (Reti Neurali, Sensori, Attuatori)
*   [ ] Inizio sviluppo di sistema di speciazione
*   [ ] (Futuro) Miglioramenti UI, ottimizzazioni performance, parametri climatici avanzati, ecc.

## Contribuzione

Al momento il progetto è in fase di sviluppo iniziale da parte del creatore. Se sei interessato a contribuire in futuro, per favore apri prima una "Issue" per discutere la modifica che vorresti apportare.

## Licenza

Nessuno licenza al momento

## Contatti

<!-- Inserisci il tuo nome o nickname e magari un link al tuo profilo GitHub/email -->
Antonoio Pino - Dupine - antoniopino1245@gmail.com

Link Progetto: [https://github.com/tuo_username/ALVE](https://github.com/tuo_username/ALVE)
<!-- Aggiorna con il link reale del repository -->
