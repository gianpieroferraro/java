package com.libreria.entities;

import java.time.LocalDate;

import com.libreria.interfacce.Validator;

public class Libro extends Entity{
	
	private String autore, titolo, genere;
	private int numero_pagine;
	private LocalDate data_pubblicazione;
	private boolean disponibile;
	private double prezzo;
	private int id_casaeditrice;//commentare riga 14
	//possibile opzione per relazionare le due tabelle
	//un libro ha anche come proprietà un oggetto di tipo CasaEditrice, cioè 
	//la casa editrice che lo produce
//	private CasaEditrice ce;
	
	public Libro() {}
	
	public Libro(int id, String autore, String titolo, String genere, int numero_pagine, LocalDate dataDiPubblicazione,
			boolean disponibile, double prezzo, int id_casaEditriceid) {
		super(id);
		this.autore = autore;
		this.titolo = titolo;
		this.genere = genere;
		this.numero_pagine = numero_pagine;
		this.data_pubblicazione = dataDiPubblicazione;
		this.disponibile = disponibile;
		this.prezzo = prezzo;
		this.id_casaeditrice = id_casaEditriceid;
	}

	public Libro(int id, String autore, String titolo, String genere, int numero_pagine, LocalDate dataDiPubblicazione,
			boolean disponibile, double prezzo) {
		super(id);
		this.autore = autore;
		this.titolo = titolo;
		this.genere = genere;
		this.numero_pagine = numero_pagine;
		this.data_pubblicazione = dataDiPubblicazione;
		this.disponibile = disponibile;
		this.prezzo = prezzo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		if(Validator.isStringa(autore))
			this.autore = autore;
		else
			this.autore = null;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public int getNumero_pagine() {
		return numero_pagine;
	}

	public void setNumero_pagine(int numero_pagine) {
		this.numero_pagine = numero_pagine;
	}


	public LocalDate getData_pubblicazione() {
		return data_pubblicazione;
	}

	public void setData_pubblicazione(LocalDate data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public boolean isDisponibile() {
		return disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(String prezzo_check) {
		if(Validator.isNumberOk(prezzo_check))
			this.prezzo = Double.parseDouble(prezzo_check);
		else
			this.prezzo = -1;
	}

	public int getId_casaeditrice() {
		return id_casaeditrice;
	}

	public void setId_casaeditrice(int id_casaeditrice) {
		this.id_casaeditrice = id_casaeditrice;
	}

	@Override
	public String toString() {
		return "Libro:\n" 
				+ super.toString() + "autore=" + autore + ",\n"
				+ "titolo=" + titolo + 
				",\ngenere=" + genere
				+ ",\nnumero di pagine= " + numero_pagine + 
				",\ndataDiPubblicazione=" + data_pubblicazione +
				",\ndisponibile=" + disponibile +
				",\nprezzo=" + prezzo + 
				",\ncasa editrice: " + id_casaeditrice;
	} 
	
	
	
	
	

}
