package com.libreria.main;

import com.libreria.dao.DaoCasaEditrice;
import com.libreria.dao.DaoLibri;

public class Main_Prova3 {

	public static void main(String[] args) {
		
		DaoLibri libri = new DaoLibri("libreria_prova");
		DaoCasaEditrice casaEd = new DaoCasaEditrice("libreria_prova",libri);
		
		System.out.println(casaEd.read());
				
		
		

	}

}
