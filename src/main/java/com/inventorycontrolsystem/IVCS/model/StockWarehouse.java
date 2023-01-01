package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StockWarehouse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String code;
	
	private Integer qty;
	private double price;
	
	@ManyToOne
	@JoinColumn(name = "stock_id")
	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	public Integer getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public StockWarehouse() {
		super();
	}

	public StockWarehouse(Double price,Integer qty, String code, Stock stock, Warehouse warehouse) {
		super();
		this.price= price;
		this.qty = qty;
		this.code = code;
		this.stock = stock;
		this.warehouse = warehouse;
	}
	
	
	
}
