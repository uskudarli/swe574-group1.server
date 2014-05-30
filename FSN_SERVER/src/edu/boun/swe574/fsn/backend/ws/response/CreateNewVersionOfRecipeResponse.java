package edu.boun.swe574.fsn.backend.ws.response;


public class CreateNewVersionOfRecipeResponse extends BaseServiceResponse {

	private Long idOfRecipeCreated;

	public Long getIdOfRecipeCreated() {
		return idOfRecipeCreated;
	}

	public void setIdOfRecipeCreated(Long idOfRecipeCreated) {
		this.idOfRecipeCreated = idOfRecipeCreated;
	}
	
}
