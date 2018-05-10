package model;

import java.sql.SQLException;

import main.Main;

public class Login {
	
    public Login() {
		super();
	}
    /**
	 * metoda, ktora overi spravnost udajov pri prihlaseni
	 */
	public Long loginQuery(String email_tf, String password_tf) throws SQLException {
		Long id = null;
		id = Main.getBl().loginQuery(email_tf, password_tf);
		return id;
    }
	
}
