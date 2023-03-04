package com.generation.enoteca.dao;

import java.util.ArrayList;
import java.util.List;

import com.generation.enoteca.entities.Bottle;
import com.generation.enoteca.entities.Entity;
import com.generation.enoteca.entities.Wine;
import com.generation.enoteca.interfacce.IDaoBottle;

public class DaoBottle extends Dao implements IDaoBottle{
	
	private static IDaoBottle instance;
	
	private DaoBottle() {
		super();
		setTabella("bottle");
	}
	
	public static IDaoBottle getInstance() {
		if(instance == null)
			instance = new DaoBottle();
		return instance;
	}

	@Override
	public List<Bottle> getAllBottles() {
		setRead("select * from bottle");
		return getBottles();
	}

	private List<Bottle> getBottles(String...params){
		List<Bottle> ris = new ArrayList<Bottle>();
		for(Entity e : super.getAll(params)) {
			if(e instanceof Bottle) {
				ris.add((Bottle)e);
			}
		}
		return ris;
	}
	
	@Override
	public List<Bottle> getAllBottles(String query, String... params) {
		setRead(query); //"select * from bottle where price > ? and quantity =
		List<Bottle> ris = getBottles(params);

		return null;
	}

	@Override
	public List<Bottle> getBottlesByWine(Wine wine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Bottle bottle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Bottle bottle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String colonna, String valore, int id) {
		// TODO Auto-generated method stub
		
	}




	


}
