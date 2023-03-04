package com.generation.enoteca.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class Reader {


    public static List<String> readFile(String percorso) {
        List<String> ris = new ArrayList<>();
        Scanner dati = null;
        try {

            dati = new Scanner(new File( percorso));
            while(dati.hasNextLine()){
                ris.add(dati.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("controlla il percorso del file");
        }
        if(dati != null){
            
            dati.close();
        }

        return ris;
    }

    public static Map<String, String> readMap(String percorso) {
        Map<String, String> mappa = new HashMap<>();
        // jdbc=jdbc:mysql://localhost:3306/
        for(String riga : readFile(percorso)) {

            mappa.put(riga.split("=", 2)[0], riga.split(",", 2)[1]);   //split("=", 2) per prendere solo due valori, quello prima il separatore e dopo poi basta

        }

        return mappa;
    }
    
}
