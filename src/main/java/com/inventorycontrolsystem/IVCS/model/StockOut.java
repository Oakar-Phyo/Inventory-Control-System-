package com.inventorycontrolsystem.IVCS.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StockOut {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	private String receiveCode;
	private Integer receiveQty;
	private Integer returnqty;
	private double price;
	private String date;
	private double exchangeRate;
	private String remark;
	private double subTotalAmount;
	@ManyToOne()
	@JoinColumn(name = "stock_id")
	private Stock stock;
	
	@ManyToOne()
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;
	
	@ManyToOne()
	@JoinColumn(name = "currency_id")
	private Currency currency;
	
	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@ManyToOne
	@JoinColumn(name = "stockIn_id")
	private StockIn stockIn;
	

	

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public StockIn getStockIn() {
		return stockIn;
	}

	public void setStockIn(StockIn stockIn) {
		this.stockIn = stockIn;
	}

	public StockOut() {
		super();
	}

	public StockOut(String code, String receiveCode, Integer returnqty, double price, String date, 
			 Stock stock,StockIn stockIn,Integer receiveQty) {
		super();
		this.code = code;
		this.receiveCode = receiveCode;
		this.returnqty = returnqty;
		this.price = price;
		this.date = date;
		this.stock = stock;
		this.stockIn = stockIn;
		this.receiveQty= receiveQty;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public String getReceiveCode() {
		return receiveCode;
	}

	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
	}


	public Integer getReturnqty() {
		return returnqty;
	}

	public Integer getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(Integer receiveQty) {
		this.receiveQty = receiveQty;
	}

	public void setReturnqty(Integer returnqty) {
		this.returnqty = returnqty;
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

//	public StockIn getExchangeRate() {
//		return exchangeRate;
//	}
//
//	public void setExchangeRate(StockIn exchangeRate) {
//		this.exchangeRate = exchangeRate;
//	}

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


	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getSubTotalAmount() {
		return subTotalAmount;
	}

	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}

	
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}



	




	
	
	
	
}
