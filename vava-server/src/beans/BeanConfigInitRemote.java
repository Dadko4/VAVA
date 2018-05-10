package beans;

import javax.ejb.Remote;

@Remote
public interface BeanConfigInitRemote {

	public void initConfig();

	public void initConnection();
}
