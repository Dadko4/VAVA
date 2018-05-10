package model;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import main.Main;

public class Motorbike {
	private Long id;
	private String brand;
	private String model;
	private Image image;
	private Integer power;
	private Integer produce_year;
	private Double price;
	private String image_str;
		
    public Motorbike() {
    	
	}
	
	public Motorbike(Long id, String brand, String model, String url, Integer power, Integer produce_year,
			Double price) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.image = new Image(url);
		this.power = power;
		this.produce_year = produce_year;
		this.price = price;
	}
	/**
	 * metoda, ktora naplni list motoriek nachadzajucich sa vo vybranej predajni
	 */
	public ObservableList<Motorbike> listQuery(String company) throws SQLException {
		List<Motorbike> list = Main.getBm().listQuery(company);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setImage(new Image(list.get(i).getImage_str()));
		}
    	ObservableList<Motorbike> products = FXCollections.observableArrayList(list);
		return products;
    }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public Integer getProduce_year() {
		return produce_year;
	}
	public void setProduce_year(Integer produce_year) {
		this.produce_year = produce_year;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImage_str() {
		return image_str;
	}
	public void setImage_str(String image_str) {
		this.image_str = image_str;
	}

}
