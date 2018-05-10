package model;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;

public class Company {
	
	public Company() {
		
	}
	/**
	 * metoda, ktora naplni combobox pobociek pozicovne z databazy
	 */
    public ObservableList<String> comboQuery() throws SQLException {
    	ObservableList<String> list = FXCollections.observableArrayList(Main.getBc().comboQuery());
		return list;
    }
    
}
