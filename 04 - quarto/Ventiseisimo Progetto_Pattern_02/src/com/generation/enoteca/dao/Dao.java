package com.generation.enoteca.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.generation.enoteca.entities.Database;
import com.generation.enoteca.entities.Entity;
import com.generation.enoteca.interfacce.Factory;
import com.generation.enoteca.interfacce.IDatabase;

public abstract class Dao {

    //classe padre da ereditare
    private IDatabase db;
    private String read;
    // private String update;
    // private String add;
    private String delete;
    private String tabella;

    

    public Dao() {
        //quando ci serve l'oggetto chiamiamo direttamente il getInstance() di Database
        //però se lo richiamiamo qua i figli avranno a disposizoine
        db = Database.getInstance(); 

        read = "select * from" + tabella;
        read = "select * from" + tabella + "where id=?";

        // create = "insert into" + tabella + " (chiavi) values([values]);";
        // update = "update" + tabella + " set [chiavi e valori] where id =?";

        //come passare il nome della tabella

    }







    public void setRead(String read) {
        this.read = read;
    }



    // public void setUpdate(String update) {
    //     this.update = update;
    // }



    public void setDelete(String delete) {
        this.delete = delete;
    }


    public void setTabella(String tabella) {
        this.tabella = tabella;
    }


    public IDatabase getDb() {
        return db;
    }


    public String getRead() {
        return read;
    }


    // public String getUpdate() {
    //     return update;
    // }

    // public String getAdd() {
    //     return add;
    // }

    public String getDelete() {
        return delete;
    }

    public void delete(int id) {
        db.executeUpdate(delete, id + "");
    }

    public String getTabella() {
        return tabella;
    }

    public List<Entity> getAll(String...params) {
        List<Entity> ris =  new ArrayList<>();
        //read = "select * from wine"
        //read = "select * from bottle"
        List<Map<String, String>> righe =  getDb().executeQuery(read, params);

        Entity e;
        for(Map<String, String> m : righe) {
            //trasforno la mappa in Entity
            m.put("tabella", tabella);
            e = Factory.make(m);

            if(e != null) {
                ris.add(e); 
            }

        }
        return ris;
    }



    
}
