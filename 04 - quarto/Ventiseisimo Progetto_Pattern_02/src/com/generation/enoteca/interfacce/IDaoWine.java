package com.generation.enoteca.interfacce;

import java.util.List;

import com.generation.enoteca.entities.Wine;

public interface IDaoWine {
   



    List<Wine> getWines();
    List<Wine> getAllWines(String query, String...params);
    Wine getWineByName(String name);
    void update(Wine Wine);
    void update(String colonna, String valore, int id);
    void delete(int id);
    void add(Wine Wine);
    void open();
    void close();

    



}
