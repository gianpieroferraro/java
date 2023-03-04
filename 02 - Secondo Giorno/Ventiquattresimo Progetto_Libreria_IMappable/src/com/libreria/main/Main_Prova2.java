package com.libreria.main;

import java.util.List;
import java.util.Map;

import com.libreria.dao.Dao;
import com.libreria.dao.DaoLibri;
import com.libreria.dao.Database;
import com.libreria.entities.Libro;
import com.libreria.interfacce.IDao;

public class Main_Prova2 {

	public static void main(String[] args) {

//		IDao libreria = new Dao("librerianuova");

//		Libro l = libreria.read().get(1);
//		System.out.println(l.toString());
		
		//riga 20, istazio un oggetto lib di tipo Libro usando il costruttore vuoto
		//quindi le proprietà dell'oggetto a riga 20 avranno i valori di default
//		Libro lib = new Libro();
//		System.out.println("Libro lib riga 20:\n" +lib.toString());
		
//		Map<String, String> map;
//		List<Map<String, String>> mappe = libreria.readMappe();
//		map = mappe.get(2);
//		System.out.println(map);
//		
//		lib.fromMap(map);
//		
//		System.out.println("Libro lib riga 30:\n" +lib.toString());

//		Map<String, String> mappa = l.toMap(); 
	//	System.out.println(mappa);
	
	//	Database db = new Database("librerianuova");
	//	Database db2 = new Database("azienda1");
		
//		String query = "select * from dipendenti";
//		IDao azienda = new Dao("azienda1");
//		System.out.println(azienda.readMappe(query));
		
		DaoLibri libri = new DaoLibri("librerianuova");
		System.out.println(libri.read());
		
		
		
	}

}
