package com.inventorycontrolsystem.IVCS.formmodel;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class UserForm {

	private Integer id;
	@NotEmpty(message = "Name must not be empty!!")
	private String name;
	@NotEmpty(message = "Email must not be empty!!")
	@Email(message = "Wrong e-mail")
	private String email;
	private String phone;
	@NotEmpty(message = "Password must not be empty!!")
	private String pass;
	@NotEmpty(message  = "Confirm password must not be empty!!")
	private String cofpass;
	@NotNull(message = "User Role must not be empty!!")
	private Integer role;
	
	public String getName() {
		return name;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}

	public UserForm(Integer id, String name, String email, String phone, String pass, String cofpass, Integer role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.pass = pass;
		this.cofpass = cofpass;
		this.role = role;
	}
	public UserForm() {
		super();
	}
	
	
	
	
}
