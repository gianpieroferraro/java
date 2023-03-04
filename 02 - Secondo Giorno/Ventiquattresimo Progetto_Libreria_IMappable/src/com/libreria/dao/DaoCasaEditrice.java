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

public class DaoCasaEditrice {

	private Database db;
	private static String tabella = "caseeditrici";
	private DaoLibri dao_libri;

	private static String insert = "insert into caseeditrici (nome, sede, generePiuTrattato) "+
			"values(?,?,?)";
	private static String read = "select * from " + tabella;
	private static String update = "update " + tabella +" set ? = ? where id = ?";
	private static String updateObj = "update " + tabella + " set nome = ?, "+
										"sede = ?, generePiuTrattato = ? where id = ?";
	private static String delete = "delete from " + tabella + " where id = ?";

	public DaoCasaEditrice(String nomedb, DaoLibri dao_libri) {
		db = new Database(nomedb);
		this.dao_libri = dao_libri;
	}

	public void add(CasaEditrice c) {
		db.apriConn();
		try {
			PreparedStatement ps = db.getC().prepareStatement(insert);
			ps.setString(1,c.getNome());
			ps.setString(2,c.getSede());
			ps.setString(3, c.getGenerePiuTrattato());
			int rows = ps.executeUpdate();
			if(rows > 0)
				System.out.println("casa editrice inserita");
			ps.close();

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("sono nel catch di add di daoCasaEd");
		}
		db.chiudiConn();
	}

	public List<Map<String, String>> readMappe(String query) {
		List<Map<String,String>> ris;
		ris = new ArrayList<Map<String,String>>();
		Map<String,String> map;
		db.apriConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try { //"select * from caseeditrici join libri on caseeditrici.id = libri.id_casaEditrice"
			ps = db.getC().prepareStatement(query);
			rs = ps.executeQuery();

			while(rs.next()) {
				map = new LinkedHashMap<String, String>();	
				int nColonne = rs.getMetaData().getColumnCount();
				for(int i = 1; i <= nColonne ;i++) {
					String nomeColonna = rs.getMetaData().getColumnName(i);
					String valore = rs.getString(i);
					map.put(nomeColonna, valore);
				}
				ris.add(map);
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("sono nel catch di readMappe(stringa) in daoCasaEd");
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
	public List<Entity> read(){
		List<Entity> ris = new ArrayList<Entity>();
		db.apriConn();
		List<Map<String, String>> mappe = new ArrayList<Map<String,String>>();
		Entity en;
		try {
			mappe = readMappe(read); //select * from caseeditrici
			for(Map<String, String > m : mappe) {
				en = verificaOggetto(m);
				if(en != null && en instanceof CasaEditrice) {
					CasaEditrice casa = (CasaEditrice) en;
					casa.setLibri(cercaLibro(casa.getId()));
					ris.add(en);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("sono nel catch di read() in DaocasaEd");
		}
		return ris;
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
		List<Entity> libri = dao_libri.read();
		for(Entity e : libri) {
		//	System.out.println(e.toString());
			if(e instanceof Libro) {
				if(((Libro)e).getId_casaeditrice() == id)
					ris.add((Libro)e);
			}
		}
		return ris;
	}
	
	public void update(int id,String colonna,String nuovoValore) {
		db.apriConn();
		//"update " + tabella +" set ? = ? where id = ?";
		try {
			PreparedStatement ps = db.getC().prepareStatement(update);
			ps.setString(1, colonna);
			ps.setString(2, nuovoValore);
			ps.setInt(3, id);
			ps.executeUpdate();
			ps.close();
		}catch(Exception e) {
			System.out.println("sono nel catch di update in daoCaseEd");
		}
		db.chiudiConn();
	}

	public void update(CasaEditrice c) {
		db.apriConn();
//		"update " + tabella + " set nome = ?, "+
//		"sede = ?, generePiuTrattato = ? where id = ?";
		try {
			PreparedStatement ps = db.getC().prepareStatement(updateObj);
			ps.setString(1, c.getNome());
			ps.setString(2, c.getSede());
			ps.setString(3,c.getGenerePiuTrattato());
			ps.setInt(4, c.getId());
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
				System.out.println("casa editrice eliminata");
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("controlla la sintassi e i dati della query:\n" + delete);

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Sono nel catch di delete di daoCaseEd");
		}
		db.chiudiConn();
	}

	
	
	
	
	
	
	
	
	
	
	
	

}
