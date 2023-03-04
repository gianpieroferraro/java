package com.generation.enoteca.dao;

import java.util.List;

import com.generation.enoteca.entities.Wine;
import com.generation.enoteca.interfacce.IDaoWine;

public class DaoWineProxy implements IDaoWine{

    //oggetto da migliorare
    private DaoWine daoWine;

    //proprietà dao
    private List<Wine> cache;

    //instanza statica dello stesso tupo della classe o dell'interfaccia per poter usare il singleton
    private static IDaoWine instance;

    //costruttore privato 
    private DaoWineProxy() {
        //istanzio l'oggetto da miglioare in modo da poterne usare le funzionalitàà
        daoWine= (DaoWine) DaoWine.getInsatnce();

    }

    

    public static IDaoWine getInstance() {
        if(instance == null)
            instance = new DaoWineProxy();
        return instance;
    }


    @Override
    public List<Wine> getWines() {
        return daoWine.getWines();
    }


    @Override
    public List<Wine> getAllWines(String query, String... params) {
        return daoWine.getAllWines(query, params);
    }

    @Override
    public Wine getWineByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWineByName'");
    }

    @Override
    public void update(Wine Wine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void update(String colonna, String valore, int id) {
        daoWine.update(colonna, valore, id);
        refresh();
    }

    @Override
    public void delete(int id) {
        daoWine.delete(id);
        refresh();
    }

    @Override
    public void add(Wine Wine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void open() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'open'");
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }



    public List<Wine> getCache() {
        if(cache == null) //se non ho salvato i vini nella lista chiamo il read dei vini e li salvo
            cache = getWines(); //read dei vini

            //se invece li ho gia salvati ritorno il valore di quella lista
        return cache;
    }   

    //metodo refresh
    public void refresh() {

        cache = getWines(); //chiamo questo metodo quando modifico la lista dei vini(delete, add, update)
        //

    }
    
}
