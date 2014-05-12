package edu.boun.swe574.fsn.backend.ws;

import java.util.Date;

import edu.boun.swe574.fsn.backend.db.dao.BaseDao;
import edu.boun.swe574.fsn.backend.db.dao.DaoFactory;
import edu.boun.swe574.fsn.backend.db.model.AccessToken;
import edu.boun.swe574.fsn.backend.db.model.User;
import edu.boun.swe574.fsn.backend.db.model.UserProfile;
import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.util.InvalidTokenException;
import edu.boun.swe574.fsn.backend.ws.util.KeyValuePair;
import edu.boun.swe574.fsn.backend.ws.util.TokenExpiredException;

public class ServiceCommons {

	@SuppressWarnings("unchecked")
	public static User authenticate(String token, BaseServiceResponse response) throws InvalidTokenException, TokenExpiredException{
		
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		KeyValuePair<String, Object> qParams = new KeyValuePair<String,Object>("token", token);
		AccessToken aToken = baseDao.executeNamedQuery("AccessToken.getTokenByHash", qParams);
		
		// if no token is returned - invalid token, die
		if (aToken==null){
			throw new InvalidTokenException();
		}
		
		// if token expired - die
		if (aToken.getExpiresAt()!=null && aToken.getExpiresAt().after(new Date())){
			throw new TokenExpiredException();
		}
		
		return aToken.getUser();
	}
	
	@SuppressWarnings("unchecked")
	public static UserProfile getProfile(User user){
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		try {
			KeyValuePair<String, Object> qParams = new KeyValuePair<String, Object>("uid", user.getId());
			UserProfile up = baseDao.executeNamedQuery("UserProfile.getUserProfile", qParams);
			return up;
		}
		catch(Exception e){
			return null;
		}
	}
	
}
