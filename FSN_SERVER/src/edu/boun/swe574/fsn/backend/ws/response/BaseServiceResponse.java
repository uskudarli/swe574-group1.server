package edu.boun.swe574.fsn.backend.ws.response;



public class BaseServiceResponse {
	
	private Integer errorCode;
	private int resultCode;
	
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public Integer getResultCode() {
		return resultCode;
	}
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	
}

