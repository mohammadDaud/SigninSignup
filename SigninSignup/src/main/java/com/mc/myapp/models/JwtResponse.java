package com.mc.myapp.models;

import java.util.List;

public class JwtResponse {

	private String email;
	private String jwttoken;
	private List<String> roles;

	public JwtResponse() {
	}

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public JwtResponse(String jwttoken, String email, List<String> roles) {
		this.jwttoken = jwttoken;
		this.email = email;
		this.roles = roles;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
