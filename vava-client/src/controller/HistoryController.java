package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import org.jboss.logmanager.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderItem;

public class HistoryController implements Initializable {
	
	private static Logger LOGGER = Logger.getLogger(HistoryController.class.getName());
	private Long id;
	
    @FXML
    private TableView<OrderItem> table;

    @FXML
    private TableColumn<OrderItem, String> brand;

    @FXML
    private TableColumn<OrderItem, String> model;

    @FXML
    private TableColumn<OrderItem, String> from;

    @FXML
    private TableColumn<OrderItem, Date> to;

    @FXML
    private TableColumn<OrderItem, Double> price;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FileHandler fh = null;
		try {
			fh = new FileHandler("history-log.log", false);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vytvarani logovacieho suboru", e);
		}
		fh.setFormatter(new XMLFormatter());
		fh.setLevel(Level.SEVERE);
		LOGGER.addHandler(fh);
		
		brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
    	model.setCellValueFactory(new PropertyValueFactory<>("model"));
    	from.setCellValueFactory(new PropertyValueFactory<>("from"));
    	to.setCellValueFactory(new PropertyValueFactory<>("to"));
    	price.setCellValueFactory(new PropertyValueFactory<>("price"));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		OrderItem item = new OrderItem();
		this.id = id;
		try {
			ObservableList<OrderItem> items = FXCollections.observableArrayList(item.listQuery(id));
			table.setItems(items);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Chyba pri vykonavani querry vracajuca historiu objednavok", e);
		}
	}    
}
