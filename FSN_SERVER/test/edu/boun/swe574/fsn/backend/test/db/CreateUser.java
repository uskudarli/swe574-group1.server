package edu.boun.swe574.fsn.backend.test.db;

import java.util.Date;

import edu.boun.swe574.fsn.backend.db.dao.DaoFactory;
import edu.boun.swe574.fsn.backend.db.model.User;

public class CreateUser {
	
	public static void main(String[] args) {
		User user = new User();
		user.setCreatedAt(new Date());
		user.setEmail("bla@bla.com");
		user.setPasswordMd5("password");
		
		DaoFactory.getInstance().getBaseDao().save(user);
		System.out.println("user saved");
	}

}
