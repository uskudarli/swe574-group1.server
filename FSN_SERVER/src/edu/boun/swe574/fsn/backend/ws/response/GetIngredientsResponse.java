package edu.boun.swe574.fsn.backend.ws.response;

import java.util.List;

import edu.boun.swe574.fsn.backend.ws.response.info.IngredientInfo;

public class GetIngredientsResponse extends BaseServiceResponse {

	List<IngredientInfo> listOfIngredients;

	public List<IngredientInfo> getListOfIngredients() {
		return listOfIngredients;
	}

	public void setListOfIngredients(List<IngredientInfo> listOfIngredients) {
		this.listOfIngredients = listOfIngredients;
	}
	
}
