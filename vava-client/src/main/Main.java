package main;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import javax.naming.Context;
import javax.naming.InitialContext;

import beans.BeanCompanyRemote;
import beans.BeanConfigInitRemote;
import beans.BeanEmailRemote;
import beans.BeanLoginRemote;
import beans.BeanMotorbikeRemote;
import beans.BeanOrderItemRemote;
import beans.BeanRegistrationRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	public static ResourceBundle bundle = null;
	public static ResourceBundle config = null;
	private static BeanConfigInitRemote bi;
	private static BeanCompanyRemote bc;
	private static BeanLoginRemote bl;
	private static BeanEmailRemote be;
	private static BeanMotorbikeRemote bm;
	private static BeanOrderItemRemote bo;
	private static BeanRegistrationRemote br;
	
	public static BeanConfigInitRemote getBi() {
		return bi;
	}
	public static void setBi(BeanConfigInitRemote bi) {
		Main.bi = bi;
	}
	public static BeanCompanyRemote getBc() {
		return bc;
	}
	public static void setBc(BeanCompanyRemote bc) {
		Main.bc = bc;
	}
	public static BeanLoginRemote getBl() {
		return bl;
	}
	public static void setBl(BeanLoginRemote bl) {
		Main.bl = bl;
	}
	public static BeanEmailRemote getBe() {
		return be;
	}
	public static void setBe(BeanEmailRemote be) {
		Main.be = be;
	}
	public static BeanMotorbikeRemote getBm() {
		return bm;
	}
	public static void setBm(BeanMotorbikeRemote bm) {
		Main.bm = bm;
	}
	public static BeanOrderItemRemote getBo() {
		return bo;
	}
	public static void setBo(BeanOrderItemRemote bo) {
		Main.bo = bo;
	}
	public static BeanRegistrationRemote getBr() {
		return br;
	}
	public static void setBr(BeanRegistrationRemote br) {
		Main.br = br;
	}

	private static Context serverConnection() {
		Context context = null;
		try {
			Properties props = new Properties();
			String JBOSS_CONTEXT="org.jboss.naming.remote.client.InitialContextFactory";
			props.put(Context.INITIAL_CONTEXT_FACTORY, JBOSS_CONTEXT);
			props.put(Context.PROVIDER_URL, "remote://localhost:4447");
			props.put(Context.SECURITY_PRINCIPAL, "maruniak");
			props.put(Context.SECURITY_CREDENTIALS, "tojejedno");
			props.put("jboss.naming.client.ejb.context", true);
			props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			context = new InitialContext(props);		
		}catch (Exception e) {
			LOGGER.log(java.util.logging.Level.SEVERE, "Chyba pri pokuse o spojenie so serverom", e);
		}
		return context;
	}
	
	public static void main(String[] args) {
		FileHandler fh = null;
		try {
			fh = new FileHandler("main-log.log", false);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vytvarani logovacieho suboru", e);
		}
		fh.setFormatter(new XMLFormatter());
		fh.setLevel(Level.SEVERE);
		LOGGER.addHandler(fh);
		try {
			Context context = serverConnection();
			bi = (BeanConfigInitRemote)context.lookup("vava-server/BeanConfigInit!beans.BeanConfigInitRemote");
			bc = (BeanCompanyRemote)context.lookup("vava-server/BeanCompany!beans.BeanCompanyRemote");
			bl = (BeanLoginRemote)context.lookup("vava-server/BeanLogin!beans.BeanLoginRemote");
			be = (BeanEmailRemote)context.lookup("vava-server/BeanEmail!beans.BeanEmailRemote");
			bm = (BeanMotorbikeRemote)context.lookup("vava-server/BeanMotorbike!beans.BeanMotorbikeRemote");
			bo = (BeanOrderItemRemote)context.lookup("vava-server/BeanOrderItem!beans.BeanOrderItemRemote");
			br = (BeanRegistrationRemote)context.lookup("vava-server/BeanRegistration!beans.BeanRegistrationRemote");

			bi.initConfig();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Chyba inicializacie spojenia na server beany", e);
		}
		Application.launch(Main.class, (java.lang.String[]) null);
	}

	public void start(Stage primaryStage) {
		try {
			bundle = ResourceBundle.getBundle("multilanguage", Locale.getDefault());
			config = ResourceBundle.getBundle("config");
			Pane page = (Pane) FXMLLoader.load(Main.class.getResource("/view/Login_view.fxml"), bundle);
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle(config.getString("NameOfCompany"));
			primaryStage.getIcons().add(new Image(config.getString("icon_path")));
			primaryStage.show();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vytvarani hlavneho okna", e);
		}
	}

}
