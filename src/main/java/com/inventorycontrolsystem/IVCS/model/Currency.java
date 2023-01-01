package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;




@Entity

public class Currency {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "Currency code must not be empty!!")
	private String currencyCode;
	
	@NotEmpty(message = "Currency name must not be empty!!")
	private String name;
	
	@NotEmpty(message = "Currency short name must not be empty!!")
	private String sname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
	
	
	  public boolean currencyCodeCheck(String cuCode) {
	  
	  if(cuCode.startsWith("cuy")) {
		  cuCode = cuCode.substring(3);
		  
		  
	  
	  return true;
	  
	  } else {
		  return false;
	  
	  }
	  
	  }
	 

}
