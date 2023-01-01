package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Adjustment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String adjustmentCode;
	private Integer currentQty;
	private Integer adjustmentQty;
	private String date;
	private Integer totalAdjustment;
	private double subTotalAmount;
	private String remark;
	
	@Transient
	private String qty;
	
	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	@ManyToOne
	@JoinColumn(name="stock_warehouse_id")
	private StockWarehouse stockWarehouse;
	@ManyToOne
	@JoinColumn(name = "stock_id")
	private Stock stock;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdjustmentCode() {
		return adjustmentCode;
	}

	public StockWarehouse getStockWarehouse() {
		return stockWarehouse;
	}

	public void setStockWarehouse(StockWarehouse stockWarehouse) {
		this.stockWarehouse = stockWarehouse;
	}

	public void setAdjustmentCode(String adjustmentCode) {
		this.adjustmentCode = adjustmentCode;
	}

	public Integer getAdjustmentQty() {
		return adjustmentQty;
	}

	public void setAdjustmentQty(Integer adjustmentQty) {
		this.adjustmentQty = adjustmentQty;
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
	
	public Integer getCurrentQty() {
		return currentQty;
	}

	
	public Integer getTotalAdjustment() {
		return totalAdjustment;
	}

	public void setTotalAdjustment(Integer totalAdjustment) {
		this.totalAdjustment = totalAdjustment;
	}

	public void setCurrentQty(Integer currentQty) {
		this.currentQty = currentQty;
	}

	
	public Adjustment() {
		super();
	}

	public Adjustment(String adjustmentCode, Integer currentQty, Integer adjustmentQty, String date,
			Integer totalAdjustment, double subTotalAmount, StockWarehouse stockWarehouse, Stock stock,
			Warehouse warehouse) {
		super();
		this.adjustmentCode = adjustmentCode;
		this.currentQty = currentQty;
		this.adjustmentQty = adjustmentQty;
		this.date = date;
		this.totalAdjustment = totalAdjustment;
		this.subTotalAmount = subTotalAmount;
		//this.remark = remark;
		this.stockWarehouse = stockWarehouse;
		this.stock = stock;
		this.warehouse = warehouse;
	}



}
