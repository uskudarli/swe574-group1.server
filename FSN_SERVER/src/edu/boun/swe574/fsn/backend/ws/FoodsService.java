package edu.boun.swe574.fsn.backend.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import edu.boun.swe574.fsn.backend.db.model.Recipe;

@WebService(name="AuthService", serviceName="AuthService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class FoodsService {

	@WebMethod
	public int createRecipe(	@WebParam(name="token")		String token, 
								@WebParam(name="recipe")	Object recipe){
		return 0;
	}
	
	@WebMethod
	public int getIngredients(	@WebParam(name="token")			String token, 
								@WebParam(name="queryString")	String queryString){
		return 0;
	}
	
	@WebMethod
	public int createNewVersionOfRecipe(	@WebParam(name="token")				String token, 
											@WebParam(name="recipe")			Recipe recipe, 
											@WebParam(name="parentRecipeId")	long parentRecipeId,  
											@WebParam(name="revisionNote")		String revisionNote){
		return 0;
	}
	
	@WebMethod
	public int getRecipe(	@WebParam(name="token")		String token, 
							@WebParam(name="recipeId")	long recipeId){
		return 0;
	}
	
	@WebMethod
	public int rateRecipe(	@WebParam(name="token")		String token, 
							@WebParam(name="recipeId")	long recipeId, 
							@WebParam(name="rateValue")	Integer rateValue){
		return 0;
	}
	
	@WebMethod
	public int getRevisionHistoryOfRecipe(	@WebParam(name="token")		String token, 
											@WebParam(name="recipeId")	long recipeId){
		return 0;
	}
}
