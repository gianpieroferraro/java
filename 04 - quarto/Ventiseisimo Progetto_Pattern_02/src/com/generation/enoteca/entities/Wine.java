package com.generation.enoteca.entities;


public class Wine extends Entity{
	
	private String name, description;
	//vitigno,gradazione,paese,regione
	
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

	@Override
	public String toString() {
		return super.toString() + 
				",\nname: " + name + 
				",\ndescription: " + description;
	}

}
