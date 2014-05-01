package edu.boun.swe574.fsn.backend.db.model;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity(name = "FoodBlacklist")
@Table(name = "FoodBlacklist")
public class FoodBlacklist extends BaseModel {

	private static final long serialVersionUID = 7123412412500250571L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOOD_ID")
	private Food food;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_PROFILE_ID")
	private UserProfile userProfile;
	
}
