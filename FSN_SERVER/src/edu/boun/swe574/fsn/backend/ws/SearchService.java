package edu.boun.swe574.fsn.backend.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import edu.boun.swe574.fsn.backend.ws.response.GetRecipeFeedsResponse;

@WebService(name="SearchService", serviceName="SearchService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class SearchService {
	
	@WebMethod
	public GetRecipeFeedsResponse searchForRecipes(	@WebParam(name="token") 						String token, 
									@WebParam(name="listOfPreferredIngredients")	long[] listOfPreferredIngredients){
		return new GetRecipeFeedsResponse();
	}
	
}
