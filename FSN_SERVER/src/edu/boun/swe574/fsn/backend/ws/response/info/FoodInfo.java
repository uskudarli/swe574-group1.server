package edu.boun.swe574.fsn.backend.ws.response.info;

public class FoodInfo {
	
	private long foodId;
	private String foodName;
	
	public long getFoodId() {
		return this.foodId;
	}
	public void setFoodId(long foodId) {
		this.foodId = foodId;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	
}
