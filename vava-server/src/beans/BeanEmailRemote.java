package beans;

import javax.ejb.Remote;

@Remote
public interface BeanEmailRemote {
	
	public void SentEmail(String email, String text);
	
}
