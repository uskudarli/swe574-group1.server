package edu.boun.swe574.fsn.backend.ws.response;

import edu.boun.swe574.fsn.backend.ws.util.ResultCode;
import edu.boun.swe574.fsn.backend.ws.util.ServiceErrorCode;



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
	
	public void fail(ServiceErrorCode ecode){
		this.resultCode = ResultCode.FAILURE.getCode();
		this.errorCode = ecode.getCode();
	}
	
	public void succeed(){
		this.resultCode = ResultCode.SUCCESS.getCode();
		this.errorCode = ServiceErrorCode.SUCCESS.getCode();
	}
	
}

