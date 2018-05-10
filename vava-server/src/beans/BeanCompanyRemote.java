package beans;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface BeanCompanyRemote {

	public List<String> comboQuery() throws SQLException;

}
