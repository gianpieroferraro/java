package com.libreria.entities;

import java.util.List;

public class CasaEditrice extends Entity {
	
	private String nome, sede, generePiuTrattato;
	//altra possibile opzione:
	//aggiungo una lista di libri nella classe modello CasaEditrice
	//una casa editrice avr� una sua lista di libri da lei prodotti
	private List<Libro> libri;
	
	public CasaEditrice() {}

	public CasaEditrice(int id, String nome, String sede, String generePiuTrattato) {
		super(id);
		this.nome = nome;
		this.sede = sede;
		this.generePiuTrattato = generePiuTrattato;
	}
	
	public CasaEditrice(int id, String nome, String sede, String generePiuTrattato, List<Libro> libri) {
		super(id);
		this.nome = nome;
		this.sede = sede;
		this.generePiuTrattato = generePiuTrattato;
		this.libri = libri;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getGenerePiuTrattato() {
		return generePiuTrattato;
	}

	public void setGenerePiuTrattato(String generePiuTrattato) {
		this.generePiuTrattato = generePiuTrattato;
	}

	
	
	
	public List<Libro> getLibri() {
		return libri;
	}

	public void setLibri(List<Libro> libri) {
		this.libri = libri;
	}

	@Override
	public String toString() {
		return  "Casa editrice: \n" + super.toString() +
				",\nnome: " + nome + 
				",\nsede: " + sede + 
				",\ngenere più trattato:" + generePiuTrattato +
				",\nlibri: \n" + stampaLibri(getLibri());
	}
	
	
	private String stampaLibri(List<Libro> libri) {
		String ris = "";
		for(Libro l : libri)
			ris += l.toString() + "\n------\n";
		ris = (ris.isEmpty())? "La casa editrice " + getNome() + " non ha ancora prodotto nessun libro" 
				             : ris;
		return ris;
	}
	

}
