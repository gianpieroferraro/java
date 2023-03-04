package com.generation.enoteca.entities;

import com.generation.enoteca.utils.Validator;

public class Bottle extends Entity{

	private Wine vino;
	private int quantity;
	private double price;
	private int year;
	//private int wine_id;






	public Bottle() {}

	public Bottle(String id, Wine vino, String quantity, String price, String year) {
		super(id);
		setVino(vino);
		setQuantity(quantity);
		setPrice(price);
		setYear(year);
	}






	public Wine getVino() {
		return vino;
	}

	public void setVino(Wine vino) {
		this.vino = vino;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		if(Validator.isNumberOk(quantity))
			this.quantity = Integer.parseInt(quantity);
		else
			this.quantity = -1;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(String price) {
		if(Validator.isNumberOk(price))
			this.price = Double.parseDouble(price);
		else
			this.price = 0;
	}

	public int getYear() {
		return year;
	}

	public void setYear(String year) {
		if(Validator.isNumberOk(year))
			this.year = Integer.parseInt(year);
		else
			this.year = 1600;

	}








	@Override
	public String toString() {
		return  super.toString() +
				",\nvino: " + vino.toString() + 
				",\nquantity: " + quantity +
				",\nprice: "+ price + 
				",\nyear: " + year;
	}

}
