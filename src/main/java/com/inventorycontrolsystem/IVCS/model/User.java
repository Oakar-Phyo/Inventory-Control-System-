package com.inventorycontrolsystem.IVCS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message = "Name must not be empty!!")
	private String name;
	@NotEmpty(message = "Email must not be empty!!")
	private String email;
	@NotEmpty(message = "Phone number must not be empty!!")
	private String phone;
	@NotEmpty(message = "Password must not be empty!!")
	private String pass;
	private String cofpass;

	private boolean enable;

   @ManyToOne
   @JoinColumn(name="role_id")
    private UserRole role;
	
	public String getName() {
		return name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getCofpass() {
		return cofpass;
	}
	public void setCofpass(String cofpass) {
		this.cofpass = cofpass;
	}
	
	
	
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	

	public User(Integer id, String name, String email, String phone, String pass, boolean enable, UserRole role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.pass = pass;
		this.enable = enable;
		this.role = role;
	}
	public User(String name, String email, String phone, String pass, boolean enable, UserRole role) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.pass = pass;
		this.enable = enable;
		this.role = role;
	}
	public User(String name, String email, String phone, String pass, String cofpass, boolean enable, UserRole role) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.pass = pass;
		this.cofpass = cofpass;
		this.enable = enable;
		this.role = role;
	}
	public User() {
		super();
		
	}
	
	
	
	
	
	
	

}
