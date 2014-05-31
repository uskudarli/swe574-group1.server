package edu.boun.swe574.fsn.backend.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import edu.boun.swe574.fsn.backend.db.model.FoodBlacklist;
import edu.boun.swe574.fsn.backend.db.model.Ingredient;
import edu.boun.swe574.fsn.backend.db.model.Recipe;
import edu.boun.swe574.fsn.backend.db.model.User;
import edu.boun.swe574.fsn.backend.db.model.UserFollowLink;
import edu.boun.swe574.fsn.backend.db.model.UserProfile;
import edu.boun.swe574.fsn.backend.ws.response.info.FoodInfo;
import edu.boun.swe574.fsn.backend.ws.request.info.FoodList;
import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetProfileResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetRecipeFeedsResponse;
import edu.boun.swe574.fsn.backend.ws.response.SearchForUsersResponse;
import edu.boun.swe574.fsn.backend.ws.response.info.RecipeInfo;
import edu.boun.swe574.fsn.backend.ws.response.info.UserInfo;
import edu.boun.swe574.fsn.backend.ws.util.InvalidTokenException;
import edu.boun.swe574.fsn.backend.ws.util.KeyValuePair;
import edu.boun.swe574.fsn.backend.ws.util.ServiceErrorCode;
import edu.boun.swe574.fsn.backend.ws.util.TokenExpiredException;

