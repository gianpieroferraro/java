package com.libreria.entities;

import com.libreria.interfacce.IMapp;

public abstract class Entity implements IMapp{
	
	private int id;
	
	public Entity() {}

	public Entity(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "id= " + id + "\n";
	}

}
