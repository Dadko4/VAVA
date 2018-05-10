package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.jboss.logmanager.Level;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import model.Company;
import model.OrderItem;
import model.Motorbike;

public class OrderController implements Initializable {
	
	private static Logger LOGGER = Logger.getLogger(OrderController.class.getName());
	private List<OrderItem> basket = new ArrayList<>();
	private FilteredList<Motorbike> filteredList;
	private Long ID;
	private String email;

	@FXML
	private ListView<Motorbike> products_list;

	@FXML
	private ImageView picture;

	@FXML
	private Button hystoryButton;

	@FXML
	private ComboBox<String> companies;

	@FXML
	private DatePicker from;

	@FXML
	private DatePicker to;

	@FXML
	private Button Add_item_button;

	@FXML
	private Label power_label;

	@FXML
	private TextField search;

	@FXML
	private ListView<OrderItem> basket_list;

	@FXML
	private Label sum;

	@FXML
	private Label sum_price;

	@FXML
	private Button make_order_button;

	@FXML
	private Color x4;

	@FXML
	private Button show;

	@FXML
	private Font x3;

	@FXML
	void hystoryClick(ActionEvent event) {
		if (ID != null && ID != 0) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/History_view.fxml"), Main.bundle);
			Pane mainPane = null;
			try {
				mainPane = loader.load();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Chyba pri vytvarani okna historie objednavok", e);
			}
			Scene mainScene = new Scene(mainPane);
			HistoryController con = loader.getController();
			Stage primaryStage = new Stage();
			primaryStage.setScene(mainScene);
			primaryStage.setTitle(Main.config.getString("NameOfCompany"));
			primaryStage.getIcons().add(new Image(Main.config.getString("icon_path")));
			con.setId(ID);
			primaryStage.show();
		}
	}

	@FXML
	void add_click(ActionEvent event) {
		if (!products_list.getSelectionModel().isEmpty()) {
			Motorbike moto = products_list.getSelectionModel().getSelectedItem();
			Double price = moto.getPrice() * java.time.temporal.ChronoUnit.DAYS.between(from.getValue(), to.getValue());
			OrderItem item = new OrderItem(Date.valueOf(from.getValue()), Date.valueOf(to.getValue()), moto, price);
			if (!basket.contains(item)) {
				basket.add(item);
				basket_list.setItems(FXCollections.observableArrayList(basket));
				Double suma = Double.parseDouble(sum_price.getText()) + price;
				sum_price.setText(suma.toString());
			}
		}
	}

	@FXML
	void make_click(ActionEvent event) {
		if (!basket.isEmpty()) {
			OrderItem item = new OrderItem();
			try {
				item.insertOrder(basket, ID, email);
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Chyba pri vykonavani querry zaznamenavajucej objednavku", e);
			} catch (AddressException e) {
				LOGGER.log(Level.SEVERE, "Chyba pri odosielani emailu", e);
			} catch (MessagingException e) {
				LOGGER.log(Level.SEVERE, "Chyba pri odosielani emailu", e);

			}
			basket.clear();
			basket_list.setItems(FXCollections.observableArrayList(basket));
			from.setValue(null);
			to.setValue(null);

		}
	}

	@FXML
	void select_item(MouseEvent event) {
		if (!products_list.getSelectionModel().isEmpty()) {
			picture.setImage((products_list.getSelectionModel().getSelectedItem().getImage()));
			power_label.setText("Power: " + products_list.getSelectionModel().getSelectedItem().getPower()
					+ "\t\tPrice: " + products_list.getSelectionModel().getSelectedItem().getPrice());
		}
	}

	@FXML
	void comboClick(ActionEvent event) {
		try {
			Motorbike motorbike = new Motorbike();
			filteredList = new FilteredList<>(motorbike.listQuery(companies.getValue()), s -> true);
			products_list.setItems(filteredList);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vykonavani querry vracajucej motorky zo zvolenej spolocnosti", e);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FileHandler fh = null;
		try {
			fh = new FileHandler("order-log.log", false);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vytvarani logovacieho suboru", e);
		}
		fh.setFormatter(new XMLFormatter());
		fh.setLevel(Level.SEVERE);
		LOGGER.addHandler(fh);
		
		File file = new File("xsr.png");
		Image image = new Image("file:///" + file.getAbsolutePath());
		picture.setImage(image);

		products_list.setCellFactory(param -> new ListCell<Motorbike>() {
			@Override
			protected void updateItem(Motorbike item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getBrand() == null) {
					setText(null);
				} else {
					setText(item.getBrand() + "\t" + item.getModel());
					setFont(Font.font(15));
				}
			}
		});

		basket_list.setCellFactory(param -> new ListCell<OrderItem>() {
			@Override
			protected void updateItem(OrderItem item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getBrand() == null) {
					setText(null);
				} else {
					setText(item.getBrand() + " " + item.getModel() + "\t\t" + item.getPrice());
					setFont(Font.font(14));
				}
			}
		});
		
		search.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(element -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String tmp = element.getBrand().toLowerCase() + " " + element.getModel().toLowerCase();
				if (tmp.contains(newValue.toLowerCase())) {
					return true;
				}
				return false;
			});
			products_list.setItems(filteredList);
		});
		try {
			Company company = new Company();
			companies.setItems(company.comboQuery());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vykonavani querry vracajucej spolocnosti", e);
		}
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD, String email) {
		ID = iD;
		this.email = email;
	}

}
