package com.inventorycontrolsystem.IVCS.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;




@Entity
public class StockIn {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String code;                    
	private int qty;
	private double price;
	private String date;
	private double exchangeRate;
	private String remark;
	private double subTotalAmount;
	private String unit;
	
	
	@ManyToOne
	@JoinColumn(name = "stock_id")
	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;
	
	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;
	
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "unit_id")
	 */
	
	
	
	
	public double getSubTotalAmount() {
		return subTotalAmount;
	}

	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}

	

	
	public StockIn(int qty, double price, String date, double exchangeRate,Stock stock,
			Warehouse warehouse, Supplier supplier, Currency currency) {
		super();
		//this.code = code;
		this.qty = qty;
		this.price = price;
		this.date = date;
		this.exchangeRate = exchangeRate;
		//this.remark = remark;
		this.stock = stock;
		this.warehouse = warehouse;
		this.supplier = supplier;
		this.currency = currency;
		//this.unit = unit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public StockIn() {
		super();
	}
	
	
	
  	

}
