package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String issueCode;  
	private Integer currentQty;
	private int issueQty;
	private double price;
	private String date;
	private String remark;
	private double subTotalAmount;
	
	
	
	@ManyToOne
	@JoinColumn(name = "stock_id")
	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;
	
	@ManyToOne()
	@JoinColumn(name = "department_id")
	private Department department;

	
	public Issue(Integer issueQty, Double price, String date, Stock stock, Integer currentQty, Department department) {
		this.issueQty = issueQty;
		this.price = price;
		this.date = date;
		this.stock = stock;
		this.currentQty= currentQty;
		this.department= department;

		
	
	}
	
	public Integer getCurrentQty() {
		return currentQty;
	}


	public void setCurrentQty(Integer currentQty) {
		this.currentQty = currentQty;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public int getIssueQty() {
		return issueQty;
	}

	public void setIssueQty(int issueQty) {
		this.issueQty = issueQty;
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


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getSubTotalAmount() {
		return subTotalAmount;
	}

	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Issue() {
		super();
	}
	
	
	
}
