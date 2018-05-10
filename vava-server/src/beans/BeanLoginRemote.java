package beans;

import java.sql.SQLException;

import javax.ejb.Remote;

@Remote
public interface BeanLoginRemote {
	
	public Long loginQuery(String email_tf, String password_tf) throws SQLException;
	
}
