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
import edu.boun.swe574.fsn.backend.db.model.User;
import edu.boun.swe574.fsn.backend.db.model.UserFollowLink;
import edu.boun.swe574.fsn.backend.db.model.UserProfile;
import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetProfileResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetRecipeFeedsResponse;
import edu.boun.swe574.fsn.backend.ws.response.SearchForUsersResponse;
import edu.boun.swe574.fsn.backend.ws.response.info.UserInfo;
import edu.boun.swe574.fsn.backend.ws.util.KeyValuePair;
import edu.boun.swe574.fsn.backend.ws.util.ServiceErrorCode;

@WebService(name="NetworkService", serviceName="NetworkService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class NetworkService {

	@SuppressWarnings("unchecked")
	@WebMethod
	public GetProfileResponse getProfileOfSelf(@WebParam(name="token") String token){
		
		GetProfileResponse response = new GetProfileResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		// TODO: Code review: passing response (out) variable to the authenticate method
		// there may be a neater way of doing this?
		User user = ServiceCommons.authenticate(token, response);
		
		if(user==null){
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
	
	@SuppressWarnings("unchecked")
	@WebMethod
	public BaseServiceResponse editProfile(	@WebParam(name="token") 			String token, 
											@WebParam(name="location") 			String location, 
											@WebParam(name="dateOfBirth")		String dateOfBirth, 
											@WebParam(name="profileMessage")	String profileMessage ){
		
		BaseServiceResponse response = new BaseServiceResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		// authenticate token
		User user = ServiceCommons.authenticate(token, response);
		if (user==null){
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
	
	@WebMethod
	public GetProfileResponse getProfileOfOtherUser(	@WebParam(name="token") 	String token, 
										@WebParam(name="email")		String email){
		return new GetProfileResponse();
	}
	
	@SuppressWarnings("unchecked")
	@WebMethod
	public BaseServiceResponse follow(	@WebParam(name="token")		String token, 
										@WebParam(name="email")		String email){
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		BaseServiceResponse response= new BaseServiceResponse();
		
		User user = ServiceCommons.authenticate(token, response);
		if (user == null){
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
	
	@WebMethod
	public SearchForUsersResponse getFollowedUser(	@WebParam(name="token")	String token){
		SearchForUsersResponse response = new SearchForUsersResponse();
		
		UserInfo user = new UserInfo();
		user.setName("Marcelino");
		user.setSurname("Sanchez");
		user.setEmail("marcelino@sanchez.com");
		user.setUserId(1L);
		
		List<UserInfo> userList = new ArrayList<UserInfo>();
		response.setUserList(userList);
		
		return response;
	}
	
	@WebMethod
	public GetRecipeFeedsResponse getRecipeFeeds(	@WebParam(name="token")		String token, 
								@WebParam(name="index")		Integer index, 
								@WebParam(name="pageSize")	Integer pageSize){
		return new GetRecipeFeedsResponse();
	}
	
}
