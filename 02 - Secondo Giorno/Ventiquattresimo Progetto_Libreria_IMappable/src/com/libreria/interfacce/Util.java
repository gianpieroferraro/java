package com.libreria.interfacce;

import java.util.List;
import java.util.Map;

public interface Util {

	public static String stampaMappa(Map<String,String> map) {
		//prendo la lista delle chiavi
		String chiavi = map.keySet() + "";
		// [Red, Lando Norris, Morpheous, Neo, Meliodas, Marcelli]
		chiavi = chiavi.substring(1, chiavi.length() -1);
	//	System.out.println(chiavi);
		String[] vettoreChiavi = chiavi.split(",");
		String ris = "";
		
		for(int i = 0; i < vettoreChiavi.length;i++) {
			ris += vettoreChiavi[i] + " : " + map.get(vettoreChiavi[i].trim() + "\n");
		//	System.out.println(ris);
		}
		return ris; 
	}
	
	
	public static String stampaMappe(List<Map<String,String>> mappe) {
		String ris = "";
		for(Map<String,String> m : mappe)
			ris += stampaMappa(m) + "\n       \n";
		return ris;
	}
	
	
	
}
