package edu.boun.swe574.fsn.backend.ws;

import java.util.Calendar;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.response.LoginResponse;
import edu.boun.swe574.fsn.backend.ws.util.ResultCode;

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
	public BaseServiceResponse register(@WebParam(name="email") 	String email,
						@WebParam(name="name") 		String name,
						@WebParam(name="surname") 	String surname,
						@WebParam(name="password")	String password){
		
		BaseServiceResponse response = new BaseServiceResponse();
		response.setResultCode(ResultCode.SUCCESS.getCode());
		return response;
	}
	
	
	@WebMethod
	public LoginResponse login(@WebParam(name="email") 		String email,
					 @WebParam(name="password")		String password){
		
		LoginResponse response = new LoginResponse();
		response.setResultCode(ResultCode.SUCCESS.getCode());
		response.setToken(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		return response;
	}
	
	
}
