package com.example.demo.Model.SearchService;


import org.springframework.stereotype.Component;

@Component
public class SearchService {

	protected String name;
    protected String providerId;
    protected String description;
    protected double price;
    protected String status;
    protected String rating;
    protected String skill;
    protected String category;
     
    //We need getter and setters to get the value of users
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPhoneno(double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
