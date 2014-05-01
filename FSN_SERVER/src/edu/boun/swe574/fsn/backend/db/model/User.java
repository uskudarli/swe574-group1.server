package edu.boun.swe574.fsn.backend.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "User")
@Table(name = "User")
public class User extends BaseModel {
	
	private static final long serialVersionUID = 7112342412500250571L;
	
	@Column(name="CREATED_AT")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name="EMAIL",length=45)
	private String email;
	
	@Column(name="PASSWORD_MD5", length=128)
	private String passwordMd5;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordMd5() {
		return passwordMd5;
	}

	public void setPasswordMd5(String passwordMd5) {
		this.passwordMd5 = passwordMd5;
	}
	
	
}
