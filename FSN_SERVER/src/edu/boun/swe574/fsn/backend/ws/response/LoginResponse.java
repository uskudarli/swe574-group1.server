package edu.boun.swe574.fsn.backend.ws.response;


public class LoginResponse extends BaseServiceResponse {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
