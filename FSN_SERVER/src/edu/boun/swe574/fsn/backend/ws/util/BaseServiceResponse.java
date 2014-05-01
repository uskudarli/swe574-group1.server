package edu.boun.swe574.fsn.backend.ws.util;


public abstract class BaseServiceResponse {
	private int ResponseCode;

	public int getResponseCode() {
		return ResponseCode;
	}

	public void setResponseCode(ServiceErrorCode responseCode) {
		ResponseCode = responseCode.getCode();
	}
}

