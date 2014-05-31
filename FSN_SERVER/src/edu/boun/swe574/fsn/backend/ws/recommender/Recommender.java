package edu.boun.swe574.fsn.backend.ws.recommender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.boun.swe574.fsn.backend.db.dao.BaseDao;
import edu.boun.swe574.fsn.backend.db.dao.DaoFactory;
import edu.boun.swe574.fsn.backend.db.model.Food;
import edu.boun.swe574.fsn.backend.db.model.FoodBlacklist;
import edu.boun.swe574.fsn.backend.db.model.Ingredient;
import edu.boun.swe574.fsn.backend.db.model.Recipe;
import edu.boun.swe574.fsn.backend.db.model.User;
import edu.boun.swe574.fsn.backend.db.model.UserProfile;
import edu.boun.swe574.fsn.backend.db.model.UserRecipeRating;
import edu.boun.swe574.fsn.backend.ws.ServiceCommons;

public class Recommender {
	
	// configuration parameters for the cache behavior and recommender
	private static final long cacheTimeout = 300000;
	private static final boolean cacheEnabled = true;
	private static final Double[] weights = new Double[]{1.0, 2.0, 3.0};
	
	// last cache times
	private static Calendar matrixCacheTime = null;
	private static Calendar ratingsCacheTime = null;
	
	// cached data
	private static Map<Recipe, List<Long>> cachedRecipeMatrix = null;
	private static Map<Recipe, Double> cachedRatingsMatrix = null;
	
	// singleton instance
	private static Recommender instance = null;
	
	public static Recommender getInstance(){
		if (instance == null){
			return instance = new Recommender();
		}
		return instance;
	}
	
	public static List<Recipe> recommend(List<Food> desired, User user){
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		// Retrieve the blacklist of the user
		UserProfile up = ServiceCommons.getProfile(user);
		List<FoodBlacklist> blist = baseDao.findByCriteria(FoodBlacklist.class, "userProfile", up);
		List<Food> blacklisted = new ArrayList<Food>();
		for (FoodBlacklist bl : blist){
			blacklisted.add(bl.getFood());
		}
		
		Map<Recipe, List<Long>> recipeMatrix = getRecipeMatrix();
		Map<Recipe, Double> ratingsMatrix = getRecipeRatingsMap();
		
		// Retrieve a list of all the recipes
		List<Recipe> recipes = baseDao.findAll(Recipe.class);
		
		Map<Recipe, Double> finalScores = new HashMap<Recipe, Double>();
		
		// for each recipe, push out a weighting on the criteria
		for (Recipe r : recipes){
			
			int foodsMatched = 0;
			int blacklistMatched = 0;
			
			//iterate over the foods in the recipe
			for(Long l : recipeMatrix.get(r)){
				Food f = baseDao.find(Food.class, l);
				if(desired.contains(f)){
					foodsMatched++;
				}
				
				// look for the food in the blacklist
				if(blacklisted.contains(f)){
					blacklistMatched++;
				}
			}
	
			double rate = ratingsMatrix.get(r);
			
			double score = weights[0]*(double)foodsMatched - weights[1]*(double)blacklistMatched + weights[2]*rate;
			finalScores.put(r, score);
			
		}
		
		//TODO: Sort the map on scores and output the list
		return new ArrayList<Recipe>();
	}
	
	private static Map<Recipe, List<Long>> getRecipeMatrix(){
		
		long cacheDiff = matrixCacheTime == null ? 400000 : Calendar.getInstance().getTimeInMillis() - matrixCacheTime.getTimeInMillis(); 
		
		if(!cacheEnabled || cacheDiff > cacheTimeout){
			
			Map<Recipe, List<Long>> recipeMatrix = new LinkedHashMap<Recipe, List<Long>>();
			
			BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
			List<Recipe> recipes = baseDao.findAll(Recipe.class);
			
			for(Recipe r : recipes){
				List<Ingredient> ingList = baseDao.findByCriteria(Ingredient.class, "recipe", r);
				List<Long> foodList = new ArrayList<Long>();
				for (Ingredient i : ingList){
					foodList.add(i.getFood().getId());
				}
				
				recipeMatrix.put(r, foodList);
			}
			
			cachedRecipeMatrix = recipeMatrix;
			matrixCacheTime = Calendar.getInstance();
		}
		
		return cachedRecipeMatrix;
		
	}
	
	private static Map<Recipe, Double> getRecipeRatingsMap(){
		
		long cacheDiff = ratingsCacheTime == null ? 400000 : Calendar.getInstance().getTimeInMillis() - ratingsCacheTime.getTimeInMillis();
		
		if (!cacheEnabled || cacheDiff > cacheTimeout){
			Map<Recipe, Double> ratingsMap = new LinkedHashMap<Recipe, Double>();
			
			BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
			List<Recipe> recipes = baseDao.findAll(Recipe.class);
			
			for(Recipe r : recipes){
				
				List<UserRecipeRating> ratings = baseDao.findByCriteria(UserRecipeRating.class, "recipe", r);
				
				double avg = 0;
				for(UserRecipeRating rate : ratings){
					avg += (double)rate.getRating();
				}
				avg = ratings.isEmpty() ? 0 : avg / (double)ratings.size();
				ratingsMap.put(r, avg);	
				
			}
			cachedRatingsMatrix = ratingsMap;
			ratingsCacheTime = Calendar.getInstance();
			
		}
		
		return cachedRatingsMatrix;
	}
	
}
