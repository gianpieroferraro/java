package com.generation.enoteca.dao;

import java.util.List;

import com.generation.enoteca.entities.Bottle;
import com.generation.enoteca.entities.Wine;
import com.generation.enoteca.interfacce.IDaoBottle;
import com.generation.enoteca.interfacce.IDaoWine;

public class Cantina {

    //facede cantina
    //riportare alla semplicità piu estrema un modo che sta al di sotto molto complesso


    private IDaoBottle bottleService;
    private IDaoWine wineService;


    public Cantina(IDaoBottle bottle, IDaoWine wine) {

        this.wineService = DaoWine.getInstance();
        this.bottleService = DaoBottle.getInstance();  //POTREBBE CONTENERE: DaoBottle, servizioVini

    }

    public void addBottle(Bottle b) {
        // bottleService.open()
        bottleService.add(b);
        // bottleService.clone();

    }

    public List<Bottle> getAllBottles() {

        // bottleService.open()
       return bottleService.getAllBottles();    //bottiglie con il vino contenuto nella bottiglia
        // bottleService.clone();
    }

    
    public List<Bottle> getBottlesByWine(Wine wine) {

        return bottleService.getBottlesByWine(wine);
    }
    
}
