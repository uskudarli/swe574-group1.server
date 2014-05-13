package edu.boun.swe574.fsn.backend.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "User")
@Table(name = "User")
@NamedQueries({
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByName" , query = "SELECT u FROM User u WHERE u.name LIKE :name OR u.surname LIKE :name")
})
public class User extends BaseModel {
	
	private static final long serialVersionUID = 7112342412500250571L;
	
	@Column(name="CREATED_AT")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name="EMAIL",length=45)
	private String email;


	@Column(name="NAME",length=45)
	private String name;
	
	@Column(name="SURNAME",length=45)
	private String surname;
	
	@Column(name="PASSWORD_MD5", length=128)
	private String passwordMd5;

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
