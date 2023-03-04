
package com.generation.enoteca.entities;

import com.generation.enoteca.interfacce.IMap;
import com.generation.enoteca.utils.Validator;

public abstract class Entity implements IMap {

    private int id;


    public Entity() {}
    
    public Entity(String id) {  //in riferimento a Imap nello switch devo metterre anche qua string
        setId(id);
    }




    public int getId() {
        return id;
    }

    public void setId(String id) {      //in riferimento a Imap nello switch devo metterre anche qua string
       

        if(Validator.isNumberOk(id)){
            this.id = Integer.parseInt(id);
        }else {
            this.id = -1;
        }

    }

    @Override
    public String toString() {
        return "Id: " + id + "\n";
    }
    



}