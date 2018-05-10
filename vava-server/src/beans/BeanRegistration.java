package beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.Stateless;

import other.ConnectToDatabase;

@Stateless
public class BeanRegistration extends ConnectToDatabase implements BeanRegistrationRemote {
	/**
	 * metoda, ktora overi, ci sa dany email nenachadza v databaze
	 */
	public Long emailValidQuery(String email) throws SQLException {
		Long ID = null;
		PreparedStatement stmt = null;
		stmt = getConnection().prepareStatement("SELECT * FROM customer WHERE email = ?;");
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			ID = rs.getLong(2);
		}
		return ID;
	}

	/**
	 * metoda, ktora vytvori noveho zakaznika v databaze
	 */
	public void newCustommerQuery(String name, String email, String password, String dl) throws SQLException {
		PreparedStatement stmt = null;
		stmt = getConnection().prepareStatement(
				"INSERT INTO customer (name, email, password, driver_license_number) VALUES (?,?,?,?);");
		stmt.setString(1, name);
		stmt.setString(2, email);
		stmt.setString(3, password);
		stmt.setString(4, dl);
		stmt.execute();
	}

}
