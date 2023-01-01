package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Warehouse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message="Warehouse code must not be empty!!")
	private String warehouseCode;
	@NotEmpty(message="Warehouse name must not be empty!!")
	private String name;
	@NotEmpty(message="Warehouse division must not be empty!!")
	private String location;
	@NotEmpty(message="StoreKeeper name must not be empty!!")
	private String storekeeper;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStorekeeper() {
		return storekeeper;
	}
	public void setStorekeeper(String storekeeper) {
		this.storekeeper = storekeeper;
	}
	
	
	
}
