package com.generation.enoteca.main;

public class Sunto {

	public static void main(String[] args) {
		
		/* Si vuole implementare un sistema per gestire le bottiglie di vino
		 * presenti in un enoteca. Il sistema deve permettere di aggiungere nuovi vini
		 * e bottiglie, visualizzare i vini e le bottiglie presenti,
		 * cercare bottiglie di un determinato vino, 
		 * vendere bottiglie e modificare il prezzo delle bottiglie.
		 * 
		 * Classi necessarie:
		 * - Wine: classe modello che rappresenta un vino
		 *   Bottle: rappresenta una bottiglia di un determinato vino, 
		 *   		 con riferimento al vino, quantità e prezzo di vendita
			 WineDao: interfaccia per la gestione dei dati dei vini, 
			 		  con metodi per salvare, recuperare e aggiornare le informazioni sui vini
			 BottleDao: interfaccia per la gestione dei dati delle bottiglie,
			  		  con metodi per salvare, recuperare e aggiornare le informazioni sulle bottiglie
 			 WineService: classe che implementa la logica di business per 
 			 			  la gestione dei vini, utilizzando il DAO WineDao
			 BottleService: classe che implementa la logica di business per 
			 			   la gestione delle bottiglie, utilizzando il DAO BottleDao e WineDao
			 EnotecaFacade: classe che utilizza WineService e BottleService 
			 				per fornire un'interfaccia semplificata per la gestione del sistema
			 La soluzione deve includere anche una classe Main
			 che utilizza EnotecaFacade per interagire con il sistema.
		 *
		 * - decidere se mantenere un classe Dao padre o eliminarla se rende la gestione troppo complessa
		 * - come gestire la relazione tra vini e bottiglie(un lista di oggetti o un oggetto)
		 * - come gestire l'apertura e la chiusura della connessione:
		 *   -- ogni volta con dei metodi specifici aprire e chiudere la connessione nella
		 *      classe o interfaccia che gestisce il sistema(separo i compiti)
		 *   -- oppure all'interno dei metodi di esecuzione delle query aprire e chiudere
		 *      la connessione in modo da non doverci pensare dopo(aggrego i compiti)    
		 * 
		 * 
		 */

	}

}
