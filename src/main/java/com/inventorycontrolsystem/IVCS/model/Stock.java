package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Entity
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Stock code must not be empty!!")
	private String stockCode;
	@NotEmpty(message = "Stock name must not be empty!!")
	private String name;

	@DecimalMin("1.0")
	private double sellprice;

	@DecimalMin("1.0")
	private double purprise;
	@NotEmpty(message = "Stock type must not be empty!!")
	private String stocktype;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_id")
	private Unit unit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