@WebService(name="NetworkService", serviceName="NetworkService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class NetworkService {

	// STATUS: OK
	@SuppressWarnings("unchecked")
	@WebMethod
	public GetProfileResponse getProfileOfSelf( @WebParam(name="token") String token){

		GetProfileResponse response = new GetProfileResponse();
		
		if (token == null){
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

			KeyValuePair<String,Object> qParams = new KeyValuePair<String,Object>("uid", user.getId());
			UserProfile up = baseDao.executeNamedQuery("UserProfile.getUserProfile", qParams);
			
			if(up != null){
				response.mapUserProfile(up);
			}
			
			List<FoodBlacklist> bllist = baseDao.findByCriteria(FoodBlacklist.class, 
					new String[] {"userProfile"}, 
					new Object[] {up});
			
			List<FoodInfo> returnBlacklist = new ArrayList<FoodInfo>();
			for(FoodBlacklist bl : bllist){
				FoodInfo fi = new FoodInfo();
				fi.setFoodId(bl.getFood().getId());
				fi.setFoodName(bl.getFood().getName());
				fi.setCategoryName(bl.getFood().getParent().getName());
				returnBlacklist.add(fi);
			}
			
			response.setIngredientBlackList(returnBlacklist);
		
		}catch(Exception e){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
			
		response.succeed();
		return response;
	}
	
	// STATUS: OK
	@SuppressWarnings("unchecked")
	@WebMethod
	public BaseServiceResponse editProfile(	@WebParam(name="token") 			String token, 
											@WebParam(name="location") 			String location, 
											@WebParam(name="dateOfBirth")		String dateOfBirth, 
											@WebParam(name="profileMessage")	String profileMessage ){
		
		BaseServiceResponse response = new BaseServiceResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		if (token == null || location == null || dateOfBirth == null || profileMessage == null){
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
		
		try {
			// TODO: Turkish character problems
			KeyValuePair<String,Object> qParams = new KeyValuePair<String,Object>("uid", user.getId());
			UserProfile up = baseDao.executeNamedQuery("UserProfile.getUserProfile", qParams);
			
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			Date parsedDate = formatter.parse(dateOfBirth);
			
			up.setDateOfBirth(parsedDate);
			up.setLocation(location);
			up.setProfileMessage(profileMessage);
			
			baseDao.update(up);
			
			response.succeed();
			return response;
		}
		catch(ParseException pe){
			System.out.println("parse exception encountered!");
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
	}
	
	// STATUS: OK
	@SuppressWarnings("unchecked")
	@WebMethod
	public SearchForUsersResponse searchForUsers(@WebParam(name="token")			String token, 
							  					@WebParam(name="queryString") 	String queryString) {
		
		SearchForUsersResponse response = new SearchForUsersResponse();
		
		if (token == null || queryString == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}

		try {
			ServiceCommons.authenticate(token, response);
		} catch (InvalidTokenException e) {
			response.fail(ServiceErrorCode.TOKEN_INVALID);
			return response;
		} catch (TokenExpiredException e) {
			response.fail(ServiceErrorCode.TOKEN_EXPIRED);
			return response;
		}
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		KeyValuePair<String, Object> qParams = new KeyValuePair<String, Object>("name", "%" + queryString + "%");
		List<User> userList = baseDao.executeNamedQueryGetList("User.findByName", qParams);
		
		
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		for (User u : userList){
			UserInfo uinfo = new UserInfo();
			uinfo.setEmail(u.getEmail());
			uinfo.setName(u.getName());
			uinfo.setSurname(u.getSurname());
			uinfo.setUserId(u.getId());
			uinfo.setProfileMessage(ServiceCommons.getProfile(u).getProfileMessage());
			
			userInfoList.add(uinfo);
		}
		
		
		response.setUserList(userInfoList);
		
		response.succeed();
		return response;
	}
	
	// STATUS: untested issue fix #41
	@SuppressWarnings("unchecked")
	@WebMethod
	public GetProfileResponse getProfileOfOtherUser(	@WebParam(name="token") 	String token, 
										@WebParam(name="email")		String email){
		
		GetProfileResponse response = new GetProfileResponse();
		
		if (token == null || email == null){
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
			KeyValuePair<String, Object> qParams = new KeyValuePair<String, Object>("email", email);
			User targetUser = baseDao.executeNamedQuery("User.findByEmail", qParams);
			
			if (targetUser == null) {
				response.fail(ServiceErrorCode.USER_NOT_FOUND);
				return response;
			}
			
			KeyValuePair<String, Object> upParams = new KeyValuePair<String, Object>("uid", targetUser.getId());
			UserProfile up = baseDao.executeNamedQuery("UserProfile.getUserProfile", upParams);
			
			response.mapUserProfile(up);
			
			List<FoodBlacklist> bllist = baseDao.findByCriteria(FoodBlacklist.class, 
					new String[] {"userProfile"}, 
					new Object[] {up});
			
			List<FoodInfo> returnBlacklist = new ArrayList<FoodInfo>();
			for(FoodBlacklist bl : bllist){
				FoodInfo fi = new FoodInfo();
				fi.setFoodId(bl.getFood().getId());
				fi.setFoodName(bl.getFood().getName());
				fi.setCategoryName(bl.getFood().getParent().getName());
				returnBlacklist.add(fi);
			}
			
			response.setIngredientBlackList(returnBlacklist);
			response.setName(targetUser.getName());
			response.setSurname(targetUser.getSurname());
		}
		catch (Exception e){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		return response;
	}
	
	// STATUS: OK
	@SuppressWarnings("unchecked")
	@WebMethod
	public BaseServiceResponse follow(	@WebParam(name="token")		String token, 
										@WebParam(name="email")		String email){
		
		BaseServiceResponse response= new BaseServiceResponse();
		
		if(token == null || email == null){
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
			KeyValuePair<String, Object> qParams = new KeyValuePair<String, Object>("email", email);
			User targetUser = baseDao.executeNamedQuery("User.findByEmail", qParams);
			
			if (targetUser == null) {
				response.fail(ServiceErrorCode.USER_NOT_FOUND);
				return response;
			}
			
			UserFollowLink link = new UserFollowLink();
			link.setFollowingUser(user);
			link.setFollowedUser(targetUser);
			baseDao.save(link);
			
			response.succeed();
			return response;
		}
		catch(Exception e){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		
	}
	
	// STATUS: OK
	@WebMethod
	public SearchForUsersResponse getFollowedUser(	@WebParam(name="token")	String token){
		
		SearchForUsersResponse response = new SearchForUsersResponse();
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
		
		List<UserFollowLink> linkList = baseDao.findByCriteria(UserFollowLink.class, 
																"followingUser", user);
		
		List<UserInfo> userList = new ArrayList<UserInfo>();
		
		for (UserFollowLink link : linkList){
			UserInfo ui = new UserInfo();
			ui.setName(link.getFollowedUser().getName());
			ui.setSurname(link.getFollowedUser().getSurname());
			ui.setEmail(link.getFollowedUser().getEmail());
			ui.setUserId(link.getFollowedUser().getId());
			userList.add(ui);
		}
			
		response.setUserList(userList);
		response.succeed();
		return response;
	}
	
	// STATUS: untested
	@WebMethod
	public GetRecipeFeedsResponse getRecipeFeeds(	@WebParam(name="token")		String token){
		
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
		
		try {
			// Get users the user follows
			List<UserFollowLink> followedList = baseDao.findByCriteria(UserFollowLink.class, 
																			"followingUser", 
																			user);
			
			// Extract and list the users from the link items
			List<User> followedUsers = new ArrayList<User>();
			
			for (UserFollowLink link : followedList)
				followedUsers.add(link.getFollowedUser());
			// user's own recipes will also be 
			followedUsers.add(user);
			
			// for all users, get the recipes which these users prepared
			List<Recipe> recipeList = new ArrayList<Recipe>();
			for (User u: followedUsers){
				List<Recipe> userRecipeList = baseDao.findByCriteria(Recipe.class, "user", u);
				for (Recipe r: userRecipeList)
					recipeList.add(r);
			}
			
			// sort from most recent to least
			// TODO: check the sort!!
			Collections.sort(recipeList);
			
			// set the index / pagesize
			List<RecipeInfo> riList = new ArrayList<RecipeInfo>();
			for (Recipe r : recipeList){
				RecipeInfo ri = new RecipeInfo();
				ri.mapRecipe(r);
				
				List<Ingredient> ingList = baseDao.findByCriteria(Ingredient.class, "recipe", r);
				
				ri.mapIngredientList(ingList);
				riList.add(ri);
			}
			
			response.setRecipeList(riList);
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
	@WebMethod
	public BaseServiceResponse updatePhoto( @WebParam(name="token") String token,
											@WebParam(name="image") byte[] image){
		
		BaseServiceResponse response = new BaseServiceResponse();
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
		
		try {
			BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
			KeyValuePair<String, Object> qParams = new KeyValuePair<String, Object>("uid", user.getId()); 
			UserProfile up = baseDao.executeNamedQuery("UserProfile.getUserProfile", qParams);
			
			up.setImage(image);
			
			baseDao.update(up);
			
			response.succeed();
			return response;
		}
		catch(Exception e){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}		
		
	}

	// STATUS: OK
	@WebMethod
	public BaseServiceResponse deletePhoto(@WebParam(name="token") String token){
		return updatePhoto(token, null);
	}

	// STATUS: OK
	@WebMethod
	public BaseServiceResponse addToBlacklist (@WebParam(name="token") String token,
												@WebParam(name="addlistIn") FoodList addlistIn){
		
		BaseServiceResponse response = new BaseServiceResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		if(token == null || addlistIn == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}
		
		List<FoodInfo> addlist = addlistIn.getList();
		
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
		
		UserProfile up = ServiceCommons.getProfile(user);
		if (up == null){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		for(FoodInfo fi : addlist){
			try {
				FoodBlacklist blitem = new FoodBlacklist();
				Food f = baseDao.find(Food.class, (long)fi.getFoodId());
				blitem.setFood(f);
				blitem.setUserProfile(up);
				if(f != null){
					baseDao.save(blitem);
				}
			} catch(Exception e){
				// an integrity error could occur (?) if there is a unique constraint
				// on the DB and the service caller tries to add a Food-UserProfile link
				// that already was in the table
				response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
				return response;
			}
		}
		
		response.succeed();
		return response;
	}
	
	// STATUS: OK
	@WebMethod
	public BaseServiceResponse removeFromBlacklist (@WebParam(name="token") String token,
													@WebParam(name="rmlistIn") FoodList rmlistIn){
		BaseServiceResponse response = new BaseServiceResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		if(token == null || rmlistIn == null){
			response.fail(ServiceErrorCode.MISSING_PARAM);
			return response;
		}
		
		List<FoodInfo> rmlist = rmlistIn.getList();
		
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
		
		UserProfile up = ServiceCommons.getProfile(user);
		if (up == null){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		try {
			for (FoodInfo fi : rmlist){
				Food f = baseDao.find(Food.class, (long)fi.getFoodId());
				List<FoodBlacklist> bllist = baseDao.findByCriteria(FoodBlacklist.class, 
												new String[] {"userProfile", "food"}, 
												new Object[] {up, f});
				for (FoodBlacklist bl : bllist){
					baseDao.delete(bl);
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
	
}
