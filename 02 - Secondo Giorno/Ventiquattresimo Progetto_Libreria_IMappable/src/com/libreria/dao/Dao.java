package com.libreria.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.libreria.entities.Libro;
import com.libreria.interfacce.IDao;

public class Dao implements IDao{

	/* DAO: Data Access Object
	 * La classe Dao permetterà di creare istanze di tipo Dao
	 * Queste istanze avranno il compito di leggere(R) e manipolare(CUD) 
	 * i dati contenuti in uno specifico db  
	 * 
	 * useremo una tecnica di programmazione definita ORM:
	 * Object Relational Mapping
	 * che consente la comunicazione tra un linguaggio OOP e un RDBMS
	 * grazie ad essa possiamo trasformare i dati presenti in un db
	 * in oggetti e viceversa eseguire operazioni(crud) sul db direttamente
	 * dal backend  
	 * 
	 */
	
	private Database db;
	
	//costruttore
	public Dao(String nomeDb) {
		db = new Database(nomeDb);
	}
	
	/* CUD , create ,update, delete non devono tornare niente quindi possono essere 
	 * messi void oppure tornare un valore boolean di controllo
	 */
	
	
	@Override
	public void add(Libro l) {
		
		//apro la connessione
		db.apriConn();
		
		//preparo la query generica che mi permette di inserire un record, una entità
		//in una specifica tabella di un db
		String query = "insert into libri (autore,titolo,genere,numero_pagine,prezzo,"
				+ "disponibile,data_pubblicazione)\r\n" + 
				"values(?,?,?,?,?,?,?)";
		try {
			/* ps è un oggetto di tipo PreparedStatement
			 * 
			 * 
			 */
			PreparedStatement ps = db.getC().prepareStatement(query);
			ps.setString(1, l.getAutore());
			ps.setString(2, l.getTitolo());
			ps.setString(3, l.getGenere());
			ps.setInt(4,l.getNumero_pagine());
			ps.setDouble(5, l.getPrezzo());
			ps.setBoolean(6, l.isDisponibile());
			ps.setDate(7, Date.valueOf(l.getData_pubblicazione()));
			
			//dopo
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

	@Override
	public List<Libro> read() {
		List<Libro> ris = new ArrayList<Libro>();
		Libro l;
		
		//apro la connessione
		db.apriConn();
		String query = "SELECT * FROM libri";
		
		try {
			//ps è un oggetto di tipo PreparedStatement
			PreparedStatement ps = db.getC().prepareStatement(query);
			//ResultSet rs è un oggetto che contiene i dati in formato tabellare
			//in rs saranno salvati i dati contenuti nella tabella, cioè la risposta 
			//del db alla domanda posta tramite la query passata alla riga 53 tramite ps
			//scritta alla riga 49
			ResultSet rs = ps.executeQuery();
			//in questa tabella contenuta in rs 
			//ogni riga corrisponde ad un oggetto in Java
			//grazie all'operazione ORM che permette di trasformare i dati in oggetti
			
			//.next()
			while(rs.next()) {
				//id, autore, titolo, genere, numero_pagine, prezzo, disponibile, data_pubblicazione
				//	1	Tolkien	Lo hobbit	Fantasy	300	12	0	1937-09-21	
				l = new Libro(rs.getInt("id"), rs.getString("autore"), 
						rs.getString("titolo"),rs.getString("genere"),
						rs.getInt("numero_pagine"), 
						rs.getDate(8)== null?null : rs.getDate(8).toLocalDate(),
						rs.getBoolean(7), rs.getDouble("prezzo"));
				
			//	System.out.println(l.toString());
				
				if(l != null)
					ris.add(l);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Sono nel catch di read");
		}
		db.chiudiConn();
		
		return ris;
	}

	@Override
	public void update(int id,String colonna,String nuovoValore) {
		
		String query = "update libri set " + colonna + "= ? where id = " + id;
		
		db.apriConn();
		try {
			
			PreparedStatement ps = db.getC().prepareStatement(query);
			ps.setString(1, nuovoValore);
			ps.executeUpdate();
			ps.close();
			
		}catch(Exception e) {
			System.out.println("sono nel catch di update");
		}
				
		db.chiudiConn();
	}

	public void update(Libro l) {
		db.apriConn();
		/* i ? sono dei segnaposti che rendono la query generica
		 */
		String query = "update libri set autore = ?, titolo = ?, " +
						"genere = ? , numero_pagine = ?, data_pubblicazione = ?, " +
						"disponibile = ?, prezzo = ? "+
						"where id = ?";
		//per sostituire i placeholder o segnaposto se state usando un oggetto di tipo
		//PreparedStatement potete usare i suoi metodi set
		//ma se usate Statement che è il padre di PreparedStatement potete usare
		//un metodo delle stringhe stringa.replace(vecchioValore,nuovoValore);
		//se avete valori numerici tipo l'id basta concatenare gli apici vuoti
//		query = query.replace("[autore]", l.getAutore()).replace("[titolo]",l.getTitolo())
//					.replace("[genere]", l.getGenere())
//					.replace("[numero_pagine]",l.getNumero_pagine()+ "")
//					.replace("[id]", l.getId() + "");
		try {
		//	Statement s = db.getC().createStatement();
			PreparedStatement ps = db.getC().prepareStatement(query);
			//ps è un oggeto che tramite la connessione setta la query nel modo indicato
			//e porta quella query che ha controllato al db
			ps.setString(1, l.getAutore());
			//ps.setString(parametro1, parametro2);
			//parametro1 corrisponde alla posizione del ? da sostituire
			//parametro2 corrisponde al valore che si vuole inserire nella query
			//cioè il valore di una specifica proprietà dell'oggetto l di tipo Libro
			ps.setString(2, l.getTitolo());
			ps.setString(3, l.getGenere());
			ps.setInt(4, l.getNumero_pagine());
			ps.setDate(5, Date.valueOf(l.getData_pubblicazione()));
			ps.setBoolean(6, l.isDisponibile());
			ps.setDouble(7, l.getPrezzo());
			//ricordarsi di considerare il tipo di elemento che deve essere congruente 
			//anche ai valori inseriti nelle colonne sul db
			ps.setInt(8, l.getId());
			//	s.execute(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sono nel catch di update(Libro l)");
			e.printStackTrace();
		}
		
		db.chiudiConn();
	}
	
	
	
	/**
	 * Javadoc, permette di documentare package,classi,interfacce,metodi,attributi del codice Java
	 * La documentazione in javadoc viene convertita in HTML e resa disponibile per la 
	 * consultazione al resto del programma
	 * @author admin
	 * @param int id, l'id dell'oggetto da eliminare
	 * @return void non ha nessun valore di ritorno
	 * 
	 */
	@Override
	public void delete(int id) {
		
		String query = "delete from libri where id = " + id;
		db.apriConn();
		try {
			PreparedStatement ps = db.getC().prepareStatement(query);
			int d = ps.executeUpdate();
			if(d > 0)
				System.out.println("libro eliminato");
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("controlla la sintassi e i dati della query:\n" + query);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Sono nel catch di delete");
		}
		
		db.chiudiConn();
	}

	@Override
	public List<Map<String, String>> readMappe() {
//		List<Libro> libri;
//		libri = new ArrayList<Libro>();
		//dichiaro e inizializzo una lista mappe
		List<Map<String,String>> ris;
		ris = new ArrayList<Map<String,String>>();
		//dichiaro una mappa che conterrà una o più coppie chiave valore
		//dove la chiave che è il primo elemento è di tipo String
		//e il valore ad essa associato è di tipo String
		Map<String,String> map;
		//apro la connessione con il db
		db.apriConn();
		String query = "select * from libri";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = db.getC().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				//istanzio la mappa map dichiarata a riga 242
				map = new LinkedHashMap<String, String>();
				//associo le chiavi ai valoro in base alle colonne della tabella libri
				map.put("id", rs.getString("id"));
				map.put("autore", rs.getString("autore"));
				map.put("titolo", rs.getString("titolo"));
				map.put("genere", rs.getString("genere"));
				map.put("numero_pagine", rs.getString("numero_pagine"));
				map.put("prezzo", rs.getString("prezzo"));
				map.put("disponibile", rs.getString("disponibile"));
				map.put("data_pubblicazione", rs.getString("data_pubblicazione"));
				map.put("id_casaEditrice", rs.getString("id_casaEditrice"));
				
				//dopo aver inerito nella mappa tante coppie quante sono le colonne della tabella
				//aggiungo la mappa nella lista per poterne salvare i valori
				ris.add(map);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Controlla la query: \n" + query);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("sono nel catch di readMappe()");
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

	public static String readLibri = "select * from libri";
	
	@Override
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
	
	
	
	

}
