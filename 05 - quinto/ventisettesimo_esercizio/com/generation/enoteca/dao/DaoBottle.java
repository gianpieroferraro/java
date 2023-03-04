package com.generation.enoteca.dao;

import java.util.ArrayList;
import java.util.List;

import com.generation.enoteca.config.Configuration;
import com.generation.enoteca.entities.Bottle;
import com.generation.enoteca.entities.Entity;
import com.generation.enoteca.entities.Wine;
import com.generation.enoteca.interfacce.IDaoBottle;
import com.generation.enoteca.interfacce.IDatabase;

public class DaoBottle implements IDaoBottle{
	
	private static IDaoBottle instance;
	private IDatabase db;


	private static String insert = "insert into bottle (id,wine_id, year, quantity) values(?,?,?,?)";
    private static String read = "select * from bottle";
    private static String update = "update bottle set [colonna]= ? where id =?";
    private static String updateObj = "update bottle set wine_id= ? year=?  quantity = ? where id =?";
    private static String delete = "delete from wine where id =?";



	
	private DaoBottle() {
		db=(IDatabase)Configuration.get("database");
	}
	
	public static IDaoBottle getInstance() {
		if(instance == null)
			instance = new DaoBottle();
		return instance;
	}

	@Override
	public List<Bottle> getAllBottles() {
		List<Wine> ris = new ArrayList<>();
        open();

        Entity e;
        List<Map<String, String>> mappe = db.executeQuery(read);

        for(Map<String,String>m: mappe) {
            m.put("tabella", "bottle");
            e = Factory.make(m);
            e.fromMap(m);


            ris.add((Bottle)e);   //bisogna castare perché la lista sopra è di tipo Wine
            
        }


        close();
        return ris;
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

	public void delete(int id) {
        db.executeUpdate(delete, id+"");
    }


	


}
