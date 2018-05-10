package model;

import java.sql.SQLException;

import main.Main;

public class Registration {
	
	public Registration(){
		
	}
	/**
	 * metoda, ktora overi, ci sa email uz nenachadza v databaze
	 */
    public Long emailValidQuery(String email) throws SQLException {
    	Long ID = null;
    	ID = Main.getBr().emailValidQuery(email);
		return ID;
    }
    /**
	 * metoda, ktora vytvori noveho zakaznika v databaze
	 */
    public void newCustommerQuery(String name,String email, String password, String dl) throws SQLException {
    	Main.getBr().newCustommerQuery(name, email, password, dl);
    }
    
}
