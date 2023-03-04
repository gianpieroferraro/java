package com.generation.enoteca.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.generation.enoteca.entities.Database;
import com.generation.enoteca.entities.Entity;
import com.generation.enoteca.entities.Wine;
import com.generation.enoteca.interfacce.Factory;
import com.generation.enoteca.interfacce.IDaoWine;

public class DaoWine extends Dao implements IDaoWine {


    private static IDaoWine instance;

    //ci servono alcune query, ma non tutte
    private DaoWine() {
        super();
        setTabella("wine");
    }

    public static IDaoWine getInsatnce() {
        if(instance == null) {
            instance = new DaoWine();
        }
        return instance;
    }





    
    public List<Wine> getWines() {
        setRead("select * from wine");
        List<Wine> vini =  getAllWines();
        return vini;
    }

   
    private List<Wine> getAllWines(String...params) {

        
        List<Wine> vini =  new ArrayList<>();

        for(Entity e : super.getAll(params)) {
            
            if(e!= null && e instanceof Wine)
                vini.add((Wine) e);
        
        }

        return vini;
    }



    @Override
    public List<Wine> getAllWines(String query, String...params) {
        
        setRead(query);

        List<Wine> vini =  getAllWines(params);
        
        return vini;
    }



    @Override
    public Wine getWineByName(String name) {
        for(Wine wine : getWines()) {
			if(wine.getName().equalsIgnoreCase(name))
				return wine;
		}
		return null;
    }

    @Override
    public void update(Wine wine) {
        String query = "update wine set name = ?, set descrizione = ? where id = ?";
        super.getDb().executeUpdate(query, wine.getName(), wine.getDescription(), wine.getId() + "");
    }

    @Override
    public void update(String colonna, String valore, int id) {
        String query  ="update wine set [colonna] =? where id =?";
        query = query.replace("[colonna]", colonna);
        super.getDb().executeUpdate(query, valore, id + "");

    }


    @Override
    public void add(Wine wine) {
        String query = "insert into wine (name, description) values(?,?)";
        super.getDb().executeUpdate(query,wine.getName(), wine.getDescription());
    }

    
    
    
    //apertura e chiusura di una connessione che deve avvenire ogni volta che si interroga il db
    @Override
    public void close() {
        //bisgona castarlo se non c'è in IDatabase

        if(super.getDb() instanceof Database) {

            super.getDb().closeConn();    
        }
    }

    @Override
    public void open() {
        //bisgona castarlo perchè  non abbiamo messo la firma in IDatabase e il metdoo si trova in Database

        ((Database)super.getDb()).openConn();
        
    }
    





}
