package edu.boun.swe574.fsn.backend.ws.response;

import java.util.Date;
import java.util.List;

import edu.boun.swe574.fsn.backend.db.model.UserProfile;
import edu.boun.swe574.fsn.backend.ws.response.info.FoodInfo;

public class GetProfileResponse extends BaseServiceResponse {
	
	private String location;
	private String name;
	private String surname;
	private Date dateOfBirth;
	private String profileMessage;
	private byte[] image;
	private List<FoodInfo> foodBlackList;
	private boolean isFollowed;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getProfileMessage() {
		return profileMessage;
	}
	public void setProfileMessage(String profileMessage) {
		this.profileMessage = profileMessage;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public List<FoodInfo> getIngredientBlackList() {
		return this.foodBlackList;
	}
	public void setIngredientBlackList(List<FoodInfo> ingredientBlackList) {
		this.foodBlackList = ingredientBlackList;
	}
	
	public void mapUserProfile(UserProfile up){
		this.dateOfBirth = up.getDateOfBirth();
		this.location = up.getLocation();
		this.name = up.getUser().getName();
		this.surname = up.getUser().getSurname();
		this.foodBlackList = null;
		this.image = up.getImage();
		this.profileMessage = up.getProfileMessage();
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
	public boolean isFollowed() {
		return isFollowed;
	}
	public void setFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}

	
}
