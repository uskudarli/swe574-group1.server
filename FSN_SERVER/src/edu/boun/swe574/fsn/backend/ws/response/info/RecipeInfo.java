package edu.boun.swe574.fsn.backend.ws.response.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.boun.swe574.fsn.backend.db.model.Ingredient;
import edu.boun.swe574.fsn.backend.db.model.Recipe;

public class RecipeInfo {
	
	private long recipeId;
	private String recipeName;
	private String ownerName;
	private String ownerSurname;
	private int rating;
	private Date createDate;
	private String directions;
	private List<IngredientInfo> ingredientList;
	private int ownRating;
	
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
	
	public void mapRecipe(Recipe r){
		this.setCreateDate(r.getDate());
		this.setDirections(r.getDirections());
		this.setOwnerName(r.getUser().getName());
		this.setOwnerSurname(r.getUser().getSurname());
		
		// fill this somewhere else!!
		this.setRating(0);
		this.setRecipeId(r.getId());
		this.setRecipeName(r.getTitle());
	}

	public void mapIngredientList(List<Ingredient> list){
		List<IngredientInfo> iilist = new ArrayList<IngredientInfo>();
		for (Ingredient i : list){
			IngredientInfo info = new IngredientInfo();
			info.setAmount(i.getAmount());
			FoodInfo fi = new FoodInfo();
			
			fi.setFoodId(i.getFood().getId());
			fi.setFoodName(i.getFood().getName());
			fi.setCategoryName(i.getFood().getParent().getName());
			info.setFood(fi);
			info.setUnit(i.getUnit());
			
			iilist.add(info);
		}
		this.setIngredientList(iilist);
	}
	public int getOwnRating() {
		return ownRating;
	}
	public void setOwnRating(int ownRating) {
		this.ownRating = ownRating;
	}
}
