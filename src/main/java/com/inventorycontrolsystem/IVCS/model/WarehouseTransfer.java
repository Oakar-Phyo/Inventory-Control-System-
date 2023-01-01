package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class WarehouseTransfer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    private String transferNo;
    private double price;
	@ManyToOne
	private Warehouse fromWarehouse;
	@ManyToOne
	private Stock stock;
	private Integer currentQty;
	private String transferDate;
	@ManyToOne
	private Warehouse toWarehouse;
	private Integer transferQty;
	private double total;
	@Column(columnDefinition = "TEXT")
	private String remark;
	  
	
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTransferNo() {
		return transferNo;
	}
	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}
	public Warehouse getFromWarehouse() {
		return fromWarehouse;
	}
	public void setFromWarehouse(Warehouse fromWarehouse) {
		this.fromWarehouse = fromWarehouse;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public Integer getCurrentQty() {
		return currentQty;
	}
	public void setCurrentQty(Integer currentQty) {
		this.currentQty = currentQty;
	}
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public Warehouse getToWarehouse() {
		return toWarehouse;
	}
	public void setToWarehouse(Warehouse toWarehouse) {
		this.toWarehouse = toWarehouse;
	}
	public Integer getTransferQty() {
		return transferQty;
	}
	public void setTransferQty(Integer transferQty) {
		this.transferQty = transferQty;
	}
	public WarehouseTransfer(String transferNo, Warehouse fromWarehouse, Stock stock, Integer currentQty,
			String transferDate, Warehouse toWarehouse, Integer transferQty, double total, Double price) {
		super();
		this.transferNo = transferNo;
		this.fromWarehouse = fromWarehouse;
		this.stock = stock;
		this.currentQty = currentQty;
		this.transferDate = transferDate;
		this.toWarehouse = toWarehouse;
		this.transferQty = transferQty;
		this.total = total;
		this.price= price;
	}
	public WarehouseTransfer() {
		super();
	}
	
    
}
