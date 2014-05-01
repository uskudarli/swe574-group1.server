package edu.boun.swe574.fsn.backend.test.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;




import edu.boun.swe574.fsn.common.client.auth.AuthService;
import edu.boun.swe574.fsn.common.client.auth.AuthService_Service;
import edu.boun.swe574.fsn.common.client.auth.BaseServiceResponse;

public class CallAuthWS {


	public static void main(String[] args) {
		URL url;
		try { 
			url = new URL("http://swe.cmpe.boun.edu.tr:8080/FSN_SERVER/fsnws_auth?wsdl");
			AuthService_Service service = new AuthService_Service(url, new QName("http://ws.backend.fsn.swe574.boun.edu/", "AuthService"));
			AuthService authServicePort = service.getAuthServicePort();
			
			BaseServiceResponse response = authServicePort.register("email", "name", "surname", "password");
			
			System.out.println("response recived: resultCode=" + response.getResultCode() + " errorCode=" + response.getErrorCode());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
