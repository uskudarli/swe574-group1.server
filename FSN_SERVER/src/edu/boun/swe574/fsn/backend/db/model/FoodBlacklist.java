package edu.boun.swe574.fsn.backend.db.model;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity(name = "FoodBlacklist")
@Table(name = "FoodBlacklist",
	   uniqueConstraints=@UniqueConstraint(columnNames={"FOOD_ID", "USER_PROFILE_ID"}))
public class FoodBlacklist extends BaseModel {

	private static final long serialVersionUID = 7123412412500250571L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOOD_ID")
	private Food food;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_PROFILE_ID")
	private UserProfile userProfile;
	
	
	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

}
