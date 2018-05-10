package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import org.jboss.logmanager.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import model.Login;

public class LoginController implements Initializable{

	private static Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	private Long id;
	
    @FXML
    private TextField email_tf;

    @FXML
    private TextField password_tf;

    @FXML
    private Button reg_button;

    @FXML
    private Label password_lb;
    
    @FXML
    private Button login_button;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		FileHandler fh = null;
		try {
			fh = new FileHandler("login-log.log", false);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vytvarani logovacieho suboru", e);
		}
		fh.setFormatter(new XMLFormatter());
		fh.setLevel(Level.SEVERE);
		LOGGER.addHandler(fh);
		
		Main.getBi().initConnection();
	}
    
    @FXML
    void eng_lan(MouseEvent event) {
    	Main.bundle = ResourceBundle.getBundle("multilanguage", new Locale("EN", "en"));
    	changeLang();
    }

    @FXML
    void svk_lan(MouseEvent event) {
    	Main.bundle = ResourceBundle.getBundle("multilanguage", new Locale("SK", "sk"));
    	changeLang();
    }
    
    @FXML
    void reg_buttonClick(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Registration_view.fxml"), Main.bundle);
		Pane mainPane = loader.load();
		Scene mainScene = new Scene(mainPane);
		Stage primaryStage = new Stage();
		primaryStage.setScene(mainScene);
		primaryStage.setTitle(Main.config.getString("NameOfCompany"));
		primaryStage.getIcons().add(new Image(Main.config.getString("icon_path")));
		primaryStage.show();
    }

    @FXML
    void login_buttonClick(ActionEvent event) throws IOException {
    	if(!password_tf.getText().isEmpty() && password_tf.getText() != null) {
    	Login login = new Login();
    	try {
			id = login.loginQuery(email_tf.getText(), password_tf.getText());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vykonavani querry overujuca prihl. udaje", e);
		}
    	if(password_tf.getText() != null && id != null && id != 0) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Order_view.fxml"), Main.bundle);
			Pane mainPane = loader.load();
			OrderController con = loader.getController();
			Scene mainScene = new Scene(mainPane);
			Stage primaryStage = new Stage();
			primaryStage.setScene(mainScene);
			primaryStage.setTitle(Main.config.getString("NameOfCompany"));
			primaryStage.getIcons().add(new Image(Main.config.getString("icon_path")));
			con.setID(id, email_tf.getText());
			primaryStage.show();
		}
		else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Wrong_login.fxml"), Main.bundle);
			Pane mainPane = loader.load();
			Scene mainScene = new Scene(mainPane);
			Stage primaryStage = new Stage();
			primaryStage.setScene(mainScene);
			primaryStage.setTitle(Main.config.getString("NameOfCompany"));
			primaryStage.getIcons().add(new Image(Main.config.getString("icon_path")));
			primaryStage.show();
		}
		password_tf.setText("");
    	}
    	else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(Main.config.getString("NameOfCompany"));
			alert.setContentText(Main.bundle.getString("wrongLogin"));
			alert.setHeaderText(Main.bundle.getString("wrongLogin"));
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(Main.config.getString("icon_path")));
			alert.show();
    	}
    }
    public void changeLang(){
    	password_lb.setText(Main.bundle.getString("password"));
    	login_button.setText(Main.bundle.getString("Login"));
    	reg_button.setText(Main.bundle.getString("registration"));
    }

}
