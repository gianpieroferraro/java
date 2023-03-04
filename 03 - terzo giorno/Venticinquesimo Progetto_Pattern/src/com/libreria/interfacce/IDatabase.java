package com.libreria.interfacce;

import java.util.List;
import java.util.Map;

public interface IDatabase {
	
	List<Map<String,String>> rows(String query, String...args);
	Map<String,String> row(String query,String...params);
	boolean update(String query,String...params);

}
