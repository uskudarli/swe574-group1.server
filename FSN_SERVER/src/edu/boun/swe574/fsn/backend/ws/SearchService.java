package edu.boun.swe574.fsn.backend.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import edu.boun.swe574.fsn.backend.db.dao.BaseDao;
import edu.boun.swe574.fsn.backend.db.dao.DaoFactory;
import edu.boun.swe574.fsn.backend.db.model.Food;
import edu.boun.swe574.fsn.backend.db.model.Ingredient;
import edu.boun.swe574.fsn.backend.db.model.Recipe;
import edu.boun.swe574.fsn.backend.db.model.User;
import edu.boun.swe574.fsn.backend.ws.recommender.Recommender;
import edu.boun.swe574.fsn.backend.ws.response.GetRecipeFeedsResponse;
import edu.boun.swe574.fsn.backend.ws.response.info.RecipeInfo;
import edu.boun.swe574.fsn.backend.ws.util.InvalidTokenException;
import edu.boun.swe574.fsn.backend.ws.util.ServiceErrorCode;
import edu.boun.swe574.fsn.backend.ws.util.TokenExpiredException;

@WebService(name="SearchService", serviceName="SearchService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class SearchService {
	
	@WebMethod
	public GetRecipeFeedsResponse searchForRecipes(	@WebParam(name="token") 						String token, 
									@WebParam(name="listOfPreferredIngredients")	long[] listOfPreferredIngredients){
		
		GetRecipeFeedsResponse response = new GetRecipeFeedsResponse();
		
		if (token == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}
			
		User user;
		try {
			user = ServiceCommons.authenticate(token, response);
		} catch (InvalidTokenException e) {
			response.fail(ServiceErrorCode.TOKEN_INVALID);
			return response;
		} catch (TokenExpiredException e) {
			response.fail(ServiceErrorCode.TOKEN_EXPIRED);
			return response;
		}
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		List<Food> foodDesired = new ArrayList<Food>();
		for(long id:listOfPreferredIngredients){
			Food f = baseDao.find(Food.class, id);
			if(f != null){
				foodDesired.add(f);
			}
		}
		
		List<Recipe> recipeList = Recommender.recommend(foodDesired, user);
		List<RecipeInfo> riList = new ArrayList<RecipeInfo>();
		for(Recipe r : recipeList){
			RecipeInfo ri = new RecipeInfo();
			ri.mapRecipe(r);
			
			List<Ingredient> ingList = baseDao.findByCriteria(Ingredient.class, "recipe", r);
			ri.mapIngredientList(ingList);
			
			riList.add(ri);
		}
		
		response.setRecipeList(riList);
		
		response.succeed();
		return response;
		
	}
	
}
