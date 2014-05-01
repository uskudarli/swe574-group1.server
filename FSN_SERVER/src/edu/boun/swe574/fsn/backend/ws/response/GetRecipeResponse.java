package edu.boun.swe574.fsn.backend.ws.response;

import edu.boun.swe574.fsn.backend.ws.response.info.RecipeInfo;

public class GetRecipeResponse extends BaseServiceResponse {

	private RecipeInfo recipe;

	public RecipeInfo getRecipe() {
		return recipe;
	}

	public void setRecipe(RecipeInfo recipe) {
		this.recipe = recipe;
	}
	
}
