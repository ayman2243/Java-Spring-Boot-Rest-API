package com.mondiamedia.tech.task.models.requests;

import javax.validation.constraints.NotNull;

public class UserLoginRequestModel {
	
	@NotNull(message="email is required!")
	private String email;
	
	@NotNull(message="password is required!")
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
