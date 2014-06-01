package edu.boun.swe574.fsn.backend.ws;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.persistence.RollbackException;

import org.eclipse.persistence.exceptions.DatabaseException;

import edu.boun.swe574.fsn.backend.db.dao.BaseDao;
import edu.boun.swe574.fsn.backend.db.dao.DaoFactory;
import edu.boun.swe574.fsn.backend.db.model.AccessToken;
import edu.boun.swe574.fsn.backend.db.model.User;
import edu.boun.swe574.fsn.backend.db.model.UserProfile;
import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.response.LoginResponse;
import edu.boun.swe574.fsn.backend.ws.util.KeyValuePair;
import edu.boun.swe574.fsn.backend.ws.util.ResultCode;
import edu.boun.swe574.fsn.backend.ws.util.ServiceErrorCode;
import edu.boun.swe574.fsn.backend.ws.util.StringUtil;

@WebService(name="AuthService", serviceName="AuthService")
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public class AuthService {
	
	/**
	 * @param email
	 * @param name
	 * @param surname
	 * @param password
	 * @return
	 */
	@WebMethod
	public BaseServiceResponse register(@WebParam(name="email") 	String email,
						@WebParam(name="name") 		String name,
						@WebParam(name="surname") 	String surname,
						@WebParam(name="password")	String password){
		
		BaseServiceResponse response = new BaseServiceResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
        // Check if mandatory params are missing
        // WARN: No validations on these fields!
		if(!StringUtil.hasText(email) ||
				!StringUtil.hasText(name) ||
				!StringUtil.hasText(password) ||
				!StringUtil.hasText(surname) ){
			  
            response.setErrorCode(ServiceErrorCode.MISSING_PARAM.getCode());
            response.setResultCode(ResultCode.FAILURE.getCode());
            
            return response;
		}

        try {
        		//TODO: Handle name and surname
                User user = new User();
                user.setCreatedAt(new Date());
                user.setEmail(email);
                user.setPasswordMd5(password);
                user.setName(name);
                user.setSurname(surname);
                
                baseDao.save(user);
                
                UserProfile up = new UserProfile();
                up.setUser(user);
                
                baseDao.save(up);
                
                response.succeed();
        }
        catch(RollbackException rbe){

                if(rbe.getCause().getClass() == DatabaseException.class){
                        DatabaseException dbe = (DatabaseException)rbe.getCause();

                        // MySQL Err#1062 - "duplicate entry for key" error handled
                        if (dbe.getDatabaseErrorCode() == 1062) {
                                response.setErrorCode(ServiceErrorCode.EMAIL_IN_USE.getCode());
                        }
                        // unhandled type of database exception
                        else {
                                response.setErrorCode(ServiceErrorCode.INTERNAL_SERVER_ERROR.getCode());
                        }
                };
                response.setResultCode(ResultCode.FAILURE.getCode());
        }
        // Entirely unexpected type of exception
        catch(Exception e){
                e.printStackTrace();
                response.setErrorCode(ServiceErrorCode.INTERNAL_SERVER_ERROR.getCode());
                response.setResultCode(ResultCode.FAILURE.getCode());
        }

		return response;
	}
	
	
	@SuppressWarnings("unchecked")
	@WebMethod
	public LoginResponse login(	@WebParam(name="email") 		String email,
					 			@WebParam(name="password")		String password){
		
		LoginResponse response = new LoginResponse();
		BaseDao baseDao = DaoFactory.getInstance().getBaseDao();
		
		if(!StringUtil.hasText(email) || !StringUtil.hasText(password)){
			response.setResultCode(ResultCode.FAILURE.getCode());
			response.setErrorCode(ServiceErrorCode.MISSING_PARAM.getCode());
			return response;
		}
		
		// Get a user object for the e-mail
		KeyValuePair<String, Object> queryParams = new KeyValuePair<String,Object>("email", email); 
		User user = baseDao.executeNamedQuery("User.findByEmail", queryParams);
		
		// if no matching user or passwd doesn't match, fail
		if(user == null || !user.getPasswordMd5().equals(password)){
			response.setErrorCode(ServiceErrorCode.INVALID_EMAIL_PWD.getCode());
			response.setResultCode(ResultCode.FAILURE.getCode());
			return response;
		}
		
		try {
			
			// get old tokens belonging to user
			KeyValuePair<String, Object> atQueryParams = new KeyValuePair<String, Object>("uid", user.getId()); 
			List<AccessToken> oldTokenList = baseDao.executeNamedQueryGetList("AccessToken.getUserTokens", atQueryParams);
			
			// delete old tokens
			for (AccessToken oldToken : oldTokenList) {
				baseDao.delete(oldToken);
			}
			
			AccessToken token = new AccessToken();
			token.setCreatedAt(new Date());
			token.setUser(user);
			String hash = StringUtil.toMD5(user.getEmail() + System.currentTimeMillis());
			token.setMd5Token(hash);
			// expiry date null: never expire
			
			baseDao.save(token);
			
			response.setToken(token.getMd5Token());
			response.setName(user.getName());
			response.setSurname(user.getSurname());

		} 
		catch(Exception e){
			e.printStackTrace();
			response.setErrorCode(ServiceErrorCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResultCode(ResultCode.FAILURE.getCode());
			return response;
		}
		
		response.setResultCode(ResultCode.SUCCESS.getCode());
		return response;
	}
	
	
}
