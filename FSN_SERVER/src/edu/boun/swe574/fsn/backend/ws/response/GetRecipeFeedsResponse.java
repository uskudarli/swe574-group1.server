package edu.boun.swe574.fsn.backend.ws.response;

import java.util.List;

import edu.boun.swe574.fsn.backend.ws.response.info.RecipeInfo;

public class GetRecipeFeedsResponse extends BaseServiceResponse {

	private List<RecipeInfo> recipeList;

	public List<RecipeInfo> getRecipeList() {
		return recipeList;
	}

	public void setRecipeList(List<RecipeInfo> recipeList) {
		this.recipeList = recipeList;
	}
	
}
