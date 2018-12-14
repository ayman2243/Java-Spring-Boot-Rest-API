/**
 * @author AymanAljohary
 * @IDE Spring Tool Suite
 */

package com.mondiamedia.tech.task.models.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequestModel {

	@NotNull(message="firstName is required!")
	@Size(min=2, max=255, message="firstName must be equal or greater than 2 characters and less than 50 characters!")
	private String firstName;
	
	@NotNull(message="lastName is required!")
	@Size(min=2, max=255, message="lastName must be equal or greater than 2 characters and less than 50 characters!")
	private String lastName;
	
	@Email(message="email is not valid!")
	@NotNull(message="email is required!")
	@Size(min=2, max=255, message="email must be equal or greater than 2 characters and less than 100 characters!")
	private String email;
	
	@NotNull(message="password is required!")
	@Size(min=8, max=255, message="password must be equal or greater than 8 characters and less than 50 characters!")
	private String password;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
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
