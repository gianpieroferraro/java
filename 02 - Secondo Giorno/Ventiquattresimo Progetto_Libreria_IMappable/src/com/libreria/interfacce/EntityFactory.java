package com.libreria.interfacce;

import java.util.Map;

import com.libreria.entities.CasaEditrice;
import com.libreria.entities.Entity;
import com.libreria.entities.Libro;

public interface EntityFactory {
	
	/* in questa interfaccia avremo solo un metodo che istanzierà 
	 * solo oggetti formalmente di tipo Entity e concretamente
	 * del tipo che il metodo riterrà più opportuno
	 * a seconda delle istruzioni inserite
	 */
	static Entity make(Map<String, String> map) {
		Entity e = null;
		if(map.containsKey("tabella")) {
			switch(map.get("tabella").toLowerCase()) {
			case "libri":
				//controlli statici
				e = new Libro();
				break;
			case "caseeditrici":
				//controlli statici 
				e = new CasaEditrice();
				break;
			}
			map.remove("tabella");
		}
		if(e != null)
			e.fromMap(map);
		return e;
	}
	
	/* Un design pattern è una soluzione astratta,generica e riproducibile 
	 * a un problema ricorrente. 
	 * Un pattern non è un pezzo di codice specifico ma un modo di lavorare
	 * unendo dei pezzi.
	 * 
	 * -1. il problema da affrontare, il problema che affronta il pattern
	 * -2. perché viene utilizzato
	 * -3. la sua struttura, quali parti lo compongono
	 * 
	 * Esistono diverse famiglie di pattern
	 * 
	 * CREAZIONALE
	 * Factory Pattern
	 * 1.vogliamo creare un sistema centralizzato e flessibile per istanziare oggetti 
	 *   di un dato tipo.Uno degli obiettivi è garantire il polimorfismo
	 * 2.viene usato per delegare la creazione dell'oggetto a un secondo soggetto
	 *   cioè alla factory che sceglierà per noi il tipo concreto a seconda dei parametri
	 *   che le passiamo
	 *   Nello specifico in questo esempio come parametro passiamo al metodo make una mappa
	 *   e in base al suo contenuto il metodo deciderà quale tipo creare
	 *   -> in questo modo il programma resta polimorfico e quindi in grado di gestire
	 *      dati diversi che possono arrivare da un file o da un database
	 *  - evitiamo di far fare new(istanziare oggetti) al client. solo la Factory ha il compito di
	 *    farlo
	 *  - usiamo l'oggetto prodotto dalla Factory per il suo tipo formale  
	 *  - ricordarsi che la Factory potrebbe restituire eccezioni o valori null
	 *    -> prevedere l'errore e gestirlo    
	 *      
	 * 3.soggetti del pattern, le componenti: 
	 *   - un Product, un Prodotto cioè il tipo formale dell'oggetto che vogliamo creare
	 *     in questo caso Entity
	 *   - la Factory, un oggetto, una classe o una interfaccia che è responsabile 
	 *     della produzione del Product, dei prodotti(nel nostro caso delle Entity)
	 *     e della scelta dei loro tipi concreti
	 *  - il Client è l'oggetto p la classe che si rivolge alla Factory per creare l'oggetto 
	 *    e se necessario eventualmente invia i dati necessari per farlo specificando il tipo
	 *    formale.
	 *    
	 *     
	 *     
	 *     
	 *     
	 *     
	 * 
	 */
	
	
}
