package com.libreria.interfacce;

public interface Validator {
	
	//metodo che controlla le stringhe
	public static boolean isStringa(String parametro) {
		boolean ris = false;
		if(!parametro.isEmpty() && parametro.length()> 2)
			ris = true;
		return ris;
	}
	
	static double isNumber(String numero){
		double parsato = 0;
		try {
			parsato = Double.parseDouble(numero);
		}catch(NumberFormatException e) {
			e.getMessage();
			return parsato;
		}
		return parsato;
	}
	
	static boolean isNumberOk(String numero){
		try {
			Double.parseDouble(numero);
			return true;
		}catch(NumberFormatException e) {
			e.getMessage();
			return false;
		}
	}
}
