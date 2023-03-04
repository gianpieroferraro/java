package com.generation.enoteca.entities;

import com.generation.enoteca.utils.Validator;

public class Bottle extends Entity{

	private int quantity;
	private double price;
	private int year;
	private int wineId; 	//per collegarci al vino
	






	public Bottle() {}

	public Bottle(String id, String quantity, String price, String year, int wineId) {
		super(id);
		setQuantity(quantity);
		setPrice(price);
		setYear(year);
		setWineId(wineId);
	}







	public int getWineId() {
		return wineId;
	}

	public void setWineId(int wineId) {
		this.wineId = wineId;
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
				",\nquantity: " + quantity +
				",\nprice: "+ price + 
				",\nyear: " + year;
	}

}
