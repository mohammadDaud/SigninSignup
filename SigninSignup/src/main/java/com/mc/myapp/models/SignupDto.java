package com.mc.myapp.models;

public class SignupDto extends UserDto {
    
	private static final long serialVersionUID = 1L;
	private String password;
	public SignupDto() {}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
    public String toString() {
        return "SignupDto{" + super.toString() + "} ";
    }
}
