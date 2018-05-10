package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import main.Main;

public class OrderItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 78470622581820762L;
	private Long id;
	private Date from;
	private Date to;
	private Long moto;
	private Long order;
	private Double price;
	private String brand;
	private String model;

	public OrderItem() {

	}
	
	public List<OrderItem> listQuery(Long ID) throws SQLException {
		List<OrderItem> products = new ArrayList<OrderItem>(Main.getBo().listQuery(ID));
		return products;
	}
	/**
	 * metoda, ktora vytvori v databaze novy zaznam objednavky a nove zaznamy poloziek, ktore patria k danej objednavke
	 */
	public void insertOrder(List<OrderItem> basket, Long ID, String email) throws SQLException, AddressException, MessagingException {
		String string = Main.getBo().insertOrder(basket, ID);
		Main.getBe().SentEmail(email, string);
	}

	public OrderItem(Date from, Date to, Motorbike motorbike, Double price) {
		super();
		this.from = from;
		this.to = to;
		this.brand = motorbike.getBrand();
		this.model = motorbike.getModel();
		this.moto = motorbike.getId();
		this.price = price;
	}

	public OrderItem(Date from, Date to, String brand, String model, Double price) {
		super();
		this.from = from;
		this.to = to;
		this.setBrand(brand);
		this.setModel(model);
		this.price = price;
	}

	public OrderItem(Long id, Date from, Date to, Long moto, Long order, Double price) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.moto = moto;
		this.order = order;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Long getMoto() {
		return moto;
	}

	public void setMoto(Long moto) {
		this.moto = moto;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
}
