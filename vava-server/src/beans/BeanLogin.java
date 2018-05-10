package beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.Stateless;
import other.ConnectToDatabase;

@Stateless
public class BeanLogin extends ConnectToDatabase implements BeanLoginRemote {
	/**
	 * metoda, ktora overi spravnost udajov z databazy
	 */
	public Long loginQuery(String email_tf, String password_tf) throws SQLException {
		Long id = null;
		String password = null;
		PreparedStatement stmt = null;
		stmt = getConnection().prepareStatement("SELECT id,password FROM customer WHERE email = ?;");
		stmt.setString(1, email_tf);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			id = rs.getLong(1);
			password = rs.getString(2);
		}
		if (password != null && !password.equals(password_tf))
			id = null;
		return id;
	}

}
