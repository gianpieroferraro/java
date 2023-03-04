package com.libreria.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.libreria.entities.CasaEditrice;
import com.libreria.entities.Entity;
import com.libreria.entities.Libro;
import com.libreria.interfacce.EntityFactory;
import com.libreria.interfacce.IDaoCasaEditrice;

public class DaoCasaEditrice implements IDaoCasaEditrice{

	private static Database db;
	private static String tabella = "caseeditrici";
	private DaoLibri dao_libri;

	private static String insert = "insert into caseeditrici (nome, sede, generePiuTrattato) "+
			"values(?,?,?)";
	private static String read = "select * from " + tabella;
	private static String update = "update " + tabella +" set [colonna] = ? where id = ?";
	private static String updateObj = "update " + tabella + " set nome = ?, "+
										"sede = ?, generePiuTrattato = ? where id = ?";
	private static String delete = "delete from " + tabella + " where id = ?";

//	public DaoCasaEditrice(String nomedb, DaoLibri dao_libri) {
//		db = new Database(nomedb);
//		this.dao_libri = dao_libri;
//	}

	private static DaoCasaEditrice instance;
	
	private DaoCasaEditrice() {}
	
	public static DaoCasaEditrice getInstance() {
		if(instance == null) {
			instance = new DaoCasaEditrice();
			db = new Database("librerianuova", "root", "root");
		}
		return instance;
	}

	
	//metodo che permette un inserimento di una nuova casa edireice nel db
	//INSERT
	@Override
	public boolean create(CasaEditrice c) {
		//"insert into caseeditrici (nome, sede, generePiuTrattato) "+
		//"values(?,?,?)";
		return db.update(insert, c.getNome(),c.getSede(),c.getGenerePiuTrattato());
	}

	@Override
	public List<CasaEditrice> read(String query, String... params) {
		List<CasaEditrice> ris = new ArrayList<CasaEditrice>();
		//conterrà tutte le righe della tabella che sto leggendo, tabella che è
		//la risposta della query inviata al database
		List<Map<String, String>> righe = db.rows(query, params);
		Entity c;
		for(Map<String, String> riga : righe) {
			c = verificaOggetto(riga);
			if(c != null && c instanceof CasaEditrice) {
				((CasaEditrice)c).setLibri(cercaLibro(c.getId()));
				ris.add((CasaEditrice)c);
			}
		}
		return ris;
	}

	public List<Map<String, String>> listaCaseEditrici(){
		return db.rows(read);
	}
	public List<CasaEditrice> read(){
		return read(read);//seelct * from caseeditrici
	}
	
	
	/* metodo che controlla la mappa in entrata come parametro e a seconda del valore 
	 * associato alla proprietà tabella chiama il metodo make di EntityFactory
	 * che istanzia un oggetto 
	 */
	private Entity verificaOggetto(Map<String, String> map) {
		map.put("tabella", tabella);
	//	System.out.println(map);
		Entity e = EntityFactory.make(map);
		if(e == null) {
			System.out.println("Controlla la mappa " + map );
			return null;
		}
		return e;
	}
	
	//creo un metodo che in base all'id della casa editrice cerca i libri
	//ad essa associati tramite la relazione PK = FK
	//dove la PK è l'id della casaEditrice e la FK è l'id_casaEditrice in libri
	//queto metodo mi ritornerà tutti gli oggetti di tipo Libro che hanno questa corrispondenza
	private List<Libro> cercaLibro(int id){
		List<Libro> ris = new ArrayList<Libro>();
		//salvo in una lista di Entity il ritorno del metodo read() del daoLibri
		//cioè il ritorno della query "select * from libri" che ho scritto in daoLibri
		//quindi la lista di tutti i libri salvati nella tabella libri sul db
		List<Libro> libri = DaoLibri.getInstance().read();
		for(Libro e : libri) {
			if(e.getId_casaeditrice() == id)
				ris.add((Libro)e);
		}
		return ris;
	}
	
	@Override
	public boolean update(CasaEditrice c) {
//		 "update " + tabella + " set nome = ?, "+
//		 "sede = ?, generePiuTrattato = ? where id = ?";
		return db.update(updateObj, c.getNome(),c.getSede(),c.getGenerePiuTrattato()
				,c.getId()+"");
	}
	
	public boolean update(String colonna,String valore,int id) {
		//"update " + tabella +" set [colonna] = ? where id = ?";
		update = update.replace("[colonna]", colonna);
		return db.update(update,valore,id+"");
	}

	@Override
	public boolean delete(int id) {
		//"delete from " + tabella + " where id = ?";
		return db.update(delete, id +"");
	}

	@Override
	public CasaEditrice ricercaPerId(int id) {
		String query = "select * from libri where id = " + id;
		Map<String, String> riga = db.row(query);
		Entity e = verificaOggetto(riga);
		if(e != null && e instanceof CasaEditrice)
			return (CasaEditrice)e;
			
		return null;
	}

}
