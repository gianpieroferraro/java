package com.generation.enoteca.interfacce;

import java.util.List;
import java.util.Map;

public interface IDatabase {


	/*
	 * pattern strutturale: FACADE
	 * 
	 * 1. il software dipende da un numero elevato di intergfacce diverse, non sempre ben strutturate e a volte
	 * troppo interdipendenti tra loro.
	 * 2. costruire una facciata, cuoè una superinterfaccia che userà o conterrà tutti i componenti che queste
	 * interfacce forniscono e di cui questa facciata ha bisogno. Ne maschera il funzionamento e le coordinerà pr
	 * rendere più semplice l'uso.
	 * 3. soggetti:
	 * 	- un numero di interfacce usate dal sistema, dal Client che non dovrebbero essere esposte a volte mal costruite
	 * 	- una super-interfaccia, Facade, che permette un diverso, un nuovo uso, per accedere alle interfacce che sono state coperte
	 * 	- qualcuno che usa e interagisce con la facade cioè i vari Client
	 * 
	 *  è un pattern usato per coprire ad esempio le librerie che hanno tante dipendenze cpme JDBC(Java Database Connectivity)
	 * 
	 * Questa libreria lavora tramite delle Interfacce: 
	 * 	-Connection(che rappresenta la connesione); 
	 * 	-PrepareStatement/Statement(che rappresenta un comando SQL)
	 * 	-ResultSet (che rappresenta un insieme di risultanti )
	 * Di cosa facciano le classi modello sottostanti di questi oggetti che formalmente sono mascherati da interfacce, non ci interessa.
	 * 
	 * PrepareStatement lo creiamo tramite un metodo .prepareStatement() che è di un oggetto di tipo COnnection.
	 * Per ottenere dei risultati dobbiamo usare ResultSet traminte una sequenza(che è sempre la stessa) 
	 *  Inoltre qiesti risultati in formato ResultSet non sono di semplice uso perché non è un formato standard
	 * 
	 * Come soluzione al problema facciamo implementare alla classe Database che usa queste interfacce, un'interfaccia IDatabase 
	 * con dei metodi che restituiscono list e Map.
	 * 
	 * IDatabase, cioè una Facede, usa JDBC ma la nasconde e ritorna oggetti java(List e Map) non in formato tabellare come ResultSet che
	 * sono più facili e che possiaomo sfruttare senza dover ogni volta ripeter quella sequenza di apertira della  connesisone,
	 * creazione del comando da inviare al db e restituzione del risultato come tabella
	 * 
	 * IDatabase (cioè la Facede); Database che la implementa; JDBC che viene coperto(stiamo comprendo anche ResulSet, PrepareStatemente e Connection)
	 * 
	 */




    
    List<Map<String, String>> executeQuery(String query, String...args);
    
	Map<String,String> row(String query,String...params);
	boolean executeUpdate(String query,String...params);

	void closeConn();
    




}
