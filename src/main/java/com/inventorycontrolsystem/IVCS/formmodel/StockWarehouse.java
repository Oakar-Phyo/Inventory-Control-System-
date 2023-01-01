package com.inventorycontrolsystem.IVCS.formmodel;

import com.inventorycontrolsystem.IVCS.model.Currency;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Supplier;
import com.inventorycontrolsystem.IVCS.model.Warehouse;

public class StockWarehouse {
	
	private int id;
	private String code;
	private int qty;
	private double price;
	private String date;
	private double exchangeRate;
	private double subTotalAmount;
	private Warehouse warehouse;
	private Stock stock;
	private Supplier supplier;
	private Currency currency;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public double getSubTotalAmount() {
		return subTotalAmount;
	}
	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
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
	


}
