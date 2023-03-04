package com.libreria.dao;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	
	//dichiaro le propriet� di un oggetto di tipo Database
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
	public Database(String nomeDb) {
		this.username = "root";
		this.password = "Kimiraikkonen7.";
		setPercorso(nomeDb);
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
			//� un servizio che usando il driver corretto e le informazioni che passiamo come
			//parametri al metodo statico getConnection() permette di creare una connessione 
			//con uno specifico db, connessione che verr� salvata in un oggetto c di tipo Connection
			c = DriverManager.getConnection(percorso, username, password);
		}catch(ClassNotFoundException e) {
			System.out.println("Il driver non � corretto");
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

	
}
