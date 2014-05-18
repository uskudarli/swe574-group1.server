package edu.boun.swe574.fsn.backend.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "UserProfile")
@Table(name = "UserProfile")
@NamedQueries({
    @NamedQuery(name = "UserProfile.getUserProfile", query = "SELECT up FROM UserProfile up WHERE up.user.id = :uid")
})
public class UserProfile extends BaseModel{
	
	private static final long serialVersionUID = 6123123500250571L;

	@Column(name="LOCATION", length=150)
	private String location;
	
	@Column(name="DATE_OF_BIRTH")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name="PROFILE_MESSAGE", length=150)
	private String profileMessage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERID", nullable = false)
	private User user;
	
	@Column(name="IMAGE_DATA")
	@Lob
	private byte[] image;
	
//	Getters and setters
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
