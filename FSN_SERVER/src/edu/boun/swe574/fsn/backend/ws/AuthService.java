package edu.boun.swe574.fsn.backend.ws;

import java.util.Calendar;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.persistence.RollbackException;

import edu.boun.swe574.fsn.backend.ws.response.BaseServiceResponse;
import edu.boun.swe574.fsn.backend.ws.response.LoginResponse;
import edu.boun.swe574.fsn.backend.ws.util.ResultCode;
import edu.boun.swe574.fsn.backend.ws.util.ServiceErrorCode;
import edu.boun.swe574.fsn.backend.ws.util.StringUtil;
import edu.boun.swe574.fsn.backend.db.model.*;
import edu.boun.swe574.fsn.backend.db.dao.BaseDao;
import edu.boun.swe574.fsn.backend.db.dao.DaoFactory;

import org.eclipse.persistence.exceptions.DatabaseException;

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
		
		System.out.println(email);
		System.out.println(name);
		System.out.println(surname);
		System.out.println(password);
		
		// TODO: Throwing NullPointerException for name, surname, password. WHY?
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

                User user = new User();
                user.setCreatedAt(new Date());
                user.setEmail(email);
                user.setPasswordMd5(password);

                baseDao.save(user);

                response.setResultCode(ResultCode.SUCCESS.getCode());

                System.out.println("User save try block end hit");

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
	
	
	@WebMethod
	public LoginResponse login(@WebParam(name="email") 		String email,
					 @WebParam(name="password")		String password){
		
		LoginResponse response = new LoginResponse();
		response.setResultCode(ResultCode.SUCCESS.getCode());
		response.setToken(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		return response;
	}
	
	
}
