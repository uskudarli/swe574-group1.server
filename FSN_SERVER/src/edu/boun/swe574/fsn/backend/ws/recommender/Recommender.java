package edu.boun.swe574.fsn.backend.ws.recommender;

import java.util.ArrayList;
import java.util.List;

import edu.boun.swe574.fsn.backend.db.dao.BaseDao;
import edu.boun.swe574.fsn.backend.db.dao.DaoFactory;
import edu.boun.swe574.fsn.backend.db.model.Food;
import edu.boun.swe574.fsn.backend.db.model.Recipe;
import edu.boun.swe574.fsn.backend.db.model.User;

public class Recommender {
	
	public static List<Recipe> recommend(List<Food> desired, User user){
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		baseDao.findAll(Recipe.class);
		
		return new ArrayList<Recipe>();
	}
	
	
}
