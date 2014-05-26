package edu.boun.swe574.fsn.backend.ws.response.info;

public class IngredientInfo {
	
	private edu.boun.swe574.fsn.backend.ws.response.info.FoodInfo food;
	private double amount;
	private String unit;
	
	public edu.boun.swe574.fsn.backend.ws.response.info.FoodInfo getFood() {
		return food;
	}
	public void setFood(edu.boun.swe574.fsn.backend.ws.response.info.FoodInfo food) {
		this.food = food;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
