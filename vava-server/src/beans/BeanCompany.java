package beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import other.ConnectToDatabase;

@Stateless
public class BeanCompany extends ConnectToDatabase implements BeanCompanyRemote {
	/**
	 * metoda, ktora naplni combobox pobociek pozicovne z databazy
	 */
	public List<String> comboQuery() throws SQLException {
		List<String> list = new ArrayList<>();
		PreparedStatement stmt = null;
		stmt = getConnection().prepareStatement("Select * from company;");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			list.add(rs.getString(1) + "\t" + rs.getString(2));
		}
		return list;
	}

}
