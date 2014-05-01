package edu.boun.swe574.fsn.backend.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetProfileResponse;
import edu.boun.swe574.fsn.backend.ws.response.GetRecipeFeedsResponse;
import edu.boun.swe574.fsn.backend.ws.response.SearchForUsersResponse;
import edu.boun.swe574.fsn.backend.ws.response.info.UserInfo;

@WebService(name="NetworkService", serviceName="NetworkService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class NetworkService {

	@WebMethod
	public GetProfileResponse getProfileOfSelf(@WebParam(name="token") String token){
		
		return new GetProfileResponse();
	}
	
	@WebMethod
	public BaseServiceResponse editProfile(	@WebParam(name="token") 			String token, 
							@WebParam(name="location") 			String location, 
							@WebParam(name="dateOfBirth")		String dateOfBirth, 
							@WebParam(name="profileMessage")	String profileMessage ){ 
//							Ingredient[] ingredientBlackList, 
//							Byte[] image){
		
		return new BaseServiceResponse();
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
	
	@WebMethod
	public BaseServiceResponse follow(	@WebParam(name="token")		String token, 
						@WebParam(name="email")		String email){
		return new BaseServiceResponse();
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
