package edu.boun.swe574.fsn.backend.ws.response.info;

public class FoodInfo {
	
	private long foodId;
	private String foodName;
	private String categoryName;
	
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
}
