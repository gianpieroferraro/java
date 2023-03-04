package com.generation.enoteca.interfacce;

import java.util.Map;

import com.generation.enoteca.entities.Bottle;
import com.generation.enoteca.entities.Entity;
import com.generation.enoteca.entities.Wine;

public interface Factory {



    public static Entity make(Map<String, String> map) {

        Entity e = null;

        //classe adibita a istanziare oggetti
        if(map.containsKey("tabella")) {
            String valore = map.get("tabella");
            switch(valore.toLowerCase()) {
                case "wine":
                    e = new Wine();
                    break;
                case "bottle":
                    e = new Bottle();
                    break;

            }
            if(e != null) {
                map.remove("tabella");
                e.fromMap(map);
            }
        }

        return e;

    }

    
}
