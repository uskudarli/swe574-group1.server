package edu.boun.swe574.fsn.backend.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import edu.boun.swe574.fsn.backend.db.model.FoodBlacklist;
import edu.boun.swe574.fsn.backend.db.model.User;
import edu.boun.swe574.fsn.backend.db.model.UserFollowLink;
import edu.boun.swe574.fsn.backend.db.model.UserProfile;
import edu.boun.swe574.fsn.backend.ws.request.info.FoodInfo;
import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetProfileResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetRecipeFeedsResponse;
import edu.boun.swe574.fsn.backend.ws.response.SearchForUsersResponse;
import edu.boun.swe574.fsn.backend.ws.response.info.UserInfo;
import edu.boun.swe574.fsn.backend.ws.util.InvalidTokenException;
import edu.boun.swe574.fsn.backend.ws.util.KeyValuePair;
import edu.boun.swe574.fsn.backend.ws.util.ServiceErrorCode;
import edu.boun.swe574.fsn.backend.ws.util.TokenExpiredException;

@WebService(name="NetworkService", serviceName="NetworkService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class NetworkService {

	// STATUS: incomplete
	@SuppressWarnings("unchecked")
	@WebMethod
	public GetProfileResponse getProfileOfSelf(@WebParam(name="token") String token){
		
		GetProfileResponse response = new GetProfileResponse();
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
		
		KeyValuePair<String,Object> qParams = new KeyValuePair<String,Object>("uid", user.getId());
		UserProfile up = baseDao.executeNamedQuery("UserProfile.getUserProfile", qParams);
		
		if(up != null){
			response.mapUserProfile(up);
		}
		
		response.succeed();
		return response;
	}
	
	// STATUS: untested
	@SuppressWarnings("unchecked")
	@WebMethod
	public BaseServiceResponse editProfile(	@WebParam(name="token") 			String token, 
											@WebParam(name="location") 			String location, 
											@WebParam(name="dateOfBirth")		String dateOfBirth, 
											@WebParam(name="profileMessage")	String profileMessage ){
		
		BaseServiceResponse response = new BaseServiceResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
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
	
	// STATUS: not started
	@WebMethod
	public SearchForUsersResponse searchForUsers(@WebParam(name="token")			String token, 
							  @WebParam(name="queryString") 	String queryString) {
		
		SearchForUsersResponse response = new SearchForUsersResponse();
		
		UserInfo user = new UserInfo();
		user.setName("Marcelino");
		user.setSurname("Sanchez");
		user.setEmail("marcelino@sanchez.com");
		user.setUserId(1L);
		
		List<UserInfo> userList = new ArrayList<UserInfo>();
		userList.add(user);
		
		response.setUserList(userList);
		
		return response;
	}
	
	// STATUS: not started
	@WebMethod
	public GetProfileResponse getProfileOfOtherUser(	@WebParam(name="token") 	String token, 
										@WebParam(name="email")		String email){
		return new GetProfileResponse();
	}
	
	// STATUS: untested
	@SuppressWarnings("unchecked")
	@WebMethod
	public BaseServiceResponse follow(	@WebParam(name="token")		String token, 
										@WebParam(name="email")		String email){
		
		BaseServiceResponse response= new BaseServiceResponse();
		
		if(token == null){
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
	
	// STATUS: untested
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
	
	// STATUS: not started
	@WebMethod
	public GetRecipeFeedsResponse getRecipeFeeds(	@WebParam(name="token")		String token, 
								@WebParam(name="index")		Integer index, 
								@WebParam(name="pageSize")	Integer pageSize){
		return new GetRecipeFeedsResponse();
	}

	// STATUS: untested
	@SuppressWarnings("unchecked")
	@WebMethod
	public BaseServiceResponse updatePhoto( @WebParam(name="token") String token,
											@WebParam(name="image") byte[] image){
		
		BaseServiceResponse response = new BaseServiceResponse();
		if (token == null || image == null){
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

	// STATUS: untested
	@WebMethod
	public BaseServiceResponse deletePhoto(@WebParam(name="token") String token){
		return updatePhoto(token, null);
	}
	
	
//	public BaseServiceResponse updateIngredientBlacklist(	@WebParam(name="token") String token,
//															@WebParam(name="blacklist") List<Food> blacklist ){
//		return new BaseServiceResponse();
//	}
	
	// STATUS: untested
	public BaseServiceResponse addToBlacklist (@WebParam(name="token") String token,
												@WebParam(name="addlist") List<FoodInfo> addlist){
		
		BaseServiceResponse response = new BaseServiceResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		if(token == null || addlist == null){
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
		
		UserProfile up = ServiceCommons.getProfile(user);
		if (up == null){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		for(FoodInfo fi : addlist){
			try {
				FoodBlacklist blitem = new FoodBlacklist();
				// the following line's logic is untested!!!
				Food f = baseDao.find(Food.class, fi.getFoodId());
				blitem.setFood(f);
				blitem.setUserProfile(up);
				baseDao.save(up);
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
	
	// STATUS: untested
	public BaseServiceResponse removeFromBlacklist (@WebParam(name="token") String token,
													@WebParam(name="rmlist") List<FoodInfo> rmlist){
		BaseServiceResponse response = new BaseServiceResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		if(token == null || rmlist == null){
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
		
		UserProfile up = ServiceCommons.getProfile(user);
		if (up == null){
			response.fail(ServiceErrorCode.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		try {
			for (FoodInfo fi : rmlist){
				Food f = baseDao.find(Food.class, fi.getFoodId());
				List<FoodBlacklist> bllist = baseDao.findByCriteria(FoodBlacklist.class, 
												new String[] {"userProfile", "food"}, 
												new Object[] {up, f});
				baseDao.delete(bllist);
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
