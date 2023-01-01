package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Damage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String damageCode;
	private Integer currentQty;
	private Integer damageQty;
	private Integer totaldamageQty;
	private String date;
	private double subTotalAmount;
	private double price;
	private String remark;
	
	@ManyToOne
	@JoinColumn(name = "stock_warehouse_id")
	private StockWarehouse stockWarehouse;
	@ManyToOne
	@JoinColumn(name = "stock_id")
	private Stock stock;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

//	@ManyToOne
//	@JoinColumn(name = "stock_in_id")
//	private StockIn stockIn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getDamageCode() {
		return damageCode;
	}

	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}

	

	public Integer getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(Integer currentQty) {
		this.currentQty = currentQty;
	}

	public Integer getDamageQty() {
		return damageQty;
	}

	public void setDamageQty(Integer damageQty) {
		this.damageQty = damageQty;
	}

	
	
	public Integer getTotaldamageQty() {
		return totaldamageQty;
	}

	public void setTotaldamageQty(Integer totaldamageQty) {
		this.totaldamageQty = totaldamageQty;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getSubTotalAmount() {
		return subTotalAmount;
	}

	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	public StockWarehouse getStockWarehouse() {
		return stockWarehouse;
	}

	public void setStockWarehouse(StockWarehouse stockWarehouse) {
		this.stockWarehouse = stockWarehouse;
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


	public Damage( String damageCode,Integer damageQty, String date,
		 StockWarehouse stockWarehouse, Stock stock,Double subTotalAmount) {
		super();
		this.damageCode=damageCode;
		this.damageQty=damageQty;
		this.date = date;
		this.stockWarehouse = stockWarehouse;
		this.stock = stock;
		this.subTotalAmount = subTotalAmount;
		
	}

	public Damage() {
		super();
	}
	

}
