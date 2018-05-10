package beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;

import model.OrderItem;
import other.ConnectToDatabase;

@Stateless
public class BeanOrderItem extends ConnectToDatabase implements BeanOrderItemRemote {

	public BeanOrderItem() {

	}

	/**
	 * metoda, ktora vrati historiu objednavok daneho zakaznika
	 */
	public List<OrderItem> listQuery(Long ID) throws SQLException {
		List<OrderItem> products = new ArrayList<>();
		PreparedStatement stmt = null;
		stmt = getConnection().prepareStatement("SELECT m.brand, m.model,item.\"from\",item.\"to\", item.price\r\n"
				+ "	FROM item\r\n" + "		JOIN motorbike m ON item.motorbike = m.id\r\n"
				+ "		JOIN \"order\" o ON item.\"order\" = o.id\r\n" + "WHERE o.customer = ?;");
		stmt.setLong(1, ID);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			products.add(
					new OrderItem(rs.getDate(3), rs.getDate(4), rs.getString(1), rs.getString(2), rs.getDouble(5)));
		}
		return products;
	}

	public String insertOrder(List<OrderItem> basket, Long ID) throws SQLException {
		PreparedStatement stmt = null;
		Double price = (double) 0;
		String string = "\n";
		stmt = getConnection().prepareStatement("select max(id) from \"order\";");
		ResultSet rs = stmt.executeQuery();
		Long IDOrder = null;
		while (rs.next()) {
			IDOrder = rs.getLong(1) + 1;
		}
		stmt = getConnection().prepareStatement("INSERT INTO \"order\" (id,date, customer) VALUES (?,?,?);");
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		stmt.setLong(1, IDOrder);
		stmt.setDate(2, new java.sql.Date(today.getTimeInMillis()));
		stmt.setLong(3, ID);
		stmt.executeUpdate();
		for (int i = 0; i < basket.size(); i++) {
			stmt = getConnection().prepareStatement(
					"INSERT  INTO item (\"from\", \"to\", motorbike, \"order\", price) VALUES (?,?,?,?,?);");
			stmt.setDate(1, basket.get(i).getFrom());
			stmt.setDate(2, basket.get(i).getTo());
			stmt.setLong(3, basket.get(i).getMoto());
			stmt.setLong(4, IDOrder);
			stmt.setDouble(5, basket.get(i).getPrice());
			stmt.executeUpdate();
			string += "\n" + basket.get(i).getFrom().toString() + "\t" + basket.get(i).getTo().toString() + "\t"
					+ basket.get(i).getBrand() + " " + basket.get(i).getModel() + "\t" + basket.get(i).getPrice()
					+ "\n";
			price += basket.get(i).getPrice();
		}

		string += ("\nVariable symbol: " + ID + "\nSum: " + price + "\n");
		return string;
	}

}
