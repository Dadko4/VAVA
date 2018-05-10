package beans;

import java.sql.SQLException;

import javax.ejb.Remote;

@Remote
public interface BeanRegistrationRemote {
	
	 public Long emailValidQuery(String email) throws SQLException;
	 
	 public void newCustommerQuery(String name,String email, String password, String dl) throws SQLException;
	 
}
