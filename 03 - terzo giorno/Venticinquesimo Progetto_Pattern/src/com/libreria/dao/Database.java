package com.libreria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.libreria.interfacce.IDatabase;

public class Database implements IDatabase{

	//dichiaro le proprietà di un oggetto di tipo Database
	private String percorso;
	private String username;
	private String password;
	private Connection c;

	//propriet� della classe Database
	//JDBC -> Java DataBase Connectivity � una libreria interna di java che si trova nel
	//package java.sql 
	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";

	//in questa propriet� abbiamno inserito il percorso per arrivare ad una specifica libreria
	//cio� JDBC che permette l'interazione,grazie al driver importato,
	//con l'RDBMS specifico che stiamo usando

	//costruttore
	public Database(String nomeDb, String user,String password) {
		setPercorso(nomeDb);
		this.username = user;
		this.password = password;
	}

	public void setPercorso(String nomeDb) {
		//permette di sfruttare l'UTC
		String timeZone= "useUnicode="
				+ "true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode"
				+ "=false&serverTimezone=UTC";
		//in questa stringa che ho chiamto jdbc inserico l'indirizzo per arrivare 
		//al db tramite il driver
		//"jdbc:mysql:" -> � un protocollo che comunica l'uso della libreria jdbc e del driver di mysql
		//localhost:3306 -> indirizzo dove si trova il server del db, nel nostro caso � 
		//sul nostro pc quindi localhost alla porta 3306
		String jdbc = "jdbc:mysql://localhost:3306/";

		this.percorso = jdbc + nomeDb + "?" + timeZone;
	}

	//metodo che appartiene ad un oggetto di tipo Database 
	//al suo interno avr� delle operazioni per provare a raggiungere un db
	//quindi di apertura della connessione 
	public void apriConn() {

		try {
			//indico il percorso per raggiungere la classe Driver che si trova nella libreria importata
			Class.forName(DRIVER);
			//è un servizio che usando il driver corretto e le informazioni che passiamo come
			//parametri al metodo statico getConnection() permette di creare una connessione 
			//con uno specifico db, connessione che verr� salvata in un oggetto c di tipo Connection
			c = DriverManager.getConnection(percorso, username, password);
		}catch(ClassNotFoundException e) {
			System.out.println("Il driver non è corretto");
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("controlla il build path, lo user,password e il driver");
		}
	}

	//metodo che prova a chiudere la connessione
	public void chiudiConn() {
		try {
			c.close();
		}catch(Exception e) {
			System.out.println("connessione non chiusa");
		}

	}

	public Connection getC() {
		return c;
	}

