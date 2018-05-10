package beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import model.Motorbike;
import other.ConnectToDatabase;

@Stateless
public class BeanMotorbike extends ConnectToDatabase implements BeanMotorbikeRemote {
	/**
	 * metoda, ktora naplni list produktov motorkami z vybranej predajne
	 */
	public List<Motorbike> listQuery(String company) throws SQLException {
		List<Motorbike> products = new ArrayList<>();
		PreparedStatement stmt = null;
		stmt = getConnection().prepareStatement(
				"SELECT * FROM motorbike " + "JOIN company c2 ON motorbike.company = c2.id " + "WHERE c2.name = ?;");
		String[] tmp = company.split("\t");
		stmt.setString(1, tmp[0]);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			products.add(new Motorbike(rs.getLong(5), rs.getString(1), rs.getString(2), rs.getString(6), rs.getInt(7),
					rs.getInt(3), rs.getDouble(4)));
		}
		return products;
	}

}
