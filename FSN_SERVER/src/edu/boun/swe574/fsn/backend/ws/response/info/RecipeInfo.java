package edu.boun.swe574.fsn.backend.ws.response.info;

import java.util.Date;
import java.util.List;

public class RecipeInfo {
	
	private long recipeId;
	private String recipeName;
	private String ownerName;
	private String ownerSurname;
	private int rating;
	private Date createDate;
	private String directions;
	private List<IngredientInfo> ingredientList;
	
	public long getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerSurname() {
		return ownerSurname;
	}
	public void setOwnerSurname(String ownerSurname) {
		this.ownerSurname = ownerSurname;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDirections() {
		return directions;
	}
	public void setDirections(String directions) {
		this.directions = directions;
	}
	public List<IngredientInfo> getIngredientList() {
		return ingredientList;
	}
	public void setIngredientList(List<IngredientInfo> ingredientList) {
		this.ingredientList = ingredientList;
	}

}
