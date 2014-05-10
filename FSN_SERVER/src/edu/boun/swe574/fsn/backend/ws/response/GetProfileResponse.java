package edu.boun.swe574.fsn.backend.ws.response;

import java.util.Date;
import java.util.List;

import edu.boun.swe574.fsn.backend.db.model.UserProfile;
import edu.boun.swe574.fsn.backend.ws.response.info.IngredientInfo;

public class GetProfileResponse extends BaseServiceResponse {
	
	private String location;
	private Date dateOfBirth;
	private String profileMessage;
	private byte[] image;
	private List<IngredientInfo> ingredientBlackList;
	
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
	public List<IngredientInfo> getIngredientBlackList() {
		return ingredientBlackList;
	}
	public void setIngredientBlackList(List<IngredientInfo> ingredientBlackList) {
		this.ingredientBlackList = ingredientBlackList;
	}
	
	public void mapUserProfile(UserProfile up){
		this.dateOfBirth = up.getDateOfBirth();
		this.location = up.getLocation();
		
		// TODO: fill this with proper blacklist items
		this.ingredientBlackList = null;
		this.image = up.getImage();
		this.profileMessage = up.getProfileMessage();
	}

	
}
