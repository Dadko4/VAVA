package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;
import org.jboss.logmanager.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.Main;
import model.Registration;

public class RegistrationController {
	
	private static Logger LOGGER = Logger.getLogger(Registration.class.getName());
	private Long id = null;
	private Registration reg = new Registration();
	
    @FXML
    private TextField name_tf;

    @FXML
    private TextField driverLicense_tf;

    @FXML
    private PasswordField password_tf;

    @FXML
    private Button reg_button;

    @FXML
    private TextField email_tf;

    @FXML
    void reg_buttonClick(ActionEvent event) {
		FileHandler fh = null;
		try {
			fh = new FileHandler("registration-log.log", false);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vytvarani logovacieho suboru", e);
		}
		fh.setFormatter(new XMLFormatter());
		fh.setLevel(Level.SEVERE);
		LOGGER.addHandler(fh);
		
    	try {
			id = reg.emailValidQuery(email_tf.getText());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vykonavani querry vracajucej id pouzivatela", e);
		}
		if(id != null && id != 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(Main.config.getString("NameOfCompany"));
			alert.setContentText(Main.bundle.getString("registered"));
			alert.setHeaderText(Main.bundle.getString("bad_reg"));
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(Main.config.getString("icon_path")));
			alert.showAndWait();
		}
		else {
			try {
				reg.newCustommerQuery(name_tf.getText(), email_tf.getText(), password_tf.getText(), driverLicense_tf.getText());
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Chyba pri vykonavani querry registrujucej noveho pouzivatela", e);
				e.printStackTrace();
			}
			name_tf.getScene().getWindow().hide();
		}
    }
}
