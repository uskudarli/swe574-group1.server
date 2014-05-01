package edu.boun.swe574.fsn.backend.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService(name="AuthService", serviceName="AuthService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class AuthService {
	
	/**
	 * @param email
	 * @param name
	 * @param surname
	 * @param password
	 * @return
	 */
	@WebMethod
	public int register(@WebParam(name="email") 	String email,
						@WebParam(name="name") 		String name,
						@WebParam(name="surname") 	String surname,
						@WebParam(name="password")	String password){
		
		return 0;
	}
	
	
	@WebMethod
	public int login(@WebParam(name="email") 		String email,
					 @WebParam(name="password")		String password){
		
		return 0;
	}
	
	
}
