package com.libreria.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.libreria.entities.Entity;
import com.libreria.entities.Libro;
import com.libreria.interfacce.EntityFactory;

public class DaoLibri {

	private Database db;
	private static String tabella = "libri";

	private static String insert = "insert into libri (autore,titolo,genere,numero_pagine,prezzo,"+
			"disponibile,data_pubblicazione)\r\n" +
			"values(?,?,?,?,?,?,?)";
	private static String read = "select * from " + tabella;
	private static String update = "update libri set [colonna]= ? where id = ?";
	private static String updateObj = "update libri set autore = ?, titolo = ?, " +
			"genere = ? , numero_pagine = ?, data_pubblicazione = ?, " 
			+  "disponibile = ?, prezzo = ? "+
			"where id = ?";
	private static String delete = "delete from libri where id = ?";

	//costruttore
	public DaoLibri(String nomeDb) {
		db = new Database(nomeDb);
	}

	public void add(Libro l) {
		db.apriConn();
		//"insert into libri (autore,titolo,genere,numero_pagine,prezzo,"
//				+ "disponibile,data_pubblicazione)\r\n" + 
//				"values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = db.getC().prepareStatement(insert);
			ps.setString(1, l.getAutore());
			ps.setString(2, l.getTitolo());
			ps.setString(3, l.getGenere());
			ps.setInt(4,l.getNumero_pagine());
			ps.setDouble(5, l.getPrezzo());
			ps.setBoolean(6, l.isDisponibile());
			ps.setDate(7, Date.valueOf(l.getData_pubblicazione()));
			int c = ps.executeUpdate();
			if(c > 0)
				System.out.println("libro inserito");
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("sono nel catch di add");
		}
		db.chiudiConn();
	}
	public List<Entity> read(){
		List<Entity> ris = new ArrayList<Entity>();
		db.apriConn();
		List<Map<String, String>> mappe = new ArrayList<Map<String,String>>();
		Entity en;

		try {
			mappe = readMappe(read);
		//	System.out.println(mappe);
			for(Map<String, String > m : mappe) {
				m.put("tabella", tabella);
				en = EntityFactory.make(m);
				if(en != null && en instanceof Libro)
					ris.add(en);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("sono nel catch di read() in DaoLibri");
		}
		return ris;
	}


	public List<Map<String, String>> readMappe(String query) {

		List<Map<String,String>> ris;
		ris = new ArrayList<Map<String,String>>();
		Map<String,String> map;
		db.apriConn();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = db.getC().prepareStatement(query);
			rs = ps.executeQuery();

			while(rs.next()) {

				map = new LinkedHashMap<String, String>();	
				//trovo il numero di colonne della tabella salvata in rs
				int nColonne = rs.getMetaData().getColumnCount();
				//rs.getMetaData() -> è un metodo di ResultSetMetaData che permette
				//l'accesso a un serie di elementi della tabella salvata in rs
				//cioè i metadati della tabella: nomi colonne,n colonne,tipo dato contenuto 
				//nelle colonne
				//.getColumnCount() -> dai metadati prende il numero di colonne
				//contenute nella tabella
				for(int i = 1; i <= nColonne ;i++) {
					//  map.put("id", rs.getString("id"));
					//.getColumnName(i) -> salvo nella variabile nomeColonna
					//il nome della colonna che si trova nella stessa posizione
					//del valore di i
					String nomeColonna = rs.getMetaData().getColumnName(i);
					//prendo il valore che si trova in quella specifica colonna 
					//rs.getString("id") 
					String valore = rs.getString(i);
					map.put(nomeColonna, valore);
				}

				ris.add(map);
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("sono nel catch di readMappe(stringa)");
		}finally {
			try {
				if(ps != null)
					ps.close();
				if(rs != null)
					rs.close();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("non sono riuscito a chiudere ps o rs");
			}
			db.chiudiConn();
		}

		return ris;
	}

	public void update(int id,String colonna,String nuovoValore) {
		db.apriConn();
		//"update libri set [colonna]= ? where id = ?";
		try {
			update = update.replace("[colonna]",colonna);
			PreparedStatement ps = db.getC().prepareStatement(update);
			ps.setString(1, nuovoValore);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();

		}catch(Exception e) {
			System.out.println("sono nel catch di update");
		}

		db.chiudiConn();
	}

	public void update(Libro l) {
		db.apriConn();
		//"update libri set autore = ?, titolo = ?, " +
		//		"genere = ? , numero_pagine = ?, data_pubblicazione = ?, " +
		//		"disponibile = ?, prezzo = ? "+
		//		"where id = ?"
		try {
			PreparedStatement ps = db.getC().prepareStatement(updateObj);
			ps.setString(1, l.getAutore());
			ps.setString(2, l.getTitolo());
			ps.setString(3, l.getGenere());
			ps.setInt(4, l.getNumero_pagine());
			ps.setDate(5, Date.valueOf(l.getData_pubblicazione()));
			ps.setBoolean(6, l.isDisponibile());
			ps.setDouble(7, l.getPrezzo());
			ps.setInt(8, l.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("sono nel catch di update(Libro l)");
			e.printStackTrace();
		}
		db.chiudiConn();
	}	


	public void delete(int id) {
		db.apriConn();
		try {
			PreparedStatement ps = db.getC().prepareStatement(delete);
			ps.setInt(1, id);
			int d = ps.executeUpdate();
			if(d > 0)
				System.out.println("libro eliminato");
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("controlla la sintassi e i dati della query:\n" + delete);

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Sono nel catch di delete");
		}
		db.chiudiConn();
	}




}
