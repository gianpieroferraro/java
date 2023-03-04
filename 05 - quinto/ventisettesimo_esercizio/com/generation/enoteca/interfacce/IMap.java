package com.generation.enoteca.interfacce;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.generation.enoteca.entities.Entity;

public interface IMap {
    
    //metodi di defoult gia implementati

    default Entity fromMap(Map<String, String> map) {

        //devo accedere all'oggetto di tipo class che lo chimarà e ornirmi i dati
        for(Method m : this.getClass().getMethods()) {
            if(m.getName().startsWith("set") && m.getParameterCount() == 1 ) {      //setNome

                String nome = m.getName().substring(3); //Nome
                nome = Character.toLowerCase(nome.charAt(0)) + nome.substring(1); //es. setNome --> n + ome
                if(map.containsKey(nome)) {
                    String valore = map.get(nome);
                    if(valore == null) 
                        continue; 
                        
                    //se non è null provo a passare il valore alla proprietà
                    try {
                        //devo prendere il tipo
                        String tipoProp = m.getParameters()[0].getType().getSimpleName().toLowerCase(); //prendo l'unico parametro e
                        switch(tipoProp){
                            case "string":
                                m.invoke(this, valore); //come scrivere --> this.set(valore)
                                break;

                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                        System.out.println("sono nel fromMap di Imap");
                    }


                }
            }

        }
        return null;


    }


    default Map<String,String> toMap()  {
        Map<String, String> ris = new LinkedHashMap<>();

        for(Method m : this.getClass().getMethods()) {
            // System.out.println(m.getName());

            //prendo metodo che inizia per set e che abbia un parametrp
            if(m.getName().startsWith("get") || m.getName().startsWith("is") 
                && !m.getName().equalsIgnoreCase("getClass")
                && m.getParameterCount() == 0) {
                    
                int caratteri  = m.getName().startsWith("get")? 3:2;    // getNome
                String nome = m.getName().substring(caratteri);//Nome
                nome = Character.toLowerCase(nome.charAt(0)) + nome.substring(1); //n + ome -> nome


                try {
                    
                    ris.put(nome, m.invoke(this) + "");

                } catch (Exception e) {

                    e.printStackTrace();
                    System.out.println("sono in toMap di IMap");
                }

            }

        
        }
        return ris;
    }


}
