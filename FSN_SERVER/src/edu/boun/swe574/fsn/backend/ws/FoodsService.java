package edu.boun.swe574.fsn.backend.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetIngredientsResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetRecipeResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetRevisionHistoryOfRecipeResponse;
import edu.boun.swe574.fsn.backend.ws.response.info.RecipeInfo;

@WebService(name="FoodsService", serviceName="FoodsService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class FoodsService {

	@WebMethod
	public BaseServiceResponse createRecipe(	@WebParam(name="token")		String token, 
								@WebParam(name="recipe")	RecipeInfo recipe){
		return new BaseServiceResponse();
	}
	
	@WebMethod
	public GetIngredientsResponse getIngredients(	@WebParam(name="token")			String token, 
								@WebParam(name="queryString")	String queryString){
		return new GetIngredientsResponse();
	}
	
	@WebMethod
	public BaseServiceResponse createNewVersionOfRecipe(	@WebParam(name="token")				String token, 
											@WebParam(name="recipe")			RecipeInfo recipe, 
											@WebParam(name="parentRecipeId")	long parentRecipeId,  
											@WebParam(name="revisionNote")		String revisionNote){
		return new BaseServiceResponse();
	}
	
	@WebMethod
	public GetRecipeResponse getRecipe(	@WebParam(name="token")		String token, 
							@WebParam(name="recipeId")	long recipeId){
		
		return new GetRecipeResponse();
	}
	
	@WebMethod
	public BaseServiceResponse rateRecipe(	@WebParam(name="token")		String token, 
							@WebParam(name="recipeId")	long recipeId, 
							@WebParam(name="rateValue")	Integer rateValue){
		
		return new BaseServiceResponse();
	}
	
	@WebMethod
	public GetRevisionHistoryOfRecipeResponse getRevisionHistoryOfRecipe(	@WebParam(name="token")		String token, 
											@WebParam(name="recipeId")	long recipeId){
		
		return new GetRevisionHistoryOfRecipeResponse();
	}
}
