package edu.boun.swe574.fsn.backend.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService(name="AuthService", serviceName="AuthService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class SearchService {
	
	@WebMethod
	public int searchForRecipes(	@WebParam(name="token") 						String token, 
									@WebParam(name="listOfPreferredIngredients")	long[] listOfPreferredIngredients){
		return 0;
	}
	
}
