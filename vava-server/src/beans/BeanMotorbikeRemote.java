package beans;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Remote;

import model.Motorbike;
@Remote
public interface BeanMotorbikeRemote {
	
	public List<Motorbike> listQuery(String company) throws SQLException;
	
}
