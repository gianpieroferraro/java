package com.libreria.interfacce;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public interface IMapp {

	/* void fromMap(Map<String,String map)
	 * tramite questo metodo prendiamo dei dati che arrivano da una mappa
	 * e li usiamo per modificare lo stato di un oggetto, cioè
	 * passiamo i dati ai set dell'oggetto
	 * Per fare questo passaggio supponiamo che:
	 * - la Entity che invocherà questo metodo abbia 
	 * -- i set per tutti gli attributi
	 * -- che i setter siano visibili 
	 * -- che ci siano un costruttore vuoto
	 * - che la mappa contenga dei valori
	 * -- che le chiavi contenute nella mappa corrispondano ai nomi 
	 * -- delle proprietà della Entity
	 * 
	 * se un oggetto invoca il metodo fromMap()
	 * mi aspetto che non restituisca nulla ma da una mappa prenda quei valori
	 * e li passi ai setter dell'oggetto in moda modicare lo stato dell'oggetto
	 * e assegnare dei valori alle sue proprietà
	 */
	//fromMap(Map<String,String> map) -> da mappa a Entity per assorbire i dati
	//da varie sorgenti(db o file)
	default void fromMap(Map<String,String> map) {
		//itero ogni metodo come oggetto di tipo Method che ottengo dal metodo .getClass()
		for(Method m : this.getClass().getMethods()) {
		//	System.out.println(m.getName());
			if(m.getName().startsWith("set") && m.getParameterCount() == 1) {//setTitolo
				String nomeProp = m.getName().substring(3);//titolo
				 nomeProp = Character.toLowerCase(nomeProp.charAt(0)) //generePiuTrattato
						 + nomeProp.substring(1);					  //generePiuTrattato	
				if(map.containsKey(nomeProp)) {
					//prendo il valore associato alla chiave che ha lo stesso della proprietà
					String valore = map.get(nomeProp);
					if(valore == null) 
						continue;
					try {
						//ricerco il tipo di dato che il set possa accettare
						String tipoParametro = m.getParameters()[0].getType().getSimpleName().toLowerCase();
						//string,int,double,boolean,localdate
					//	System.out.println(tipoParametro);
						switch(tipoParametro) {
						case "string":
							//se il parametro è una stringa mi aspetto che
							//questo metodo m invochi l'oggetto a cui appartiene e prendaa anche 
							//il valore che deve passare alla proprietà
							m.invoke(this, valore);
							break;
						case "double":
							/* m è un metodo, un oggetto di tipo Method della classe
							 * this è l'istanza che invoca questo metodo, un oggetto
							 * se l'oggetto, this, è un oggetto della classe a cui
							 * appartiene il metodo allora il l'invoke farà si chè venga chiamato 
							 * il metodo stesso e agisca il comportamento per cui è stato creato
							 */
							m.invoke(this, Double.parseDouble(valore));
							break;
						case "integer":
						case "int":
							m.invoke(this, Integer.parseInt(valore));
							break;
						case "boolean":
							m.invoke(this, valore.equalsIgnoreCase("1") ||
									valore.equalsIgnoreCase("true")?true:false);
							break;
						case "localdate":
							LocalDate d = LocalDate.parse(valore);
							m.invoke(this, d);
							break;
						}

					}catch(NumberFormatException e) {
						e.printStackTrace();
						System.out.println("Controlla i valori numerici");
					}catch(Exception e) {
						e.printStackTrace();
						System.out.println("sono nel catch di fromMap()");
					}
				}
			}
		}
	}

	//da Entity(oggetto) a mappa
	//trasformo una entity in una mappa, cioè passo nomi delle proprietà
	//e valori ad esse associate in una mappa
	default Map<String, String> toMap(){
		Map<String, String> ris = new LinkedHashMap<String, String>();
		for(Method m : this.getClass().getMethods()) {
			if(
				(m.getName().startsWith("get") || m.getName().startsWith("is"))
				&& !m.getName().equalsIgnoreCase("getClass")
				&& m.getParameterCount() == 0)
				try {
					int partenza = m.getName().startsWith("get") ? 3 : 2;
					String nomeProp = m.getName().substring(partenza).toLowerCase();
					//m.invoke(this) 
					//m è il metodo ad esempio getTitolo()
					//this-> libro,oggetto che chiama il metodo toMap() 
					//libro.getTitolo()
					ris.put(nomeProp, m.invoke(this)+ "");
				}catch(Exception e) {
					e.printStackTrace();
				}	
		}
		return ris;
	}







}
