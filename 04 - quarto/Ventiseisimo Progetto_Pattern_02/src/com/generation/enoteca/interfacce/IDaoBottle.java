package com.generation.enoteca.interfacce;

import java.util.List;

import com.generation.enoteca.entities.Bottle;
import com.generation.enoteca.entities.Wine;

public interface IDaoBottle {

    List<Bottle> getAllBottles();
    List<Bottle> getAllBottles(String query, String...params);
    List<Bottle> getBottlesByWine(Wine wine);
    
    void update(Bottle bottle);
    void delete(int id);
    void add(Bottle bottle);
    void update(String colonna, String valore, int id);
}
