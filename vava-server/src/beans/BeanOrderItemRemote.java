package beans;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Remote;

import model.OrderItem;

@Remote
public interface BeanOrderItemRemote {
	
	public List<OrderItem> listQuery(Long ID) throws SQLException;
	
	public String insertOrder(List<OrderItem> basket, Long ID) throws SQLException;
	
}
