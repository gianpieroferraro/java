package com.libreria.interfacce;

import java.util.List;

import com.libreria.entities.CasaEditrice;

public interface IDaoCasaEditrice {
	
	List<CasaEditrice> read(String query, String...params);
	boolean create(CasaEditrice c);
	boolean update(CasaEditrice c);
	boolean delete(int id);
	CasaEditrice ricercaPerId(int id);
	
	default String stampaLista(List<CasaEditrice> caseEd) {
		String ris = "";
		for(CasaEditrice c : caseEd)
			ris += c.toString() + "\n-----\n";
		
		return ris;
	}
	
	

}
