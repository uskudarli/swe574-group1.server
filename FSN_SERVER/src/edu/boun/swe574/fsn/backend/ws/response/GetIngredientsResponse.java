package edu.boun.swe574.fsn.backend.ws.response;

import java.util.List;

import edu.boun.swe574.fsn.backend.ws.response.info.FoodInfo;

public class GetIngredientsResponse extends BaseServiceResponse {

	List<FoodInfo> listOfIngredients;

	public List<FoodInfo> getListOfIngredients() {
		return listOfIngredients;
	}

	public void setListOfIngredients(List<FoodInfo> listOfIngredients) {
		this.listOfIngredients = listOfIngredients;
	}
	
}
