package com.libreria.interfacce;

import java.util.List;

import com.libreria.entities.Libro;

public interface IDaoLibri {
	
	List<Libro> read(String query,String...params);
	boolean create(Libro l);
	boolean update(Libro l);
	boolean delete(int id);
	
	default String stampaLista(List<Libro> libri) {
		String ris = "";
		for(Libro l : libri)
			ris += l.toString() + "\n-----\n";
		
		return ris;
	}
	
	

}
