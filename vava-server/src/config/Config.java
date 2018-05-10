package config;

import java.util.ResourceBundle;

public class Config {
	
	public static ResourceBundle config = null;
	/**
	 * metoda, ktora napoji konfiguracny subor na ResourceBoundle objekt
	 */
	public Config() {
		config = ResourceBundle.getBundle("config");
	}
	
}
