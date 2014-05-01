package edu.boun.swe574.fsn.backend.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService(name="AuthService", serviceName="AuthService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class NetworkService {

	@WebMethod
	public int getProfileOfSelf(@WebParam(name="token") String token){
		return 0;
	}
	
	@WebMethod
	public int editProfile(	@WebParam(name="token") 			String token, 
							@WebParam(name="location") 			String location, 
							@WebParam(name="dateOfBirth")		String dateOfBirth, 
							@WebParam(name="profileMessage")	String profileMessage ){ 
//							Ingredient[] ingredientBlackList, 
//							Byte[] image){
		return 0;
	}
	
	@WebMethod
	public int searchForUsers(@WebParam(name="token")			String token, 
							  @WebParam(name="queryString") 	String queryString) {
		return 0;
	}
	
	@WebMethod
	public int getProfileOfOtherUser(	@WebParam(name="token") 	String token, 
										@WebParam(name="email")		String email){
		return 0;
	}
	
	@WebMethod
	public int follow(	@WebParam(name="token")		String token, 
						@WebParam(name="email")		String email){
		return 0;
	}
	
	@WebMethod
	public int getFollowedUser(	@WebParam(name="token")	String token){
		return 0;
	}
	
	@WebMethod
	public int getRecipeFeeds(	@WebParam(name="token")		String token, 
								@WebParam(name="index")		Integer index, 
								@WebParam(name="pageSize")	Integer pageSize){
		return 0;
	}
	
}
