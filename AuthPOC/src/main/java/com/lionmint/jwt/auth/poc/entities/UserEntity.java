package com.lionmint.jwt.auth.poc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique=true)
	private String email;
	private String password;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	private String role;
	
	private boolean isAuthorized;
	private boolean isGoogleAuthroized;
	private boolean isFacebookAuthorized;
	
	public boolean isAuthorized() {
		return isAuthorized;
	}

	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isGoogleAuthroized() {
		return isGoogleAuthroized;
	}

	public void setGoogleAuthroized(boolean isGoogleAuthroized) {
		this.isGoogleAuthroized = isGoogleAuthroized;
	}

	public boolean isFacebookAuthorized() {
		return isFacebookAuthorized;
	}

	public void setFacebookAuthorized(boolean isFacebookAuthorized) {
		this.isFacebookAuthorized = isFacebookAuthorized;
	}
}
