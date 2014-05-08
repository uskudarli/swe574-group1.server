package edu.boun.swe574.fsn.backend.ws;

import java.util.Date;

import edu.boun.swe574.fsn.backend.db.dao.BaseDao;
import edu.boun.swe574.fsn.backend.db.dao.DaoFactory;
import edu.boun.swe574.fsn.backend.db.model.AccessToken;
import edu.boun.swe574.fsn.backend.db.model.User;
import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.util.KeyValuePair;
import edu.boun.swe574.fsn.backend.ws.util.ServiceErrorCode;

public class ServiceCommons {

	@SuppressWarnings("unchecked")
	public static User authenticate(String token, BaseServiceResponse response){
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		KeyValuePair<String, Object> qParams = new KeyValuePair<String,Object>("token", token);
		AccessToken aToken = baseDao.executeNamedQuery("AccessToken.getTokenByHash", qParams);
		
		// if no token is returned - invalid token, die
		if (aToken==null){
			response.fail(ServiceErrorCode.TOKEN_INVALID);
			return null;
		}
		
		// if token expired - die
		if (aToken.getExpiresAt()!=null && aToken.getExpiresAt().after(new Date())){
			response.fail(ServiceErrorCode.TOKEN_EXPIRED);
			return null;
		}
		
		return aToken.getUser();
	}
}
