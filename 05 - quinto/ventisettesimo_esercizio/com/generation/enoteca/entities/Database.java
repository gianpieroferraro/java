package com.generation.enoteca.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.generation.enoteca.config.Configuration;
import com.generation.enoteca.interfacce.IDatabase;

public class Database implements IDatabase{
    
    private String percorso;
    private String username, password;
    private Connection c;


    private static IDatabase instance; // per convenzione il nome della prop è instance

    private Database() {
        //una volta caricato
        this.username = Configuration.getProperties().get("username"); //per la chiave username forniscimi il vaolre
        this.password = Configuration.getProperties().get("password"); 
        this.percorso = Configuration.getProperties().get("jdbc") + Configuration.getProperties().get("nomedb") + 
                        Configuration.getProperties().get("timezone");
    };



    


    public static IDatabase getInstance() {
        if(instance == null) {

            instance = new Database();
        }
        //se c'è gia la ritorno con la riga sotto
        return instance;
    }


    public void openConn() {


        try {
            
            String driver = Configuration.getProperties().get("driver");
            Class.forName(driver);
            c = DriverManager.getConnection(percorso, username, password);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connessione non aperta:\ncontrolla il build path, user, percorso e la password");
        }

    }

    public void closeConn() {
        try {

            c.close();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("connessione non chiusa");
        }
    }









    public Connection getC() {
        return c;
    }


    


    //crea la tabella -- tutte le query ogni volta che ne fai una
    @Override
    public List<Map<String, String>> executeQuery(String query, String... args) { //DQL

        List<Map<String, String>> ris = new ArrayList<>();

        Map<String, String> riga;
        try {

            PreparedStatement ps = c.prepareStatement(query);
            //vedo se ci sono dei placehoders dfa sostiturire
            for(int i=0; i<args.length; i++ ) {
                //sostituisco
                ps.setString(i+1, args[1]);
            }
            //una volta sostituiti provo ad eseguire la query
            ResultSet rs = ps.executeQuery();

            //iteriamo la tabella
            while(rs.next()) {

                riga = new LinkedHashMap<>();

                for(int i=0; i<rs.getMetaData().getColumnCount(); i++ ) {
                    riga.put(rs.getMetaData().getColumnLabel(i), rs.getString(i));
                }
                //una volta finito salvo la mappa nella lista delle mappe
                ris.add(riga);
            }



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Catch di execute Query in Database");
        }

        return null;
    }

    @Override
    public Map<String, String> row(String query, String... params) {
        try {

            return executeQuery(query, params).get(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("");
            return null;
        }
    }




    //gestisce le modifiche fatte sulle celle -- DML (query di manipolazione)
    @Override
    public boolean executeUpdate(String query, String... params) { 

        //mandiamo quella query al db
        try {

            PreparedStatement ps = c.prepareStatement(query);
            //se ci sono segnaposto devo scambiarli
            for(int i=0; i<params.length; i++ ) {
                ps.setString(i+1, params[i]);
            }

            int c = ps.executeUpdate();
            if(c >0) {
                ps.close();
                return true;
            }

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("sono nel catch di executeUpdate in Database");
        }

        return false;
    }




}
