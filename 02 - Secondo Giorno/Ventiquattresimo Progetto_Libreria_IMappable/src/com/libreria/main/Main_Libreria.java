package com.libreria.main;

import java.util.Map;
import java.util.Map.Entry;

import com.libreria.dao.Dao;
import com.libreria.entities.Libro;
import com.libreria.interfacce.IDao;

public class Main_Libreria {

	public static void main(String[] args) {
	
		IDao libreria = new Dao("librerianuova");
	//	System.out.println(libreria.read());
	//	Libro l = new Libro(0,"autore1","titolo1","romanzo", 340,LocalDate.parse("1999-08-23") ,
	//			true, 34.09);
	//	libreria.add(l);
	//	libreria.delete(18);
		
		//prendo il libro all'indice 1, cioè il secondo, che si trova nella
		//lista dei libri che ricevo come risultato
		//dal metodo .read() 
		Libro l2 = libreria.read().get(1);
	//	System.out.println(l2.toString());
		//di quel libro stampo il toString()
	//	System.out.println(l2.toString());
		//creo un oggetto d di tipo LocalDate
//		LocalDate d = LocalDate.parse("1975-04-12");
		//setto, modifico tramite il setDataDiPubblicazione() il valore della 
		//proprietà data_publlicazione, quindi modifico lo stato dell'oggetto l2
//		l2.setDataDiPubblicazione(d);
		//l'oggetto libreria di tipo IDao invoca un suo metodo update(Libro l)
		//metodo che aggiorna uno o più specifici valori di una specifica entità, riga
		//all'interno del db
//		libreria.update(l2);
//		libreria.update(2, "data_pubblicazione", "2000-12-12");
//		libreria.update(2, "prezzo", "22.90");
		
		//ritorna una lista di mappe
	//	libreria.readMappe();
		//per stampare la lista di mappe
	//	System.out.println(Util.stampaMappe(libreria.readMappe()));
		
		
//		for(Map<String, String> l : libreria.readMappe()) {
//			   System.out.println("\n");
//			   for(Entry<String, String> m : l.entrySet())
//				   System.out.println(m.getKey() + " : " + m.getValue());
//		 }
		
	
		
	}

}
