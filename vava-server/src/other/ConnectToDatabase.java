package other;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
/**
 * metoda, ktora vytvori pripojenie na databazu a vytvori logger
 */
public class ConnectToDatabase {
	
	private static Logger LOGGER = Logger.getLogger(ConnectToDatabase.class.getName());
	protected static Connection connection = null;
	/**
	 * metoda, ktora vytvori pripojenie na databazu a vytvori logger
	 */
	private Connection createConnection() {
		FileHandler fh = null;
		try {
			fh = new FileHandler("connection-log.log", false);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vytvarani logovacieho suboru", e);
		}
		fh.setFormatter(new XMLFormatter());
		fh.setLevel(Level.SEVERE);
		LOGGER.addHandler(fh);
		
		Connection connect = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:/datasources/databaza");
			connect = ds.getConnection();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vytvarani spojenia s databazaou", e);
		}
		return connect;
	}
	
	public Connection getConnection() {
		if (connection == null) {
			connection = createConnection();
		}
		return connection;
	}

}
