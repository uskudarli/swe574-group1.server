package edu.boun.swe574.fsn.backend.db.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "UserFollowLink")
@Table(name = "UserFollowLink")
@Cacheable(false)
public class UserFollowLink extends BaseModel {

	private static final long serialVersionUID = 58962184200250571L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FOLLOWED_USER_ID",nullable=false)
	private User followedUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FOLLOWING_USER_ID",nullable=false)
	private User followingUser;

	public User getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(User followedUser) {
		this.followedUser = followedUser;
	}

	public User getFollowingUser() {
		return followingUser;
	}

	public void setFollowingUser(User followingUser) {
		this.followingUser = followingUser;
	}
	
	
}
