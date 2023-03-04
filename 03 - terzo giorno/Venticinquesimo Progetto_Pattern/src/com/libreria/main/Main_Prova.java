package com.libreria.main;

import com.libreria.dao.DaoCasaEditrice;
import com.libreria.dao.DaoLibri;
import com.libreria.dao.Database;
import com.libreria.entities.CasaEditrice;
import com.libreria.interfacce.IDaoCasaEditrice;
import com.libreria.interfacce.IDaoLibri;

public class Main_Prova {
	
	public static void main(String[] args) {
		
		IDaoLibri libreria = DaoLibri.getInstance();
		System.out.println(libreria.stampaLista(libreria.read("select * from libri")));
		System.out.println(((DaoLibri)libreria).update("autore", "anonimo",16));
		
	// 	IDaoCasaEditrice case_editrici = DaoCasaEditrice.getInstance();
	// //	System.out.println(case_editrici.stampaLista(case_editrici.read("select * from caseeditrici")));
	// 	System.out.println(((DaoCasaEditrice)case_editrici).update("sede", "Bologna", 3));
		
	}

}
