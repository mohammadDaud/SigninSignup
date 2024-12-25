package com.mc.myapp.models;

public class JwtRequest {

	private String email;
	private String password;
	private boolean rememberMe;
	public JwtRequest() {
	}

	public JwtRequest(String email, String password,boolean rememberMe) {
		this.email = email;
		this.password = password;
		this.rememberMe=rememberMe;
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

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	
	
}
