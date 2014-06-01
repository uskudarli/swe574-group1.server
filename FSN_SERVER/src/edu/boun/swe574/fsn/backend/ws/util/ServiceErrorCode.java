package edu.boun.swe574.fsn.backend.ws.util;

/**
	101	Missing mandatory parameter
	102	Email is already in use
	103	Invalid email or password
	104	Invalid token
	105	Token expired
	106	Wrong date format
	107	User not found
	108	Recipe not found
	109	Rate value is out of the range
	110 Food already blacklisted by user
	500	Internal server error
*/
public enum ServiceErrorCode {

	SUCCESS					(0),
	MISSING_PARAM 			(101),
	EMAIL_IN_USE			(102),
	INVALID_EMAIL_PWD		(103),
	TOKEN_INVALID			(104),
	TOKEN_EXPIRED			(105),
	WRONG_DATE_FORMAT		(106),
	USER_NOT_FOUND			(107),
	RECIPE_NOT_FOUND		(108),
	RATE_VALUE_INVALID		(109),
	ALREADY_BLACKLISTED 	(110),
	INTERNAL_SERVER_ERROR 	(500)
	;
	
	
	private final int code;
	ServiceErrorCode(int code){
		this.code = code;
	}
	public int getCode(){
		return this.code;
	}
	
}
