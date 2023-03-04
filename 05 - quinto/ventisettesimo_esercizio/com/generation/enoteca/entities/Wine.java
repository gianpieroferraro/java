package com.generation.enoteca.entities;

import java.util.ArrayList;

public class Wine extends Entity{
	
	private String name, description;
	//vitigno,gradazione,paese,regione

	private ArrayList<Bottle> bottiglie;


	
	public Wine() {}

	public Wine(String id, String name, String description) {
		super(id);
		setName(name);
		setDescription(description);
	}
	




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Bottle> getBottiglie() {
		return bottiglie;
	}

	public void setBottiglie(ArrayList<Bottle> bottiglie) {
		this.bottiglie = bottiglie;
	}

	@Override
	public String toString() {
		return "Nome: " + name + "\nDescrizione: " + description + "\nBottiglie: " + bottiglie + "\n";
	}






	





	
}
