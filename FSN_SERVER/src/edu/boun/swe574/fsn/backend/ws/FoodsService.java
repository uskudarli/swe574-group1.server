package edu.boun.swe574.fsn.backend.ws;

import java.util.ArrayList;
import java.util.Date;
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
import edu.boun.swe574.fsn.backend.db.model.UserRecipeRating;
import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.response.CreateNewVersionOfRecipeResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetIngredientsResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetRecipeResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetRevisionHistoryOfRecipeResponse;
import edu.boun.swe574.fsn.backend.ws.response.info.FoodInfo;
import edu.boun.swe574.fsn.backend.ws.response.info.IngredientInfo;
import edu.boun.swe574.fsn.backend.ws.response.info.RecipeInfo;
import edu.boun.swe574.fsn.backend.ws.response.info.RevisionInfo;
import edu.boun.swe574.fsn.backend.ws.util.InvalidTokenException;
import edu.boun.swe574.fsn.backend.ws.util.KeyValuePair;
import edu.boun.swe574.fsn.backend.ws.util.ServiceErrorCode;
import edu.boun.swe574.fsn.backend.ws.util.TokenExpiredException;

@WebService(name="FoodsService", serviceName="FoodsService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class FoodsService {

	// STATUS: OK
	@WebMethod
	public BaseServiceResponse createRecipe(	@WebParam(name="token")		String token, 
												@WebParam(name="recipe")	RecipeInfo recipe){
		
		// TODO: Clean up the transaction logic, it looks very fragile
		
		BaseServiceResponse response = new BaseServiceResponse();
		
		if (token == null || recipe == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
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
		
		try {
			
			List<IngredientInfo> ilist = recipe.getIngredientList();
			
			for (IngredientInfo ing : ilist){
				Food ff = baseDao.find(Food.class, (long)ing.getFood().getFoodId());
				if (ff == null){
					throw new Exception("The food was not found in the database");
				}
			}
			
			Recipe r = new Recipe();
			
			r.setDate(new Date());
			r.setDirections(recipe.getDirections());
			r.setTitle(recipe.getRecipeName());
			r.setUser(user);
			r.setVersionNote("Initial version");
			
			baseDao.save(r);
			
			List<IngredientInfo> inglist = recipe.getIngredientList();
			
			for (IngredientInfo ing : inglist){
				Ingredient i = new Ingredient();
				i.setAmount(ing.getAmount());
				Food food = baseDao.find(Food.class, (long)ing.getFood().getFoodId());
				i.setFood(food);
				i.setRecipe(r);
				i.setUnit(ing.getUnit());
				
				if (ing != null){
					baseDao.save(i);
				}
			}
			
			
			
		}
		catch (Exception e){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		response.succeed();
		return response;
	}
	
	// STATUS: OK
	@SuppressWarnings("unchecked")
	/**
	 * Could have well been "getFoods"! Gets the Food items in the database
	 * @param token
	 * @param queryString
	 * @return
	 */
	@WebMethod
	public GetIngredientsResponse getIngredients(	@WebParam(name="token")			String token, 
													@WebParam(name="queryString")	String queryString){
		
		GetIngredientsResponse response = new GetIngredientsResponse();
		
		if (token == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		try {
			ServiceCommons.authenticate(token, response);
		} catch (InvalidTokenException e) {
			response.fail(ServiceErrorCode.TOKEN_INVALID);
			return response;
		} catch (TokenExpiredException e) {
			response.fail(ServiceErrorCode.TOKEN_EXPIRED);
			return response;
		}
		
		try {
			if (queryString == null) {
				queryString = "";
			}
			KeyValuePair<String, Object> kvpair = new KeyValuePair<String, Object>("fname", queryString + "%");
			List<Food> foodList = baseDao.executeNamedQueryGetList("Food.findStartingWith", kvpair);
			
			List<FoodInfo> fiList = new ArrayList<FoodInfo>();
			
			for (Food f : foodList){
				FoodInfo fi = new FoodInfo();
				fi.setFoodId(f.getId());
				fi.setFoodName(f.getName());
				fi.setCategoryName(f.getParent().getName());
				fiList.add(fi);
			}
			
			response.setListOfIngredients(fiList);
		}
		catch (Exception e){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		response.succeed();
		return response;
	}
	
	// STATUS: untested
	@WebMethod
	public CreateNewVersionOfRecipeResponse createNewVersionOfRecipe(	@WebParam(name="token")				String token, 
											@WebParam(name="recipe")			RecipeInfo recipe, 
											@WebParam(name="parentRecipeId")	long parentRecipeId,  
											@WebParam(name="revisionNote")		String revisionNote){
		CreateNewVersionOfRecipeResponse response = new CreateNewVersionOfRecipeResponse();
		
		if (token == null || recipe == null || parentRecipeId == 0L || revisionNote == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
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
		
		try {
			Recipe r = new Recipe();
			r.setDate(new Date());
			r.setDirections(recipe.getDirections());
			r.setParentRecipe(baseDao.find(Recipe.class, parentRecipeId));
			r.setTitle(recipe.getRecipeName());
			r.setUser(user);
			r.setVersionNote(revisionNote);
			
			//fix for Issue 51. Recipe  must be saved first.
			baseDao.save(r);
			
			//for Issue 53
			response.setIdOfRecipeCreated(r.getId());
			
			List<IngredientInfo> inglist = recipe.getIngredientList();
			
			for (IngredientInfo ing : inglist){
				
				Ingredient i = new Ingredient();
				i.setAmount(ing.getAmount());
				Food food = baseDao.find(Food.class, (long)ing.getFood().getFoodId());
				i.setFood(food);
				i.setRecipe(r);
				i.setUnit(ing.getUnit());
				
				if (ing != null){
					baseDao.save(i);
				}
			}
			
			

		}
		catch (Exception e){
			e.printStackTrace();
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		response.succeed();
		return response;
	}
	
	// STATUS: OK
	@WebMethod
	public GetRecipeResponse getRecipe(	@WebParam(name="token")		String token, 
							@WebParam(name="recipeId")	Long recipeId){
		
		GetRecipeResponse response = new GetRecipeResponse();
		
		// TODO: test the 0L logic
		if (token == null || recipeId == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		try {
			ServiceCommons.authenticate(token, response);
		} catch (InvalidTokenException e) {
			response.fail(ServiceErrorCode.TOKEN_INVALID);
			return response;
		} catch (TokenExpiredException e) {
			response.fail(ServiceErrorCode.TOKEN_EXPIRED);
			return response;
		}
		
		try {
			
			Recipe r = baseDao.find(Recipe.class, recipeId);
			if (r == null){
				response.fail(ServiceErrorCode.RECIPE_NOT_FOUND);
				return response;
			}
			RecipeInfo ri = new RecipeInfo();
			
			ri.mapRecipe(r);
			
			//TODO: debug&test the query logic!
			List<Ingredient> inglist = baseDao.findByCriteria(Ingredient.class, "recipe", r);
			
			//TODO: debug&test the map logic
			ri.mapIngredientList(inglist);
			
			List<UserRecipeRating> rateList = baseDao.findByCriteria(UserRecipeRating.class, "recipe", r);
			double avg = 0;
			for (UserRecipeRating rate : rateList){
				avg += (double)rate.getRating();
			}
			avg /= rateList.size();
			
			//TODO: What happens in the typecast here??
			ri.setRating((int)avg);
			
			response.setRecipe(ri);
		}
		catch (Exception e){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		response.succeed();
		return response;
	}
	
	// STATUS: OK
	@WebMethod
	public BaseServiceResponse rateRecipe(	@WebParam(name="token")		String token, 
							@WebParam(name="recipeId")	Long recipeId, 
							@WebParam(name="rateValue")	Integer rateValue){
		BaseServiceResponse response = new BaseServiceResponse();
		
		if (token == null || recipeId == null || rateValue == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
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
		
		try {
			Recipe r = baseDao.find(Recipe.class, recipeId);
			if (r == null){
				response.fail(ServiceErrorCode.RECIPE_NOT_FOUND);
				return response;
			}
			
			if (rateValue < 0 || rateValue > 5){
				response.fail(ServiceErrorCode.RATE_VALUE_INVALID);
				return response;
			}
			
			//TODO: Update if the user already has a rating
			List<UserRecipeRating> ratelist = baseDao.findByCriteria(UserRecipeRating.class, new String[]{"recipe", "user"}, new Object[]{r, user});
			
			if (ratelist.isEmpty()){
				// User has no rating before
				UserRecipeRating rate = new UserRecipeRating();
				rate.setUser(user);
				rate.setRating(rateValue);
				rate.setRecipe(r);
				
				baseDao.save(rate);
			}
			else {
				// User has a rating from before
				UserRecipeRating rate = ratelist.get(0);
				rate.setRating(rateValue);
				
				baseDao.save(rate);
			}			
			
		}
		catch(Exception e){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		response.succeed();
		return response;
	}
	
	// STATUS: OK
	@WebMethod
	public GetRevisionHistoryOfRecipeResponse getRevisionHistoryOfRecipe(	@WebParam(name="token")		String token, 
											@WebParam(name="recipeId")	Long recipeId){
		
		GetRevisionHistoryOfRecipeResponse response = new GetRevisionHistoryOfRecipeResponse();
		
		if (token == null || recipeId == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		try {
			ServiceCommons.authenticate(token, response);
		} catch (InvalidTokenException e) {
			response.fail(ServiceErrorCode.TOKEN_INVALID);
			return response;
		} catch (TokenExpiredException e) {
			response.fail(ServiceErrorCode.TOKEN_EXPIRED);
			return response;
		}
		
		try {
			List<RevisionInfo> riList = new ArrayList<RevisionInfo>();
			
			Recipe root = baseDao.find(Recipe.class, recipeId);
			if (root == null){
				response.fail(ServiceErrorCode.RECIPE_NOT_FOUND);
				return response;
			}
			riList.add(RevisionInfo.mapRecipe(root));
			
			while (root.getParentRecipe() != null){
				root = root.getParentRecipe();
				riList.add(RevisionInfo.mapRecipe(root));
			}
			
			response.setListOfRevisions(riList);
		}
		catch (Exception e) {
			e.printStackTrace();
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		response.succeed();
		return response;
	}
}
