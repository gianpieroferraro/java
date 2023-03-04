package com.libreria.interfacce;

import java.util.List;
import java.util.Map;

import com.libreria.entities.Libro;

public interface IDao {
	
	//firme dei metodi crud
	//create --> sar� un metodo che permette l'aggiunta di un libro nel db
	void add(Libro l);
	List<Libro> read();
	void update(int id, String proprieta,String nuovoValore);
	void delete(int id);
	void update(Libro l);
	List<Map<String,String>> readMappe();
	List<Map<String,String>> readMappe(String query);
	

}
