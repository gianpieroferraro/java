package com.generation.enoteca.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.generation.enoteca.config.Configuration;
import com.generation.enoteca.entities.Bottle;
import com.generation.enoteca.entities.Database;
import com.generation.enoteca.entities.Entity;
import com.generation.enoteca.entities.Wine;
import com.generation.enoteca.interfacce.Factory;
import com.generation.enoteca.interfacce.IDaoWine;
import com.generation.enoteca.interfacce.IDatabase;
import com.mysql.cj.xdevapi.PreparableStatement;

public class DaoWine extends Dao implements IDaoWine {


    private static IDaoWine instance;
    private IDatabase db;
    private static String insert = "insert into wine (id,name,description) values(?,?,?)";
    private static String read = "select * from wine";
    private static String update = "update wine set [colonna]= ? where id =?";
    private static String updateObj = "update wine set name= ? description=? where id =?";
    private static String delete = "delete from wine where id =?";








    //ci servono alcune query, ma non tutte
    private DaoWine() {
        db = (IDatabase)Configuration.get("database");
    }



    public static IDaoWine getInstance() {
        if(instance == null) {
            instance = new DaoWine();
        }
        return instance;
    }





    
   
    
    
    //apertura e chiusura di una connessione che deve avvenire ogni volta che si interroga il db
    @Override
    public void close() {
        //bisgona castarlo se non c'è in IDatabase

        if(getDb() instanceof Database) {

            getDb().closeConn();    
        }
    }

    @Override
    public void open() {
        //bisgona castarlo perchè  non abbiamo messo la firma in IDatabase e il metdoo si trova in Database

        ((Database)getDb()).openConn();
        
        
    }




    @Override   // è come un select * ...
    public List<Wine> getWines() {
        List<Wine> ris = new ArrayList<>();
        open();

        Entity e;
        List<Map<String, String>> mappe = db.executeQuery(read);

        for(Map<String,String>m: mappe) {
            m.put("tabella", "wine");
            e = Factory.make(m);
            e.fromMap(m);

            ((Wine)e).setBottiglie(searchBottle(e.getId()));

            ris.add((Wine)e);   //bisogna castare perché la lista sopra è di tipo Wine
            
        }


        close();
        return ris;
    }



    //ritorna lista di bottiglie perché abbiamo un arrayList dentro vino
    public ArrayList<Bottle> searchBottle(int id) {

        ArrayList<Bottle> ris = new ArrayList<>();

        for(Bottle b : DaoBottle.getInstance().getAllBottles()) {
            if(id == b.getWineId()) {

                ris.add(b);
            }
        }

        return ris;
    }



    @Override   //come una join
    public List<Wine> getAllWines(String query, String... params) {
       
        // List<Wine> ris = new ArrayList<>();
        return null;



    }



    @Override
    public Wine getWineByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWineByName'");
    }



    @Override
    public void update(Wine Wine) {
        
        db.executeUpdate(updateObj, Wine.getName(),Wine.getDescription(), Wine.getId() + "" ); //riferito ai ? sopra nella proprietà update

    }



    @Override
    public void update(String colonna, String valore, int id) {
        //update wine set [colonna]= ? where id =?
        update.replace("[colonna]", colonna);
        db.executeUpdate(update, valore, id + "");

    }



    @Override
    public void add(Wine Wine) {
        
        //insert into wine (id,name,description) values(?,?,?)

        db.executeUpdate(insert, Wine.getId() +"", Wine.getName() ,Wine.getDescription());

    }
    

    public void delete(int id) {
        db.executeUpdate(delete, id+"");
    }




}
