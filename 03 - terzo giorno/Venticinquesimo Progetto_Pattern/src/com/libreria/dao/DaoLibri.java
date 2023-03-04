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
import com.libreria.interfacce.IDaoLibri;

public class DaoLibri implements IDaoLibri{

	private static Database db;
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
	
	/* pattern creazionale: SINGLETON
	 * -1. problema: evitare di creare più volte lo stesso oggetto
	 * 	   sia per ragioni di sicurezza che per ragioni di prestazione
	 * 	   ad esempio non voglio duplicare un registro di dati
	 * 	   di solito viene creato un solo dao per tipo
	 * -2. viene usato per garantire al massimo una istanza di un oggetto. Definito un oggetto
	 *     non vogliamo ripetizioni e quindi dobbiamo modificare la classe dell'oggetto
	 *     e il suo costruttore in modo da evitare repliche e crearne al massimo uno.
	 *     Questo passaggio richiede un approccio standard che è il singleton
	 * -3. soggetti del pattern:
	 *     - il Client: chi ha bisogno dell'oggetto e che quindi usar� il Singleton
	 *     - il Singleton stesso che rappresenta l'oggetto
	 *     Punti chiave:
	 *     - un costruttore privato e vuoto. Privato signigica che non pu� essere usato e richiamato
	 *       fuori dalla classe
	 *     - una PROPRIETA' STATICA(della classe) privata che è una istanza cioè un oggetto
	 *     che è anche l'unico oggetto possibile di questo tipo cioè del tipo della classe 
	 *     in cui abbiamo inserito il Singleton
	 *     -> questo significa che l'oggetto in questo caso DaoLibri che è una proprietà statica
	 *     della classe DaoLibri appartiene alla classe stessa. 
	 *     Ovviamente il tipo dell'oggetto deve essere lo stesso della classe
	 *     ma il nome per convenzione è instance.
	 *     - ci deve essere un metodo statico GET public che vi ritorni il valore di quell'oggetto
	 * 
	 */
	//costruttore
//	public DaoLibri(String nomeDb) {
//		db = new Database(nomeDb);
//	}

	//istanza statica del tipo della classe
	private static DaoLibri instance;
	

	//costruttore privato e vuoto
	private DaoLibri() {}

	//metodo get public che ritorni il valore della propriet� statica
	//verifica se l'istanza instance di tipo DaoLibri esista già
	//se esiste ritorna direttamente il suo valore, cio� un oggetto
	//se non esiste ed � null lo istanzia, lo crea e lo ritorna
	public static DaoLibri getInstance() {
		if(instance == null) {
			instance = new DaoLibri();
			db = new Database("libreria_prova", "root", "Kimiraikkonen7.");
		}
		return instance;
	}

	
	//ritorna i libri come oggetti salvati nella tabella libri del database
	//per funzionare ha bisogno di una query e se necessario di parametri
	@Override
	public List<Libro> read(String query, String... params) {
		List<Libro> ris = new ArrayList<Libro>();
		Entity e;
		//salvo le righe, i record della tabella che ricevo come risultato dalla query
		//che ho inviato al database
		List<Map<String, String>> righe = db.rows(query, params);
		for(Map<String, String> m : righe) {
			m.put("tabella",tabella);
			e = EntityFactory.make(m);
			if(e instanceof Libro)
				ris.add((Libro)e);
		}
		return ris;
	}
	
	public List<Map<String, String>> listaLibri(){
		List<Map<String, String>> righe = db.rows(read);
		return righe;
	}
	 public List<Libro> read(){
		 //select * from libri;
		 return read(read);
	 }
	
	//metodo che ritorna i libri di uno specifico autore
	public List<Libro> libriPerAutore(String autore){
		return read("select * from libri where autore = ?", autore);
	}
	
	//metodo che ritorna i libri che costano pi� di 20 euro pubblicati dopo il 2020
	public List<Libro> ricercaLibriPrezzoEAnno(int anno,double prezzo){
		return read("select * from libri where prezzo > ? and year(data_pubblicazione)"
				+ " > ?", (""+prezzo),(anno +""));
	}

	
	/**
	 * Metodo che viene invoca per inserire un nuovo record nella tabella libri nel
	 * database con il quale il dao � collegato
	 * Prende in input un oggetto di tipo Libro che avr� dei valori asseganti alle propriet�
	 * quei valori li vogliamo salvare nel db
	 * Dovr� passare una query al db che sia di insert, a quella query dovr� 
	 * sostituire i segnaposto, i ?, con i valori delle propriet� dell'oggetto passato come parametro 
	 * Il metodo update in Database esegue questa operazione, manda la query e se
	 * l'esito finale � positivo ritorner� true
	 * @param Libro l
	 * @return boolean: true se � avvenuto l'inserimento, false se c'� stato un errore e non � stato
	 * aggiunto il record nella tabella 
	 */
	@Override
	public boolean create(Libro l) {
//		"insert into libri (autore,titolo,genere,numero_pagine,prezzo,"+
//		"disponibile,data_pubblicazione)\r\n" +
//		"values(?,?,?,?,?,?,?)";
		//il varargs � un array di String separate dalla virgola
		return db.update(insert,l.getAutore(),l.getTitolo(),l.getGenere(),
							  l.getNumero_pagine()+"",l.getPrezzo()+"",l.isDisponibile() +"",
							  l.getData_pubblicazione() +"");
	}

	@Override
	public boolean update(Libro l) {
//		"update libri set autore = ?, titolo = ?, " +
//		"genere = ? , numero_pagine = ?, data_pubblicazione = ?, " 
//		+  "disponibile = ?, prezzo = ? "+
//		"where id = ?";
		return db.update(updateObj,l.getAutore(),l.getTitolo(),l.getGenere(),
				l.getNumero_pagine()+"",l.getData_pubblicazione() +"",l.isDisponibile() +""
				,l.getPrezzo()+"",l.getId()+"");
	}

	public boolean update(String colonna,String valore,int id) {
		//"update libri set [colonna]= ? where id = ?"
		String query = update.replace("[colonna]", colonna);
		return db.update(query,valore,id+"");
	}

	@Override
	public boolean delete(int id) {
		//"delete from libri where id = ?"
		return db.update(delete, id+"");
	}



}
