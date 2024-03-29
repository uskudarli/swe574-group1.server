package edu.boun.swe574.fsn.backend.ws.response.info;

import edu.boun.swe574.fsn.backend.db.model.User;

public class UserInfo {
	
	private long userId;
	private String email;
	private String name;
	private String surname;
	private String profileMessage;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
	public static UserInfo mapUser(User u){
		UserInfo ui = new UserInfo();
		ui.setEmail(u.getEmail());
		ui.setName(u.getName());
		ui.setSurname(u.getSurname());
		ui.setUserId(u.getId());
		
		return ui;
	}
	public String getProfileMessage() {
		return profileMessage;
	}
	public void setProfileMessage(String profileMessage) {
		this.profileMessage = profileMessage;
	}

}
