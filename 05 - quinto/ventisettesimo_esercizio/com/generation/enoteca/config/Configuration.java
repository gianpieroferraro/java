package com.generation.enoteca.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

import com.generation.enoteca.dao.DaoBottle;
import com.generation.enoteca.dao.DaoWine;
import com.generation.enoteca.entities.Database;
import com.generation.enoteca.utils.Reader;

public class Configuration {
    
    //oggetto che serve per caricare i dati che ci servono per il progetto
    private static Map<String, String> properties;
    private static Map<String, Object> dependencies;


    //sigleton
    private static Configuration instance; //-> proprietà variabile di tipo della classe --- non si potrebbe fare ma se lo rendi statico si, perché
    //diventa una proprietòà di classe

    //costruttore privato -- mi aspetto che la prima volta che si fa getInstance si carichi dei valori
    private Configuration() {

        //properties al momento che viene chiamato verrà iniziallizzato
        properties  = Reader.readMap("src/app.properties"); //mi aspetto che abbia 6 chiavi con sei valori associati
        dependencies = loadDependencies();
    }

    //synchronized --> invece di crearne due la priva volta lo crea e la seconda lo richiama
    public synchronized static Configuration getInstance() {

        if(instance == null) {
            instance = new Configuration();
        }

        return instance;
    }
    

    public static  Map<String, String> getProperties() {
        return properties;
    }


    private Map<String, Object> loadDependencies() {

        Map<String, Object> ris = new HashMap<>();

        //ci carichiamo tutti gli oggetti che mi servono -- un lettore
        ris.put( "database", Database.getInstance() );
        ris.put( "daowine", DaoWine.getInstance() );
        ris.put( "daobottle", DaoBottle.getInstance() );

        return ris;

    }
    public static Object get(String chiave) {

        if(dependencies.containsKey(chiave)){
            return dependencies.get(chiave);
        }
        return null;
    }




}
