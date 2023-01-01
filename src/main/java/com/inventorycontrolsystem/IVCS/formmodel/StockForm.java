package com.inventorycontrolsystem.IVCS.formmodel;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class StockForm {

	private Integer id;

	@NotEmpty(message = "Stock Name must not be empty!")
	private String name;
	@NotEmpty(message = "Stock Code must not be empty!")
	private String stockCode;
	@NotNull(message = "Unit name must not be empty!")
	private Integer unit;

	@DecimalMin("1.0")
	private double sellprice;

	@DecimalMin("1.0")
	private double purprise;
	@NotEmpty(message = "Stock type must not be empty!")
	private String stocktype;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public double getSellprice() {
		return sellprice;
	}

	public void setSellprice(double sellprice) {
		this.sellprice = sellprice;
	}

	public double getPurprise() {
		return purprise;
	}

	public void setPurprise(double purprise) {
		this.purprise = purprise;
	}

	public String getStocktype() {
		return stocktype;
	}

	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}

	public StockForm(Integer id, String name, String stockCode, Integer unit, double sellprice, double purprise,
			String stocktype) {
		super();
		this.id = id;
		this.name = name;
		this.stockCode = stockCode;
		this.unit = unit;
		this.sellprice = sellprice;
		this.purprise = purprise;
		this.stocktype = stocktype;
	}

	public StockForm() {
		super();
	}

}
