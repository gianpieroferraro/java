package com.libreria.main;

import java.lang.reflect.Method;

import com.libreria.dao.Dao;
import com.libreria.entities.Libro;
import com.libreria.interfacce.IDao;

public class Main_Prova {

	public static void main(String[] args) {

		IDao libreria = new Dao("librerianuova");
		
		Libro l = libreria.read().get(1);
		
		//stampo il nome della classe dell'oggetto l
		/* .getClass() � un metodo che restituisce un oggetto di tipo "Class"
		 * che rappresenta una classe. Non solo il nome della classe ma l'intera classe
		 * con i tutti i metodi dichiarati e eratidati, le sue propriet�, le informazioni
		 * sulla visibilit�, i costruttori....
		 */
	//	System.out.println(l.getClass().getSimpleName());
		
		//a partire da un oggetto di tipo Class ottenuto come ritorno dal metodo .getClass()
		//posso invocare il metodo .getMethods() 
		/* -> restituisce un vettore, un array di oggetti di tipo Method
		 * Method � un oggetto che contiene tutti i dato di un metodo:
		 * i suoi parametri(numero e dato), il nome del metodo
		 * ci permette di invocare il metodo su un oggetto specifico
		 */
		//stampo i nomi dei metodi di un oggetto
//		for(Method m : l.getClass().getMethods())
//			System.out.println(m.getName());
		
		//stampo solo i getter 
		for(Method m : l.getClass().getMethods())
			if(m.getName().startsWith("get") || m.getName().startsWith("is"))
				System.out.println(m.getName());
		

	}

}
