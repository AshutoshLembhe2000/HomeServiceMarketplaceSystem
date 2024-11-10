package com.example.demo.Model.User;

import org.springframework.stereotype.Component;

@Component
public abstract class User {
	protected String name;
    protected String email;
    protected String password;
    protected int phoneno;
    protected String city;
    
    //This method will get override since it will be used by the Admin, Customer and ServiceProvider
    public abstract String getUserType();
    
    //We need getter and setters to get the value of users
    //Since both customer and service provider need the same basic info
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(int phoneno) {
		this.phoneno = phoneno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
