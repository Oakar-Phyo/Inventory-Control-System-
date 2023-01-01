package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class IssueReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	private String issueCode;
	
	private Integer returnqty;
	private Integer issueQty;
	private double price;
	private String date;
	//private double exchangeRate;
	private String remark;
	private double subTotalAmount;
	
	@ManyToOne()
	@JoinColumn(name = "stock_id")
	private Stock stock;
	
	@ManyToOne()
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;
	
	//@ManyToOne()
	//@JoinColumn(name = "currency_id")
	//private Currency currency;
	
	@ManyToOne()
	@JoinColumn(name = "department_id")
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "issue_id")
	private Issue issue;

	

	public Integer getIssueQty() {
		return issueQty;
	}

	public void setIssueQty(Integer issueQty) {
		this.issueQty = issueQty;
	}

	/*
	 * public Currency getCurrency() { return currency; }
	 * 
	 * public void setCurrency(Currency currency) { this.currency = currency; }
	 */
	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	
	public IssueReturn() {
		super();
	}

	public IssueReturn(String code, String issueCode, Integer returnqty, double price, String date,
					Stock stock, Issue issue, Integer issueQty) {
		super();
		this.code = code;
		this.issueCode = issueCode;
		this.returnqty = returnqty;
		this.price = price;
		this.date = date;
		//Exchange rate Necessary??
		//this.exchangeRate = exchangeRate;
		//this.remark = remark;
		//this.subTotalAmount = subTotalAmount;
		this.stock = stock;
		//this.warehouse = warehouse;
		//this.currency = currency;
		//this.department = department;
		this.issue = issue;
		this.issueQty= issueQty;
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

	public String getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public Integer getReturnqty() {
		return returnqty;
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

	/*
	 * public double getExchangeRate() { return exchangeRate; }
	 * 
	 * public void setExchangeRate(double exchangeRate) { this.exchangeRate =
	 * exchangeRate; }
	 */

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
	
	
}