	/**
	 * Metodo che creiamo per trasformare le righe di una tabella,che riceviamo
	 * come risposta di una interrogazione al db cio� una select, in una mappa
	 * Ogni mappa corrisponde ad un record, ad una riga della tabella
	 * Ogni mappa sar� composta dalle chiavi corrispondenti ai nomi delle colonne
	 * della tabella e dai valori, cio� i campi corrispondenti sempre nella tabella 
	 * @param String query: conterr� la query da inviare al database
	 * @param String...args -> args conterr� il valore da sostituire ai segnaposto della 
	 * 						   query. Il valore pu� variare a seconda della query che passiamo
	 * 						   args è di tipo [] -> � un array o vettore di String
	 * 						   ... -> i tre punti indicano che args è un array di string 
	 * 						   dalla grandezza opzionale. Non � detto che arrivino dei parametri,
	 * 						   � un valore che pu� essere 0 o n String, � un numero variabile
	 * 						   Le stringhe che vengono passate come args vengono inserite 
	 * 						   nell'array args, args verr� gestito come un array, si comporter�
	 * 						   come un vettore. Le stringhe raggruppate in args sono separate
	 * 						   dalla virgola, quindi ","	
	 * 						   String...args -> 
	 * 						   VARARGS cio� Variable Arguments			 	 				
	 * @return una mappa o una lista di mappe che contengono le informazioni salvate nella tabella
	 * 			del database che state interrogando 
	 */
	@Override
	public List<Map<String, String>> rows(String query, String... params) { 
		//andrà ad inserire in ogni mappa un insieme di coppie chiave valore, 
		//dove ogni chiave sarà il nome della colonna (label) e il valore
		//il valore assocciato a quella colonna per quel record
		List<Map<String, String>> ris = new ArrayList<Map<String,String>>();
		Map<String, String> riga;
		apriConn();
		try {
			System.out.println(query);
			//passo la query ad un oggetto di tipo PreparedStatement
			PreparedStatement ps = c.prepareStatement(query);
			//params indica un array di String che sono i valori da andare a mettere 
			//al posto dei placeholder
			//questi valori arrivano tutti sotto forma di stringhe poi sar� ps
			//che sapr� interpretare quei valori e passarli correttamente al db
			//se  non ci fossero parametri e uindi la dimensione dell'array fosse 0
			//il ciclo che stiamo per definire non partirebbe perch� la condizione
			//non sarebbe verificata
			//se ci dovessero essere pi� parametri dei segnaposto verr� generata un'eccezione
			for(int i = 0; i < params.length;i++) {
				ps.setString(i+1, params[i]);
			}
			//se c'erano dei parametri li ho inseriti nella query
			//a questo punto provo ad eseguirla
			//se va tutto bene ottengo come risposta una tabella che salvo in rs
			ResultSet rs = ps.executeQuery();

			//se ho una tabella con dei record salvati la itero per prenderne i valori
			while(rs.next()) {//mi permette di muovermi riga dopo riga per sequenza di righe
				//istanzio una mappa che mi permetter� di salvare i valori di una singola riga
				riga = new LinkedHashMap<String, String>();
				//itero per il numero di colonne, ad ogni colonna sar� associato un valore
				//con il for mi muovo colonna per colonna
				for(int i = 1; i <= rs.getMetaData().getColumnCount();i++) {
					riga.put(rs.getMetaData().getColumnLabel(i), rs.getString(i));
				}
				//aggiungo la mappa appena creata nell'AL ris
				//sono ancora nel while perch� le istruizioni inserite vengono ripetute
				//per tutte le righe della tabella
				//con il .next() presa una riga si passa alla successiva
				ris.add(riga);
			}
			/* il while cicla in verticale per ogni riga della tabella,ne prenda una 
			 * dopo l'altra
			 * il for cicla in orizzontale per ogni colonna, prende una colonna dopo l'altra 
			 */
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Sono nel metodo rows di Database");
		}
		chiudiConn();
		return ris;
	}

	/**
	 * Il metodo row prende solo una mappa dal ritorno del metodo sopra, cio�
	 * prende solo una riga(di solito la prima) della tabella risulante dalla query che mandiamo
	 * al database. Ad esempio serve quando cercate un record per id, oppre se state usando una funzione
	 * @param query che contiene la domanda che voglio inviare al database
	 * @param params -> varargs 
	 * @return una mappa che rapprensenta una riga della tabella 
	 */
	@Override
	public Map<String, String> row(String query, String... params) {
		Map<String, String> ris = null;
		try {
			ris = rows(query,params).get(0);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("sono nel metodo row di Database");
		}
		return ris;
	}


	/**
	 * Il metodo update viene usato per tutte le query di modifica dei datti all'interno del database
	 * Riceve una query e dei parametri che se ci sono corrisponderanno ai nuovi valori
	 * da inserire
	 * All'interno del metodo verr� presa la query e verr� aggiornata sostituendo i segnaposto con 
	 * i nuovi valori inseriti nel params
	 * Se va a buon fine il metodo verr� retituito true 
	 * @param query
	 * @param parametri inseriti in params
	 * @return true se i ddati vengono aggiornati, false se l'esito non � andato a buon fine
	 */
	@Override
	public boolean update(String query, String... params) {
		apriConn();
		try {
			PreparedStatement ps = c.prepareStatement(query);
			for(int i = 0; i < params.length;i++) {
				ps.setString(i+1, params[i]);
			}
			//provo ad eseguire la query che non deve ritornami una tabella
			//perch� non sto leggendo dei dati ma li sto manipolando
			System.out.println(query);
			int riga = ps.executeUpdate();
			if(riga > 0) {
				chiudiConn();
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Sono nel catch di update in Database");
		}
		chiudiConn();
		return false;
	}


}
