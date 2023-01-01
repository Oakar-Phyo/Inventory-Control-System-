package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;


@Entity
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message="User role must not be empty!!")
	private String name;
	
	@NotEmpty(message="Role permission must not be empty!!")
	private String prmit;
	

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
	public String getPrmit() {
		return prmit;
	}
	public void setPrmit(String prmit) {
		this.prmit = prmit;
	}
	
	public UserRole(Integer id, String name, String prmit) {
		super();
		this.id = id;
		this.name = name;
		this.prmit = prmit;
	}
	public UserRole() {
		super();
	}
	public UserRole(Integer role) {
		super();

	}
	
	
	
	
	
	
	
	

}
