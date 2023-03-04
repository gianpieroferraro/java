package com.generation.enoteca.main;

public class Teoria {
      /*
     * Pattern CREAZIONALI:
     * - Factory;
     * - Singleton
     * - Prototype (clonazione di oggetti che gia abbiamo per non dover fare new)
     *      -- quando ci sono oggetti molto simili tra loro si clonano tramite un metodo, per risparmiare tempo
     *         e risorse; utilizziamo una cache; si utilizza uina classe Clonable e si usa il metodo clone()
     * 
     *         PROBLEMA -> creare oggetti simili tra loro, partendo da una base comune.
     *         a livello di tempo e risorse la creazione convenzionale potrebbe risultare troppo dispendiosa e quindi si 
     *         preferisce clonare gli oggetti
     *         
     *         SOLUZIONE -> creazione di una cache di prototipi che serviranno come base per gli altri oggetti.
     *         memorizzio i prototipo nella cache e poi li clono
     * 
     *         SOGGETTI -> 
     *              - Client che ha bisogno di uno o più oggetti, liberamente modificabili a partire dalla versione standard
     *                del prototipo stesso.
     *              - Cache, cioè l'oggetto che mantiene in memoria l'elenco dei protipi e vi permette di scegliere quale.
     *              - Product, il tipo formale da clonare. Potete pensare al prototype come una factory(a fronte di un tupo formale 
     *                viene istanziato l'oggetto per il suo tipo concreto) ma a differenza di quest'ultima non create oggetti
     *                tramite il new ma tramite clonazione.
     * il product deve implementare l'interfaccia che si chiama clonable(), che contiene i metodi tra cui prototipo.clone() che 
     * permettono di creare una copia del prototipo e in generale è più veloce della creazione.
     * Inoltre la cache deve permettere di memorizzare una lista di oggetti(in questo caso protopi) in una mappa o una list o arralist
     * il cui tipo formale e il tipo concreto sarà un figlio, che deve essere compatibile.
     *          
     * 
     * elementi da inserire nella classe:
     * - cache, in cui inserite i prototipi inizializzate
     * - metodo che restituisce i cloni (es un get() che restituisce i cloni) in base alla specifica date
     * 
     * 
     * 
     * PATTERN STRUTTURALI
     * FACEDE -> facciata che consente di nascondere la parte complessa e consente di semplifaicare l'uso di un'insieme di interfaccia(?) 
     * PROXY ->  
     *              Problema:  migliorare il funzionamento di un oggetto esistente senza cambiare l'interfaccia di questo oggetto.
     *                          solitanente per aggiungere delle funzioni
     *              Soluzione:  prendo l'oggetto da migliorare e lo metto all'interno di un secondo oggetto che ha la stessa interfaccia dove 
     *                          il secondoo usa il primo ma ne aggiunge le funzionalità, migliora le prestazione
     *              Soggetti: - Interfaccia I, cioè l'interfaccia dell'oggetto da migliorare 
     *                        - oggetto O da migliorare che implementa I
     *                        - oggetto migliorato OP che anche lui implementa I e che contiene anche quell'oggetto da migliorare O
     *                        - Client che usano l'interfaccia I.
     * 
     * es. DaoWineProxy
     * 
     */

}
