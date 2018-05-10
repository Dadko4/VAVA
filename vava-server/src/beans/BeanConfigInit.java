package beans;

import javax.ejb.Stateless;
import config.Config;
import other.ConnectToDatabase;

@Stateless
public class BeanConfigInit extends ConnectToDatabase implements BeanConfigInitRemote {
	/**
	 * metoda, ktora priradi konfiguracny subor do ResoursBoundle objektu
	 */
	public void initConfig() {
		new Config();
	}

	/**
	 * metoda, ktora vytvori pripojenie na databazu
	 */
	public void initConnection() {
		getConnection();
	}

}
