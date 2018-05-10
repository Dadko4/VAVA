package model;

import java.io.Serializable;

public class Motorbike implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String brand;
	private String model;
	private String image;
	private Integer power;
	private Integer produce_year;
	private Double price;
	private String image_str;
		
	public Motorbike(Long id, String brand, String model, String url, Integer power, Integer produce_year, Double price) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.power = power;
		this.produce_year = produce_year;
		this.price = price;
		this.image_str = url;
	}
	
    public Motorbike() {
    	
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
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
