package edu.boun.swe574.fsn.backend.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "AccessToken")
@Table(name = "AccessToken")
@NamedQueries({
    @NamedQuery(name = "AccessToken.getUserTokens", query = "SELECT at FROM AccessToken at WHERE at.user.id = :uid"),
    @NamedQuery(name = "AccessToken.getTokenByHash" , query = "SELECT at FROM AccessToken at WHERE at.md5Token = :token" )
})
public class AccessToken extends BaseModel {

	private static final long serialVersionUID = 58917120000571L;
	
	@Column(name="CREATED_AT")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name="EXPIRES_AT")
	@Temporal(TemporalType.DATE)
	private Date expiresAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
	private User user;
	
	@Column(name="MD5TOKEN", length=128)
	private String md5Token;

	public String getMd5Token() {
		return md5Token;
	}

	public void setMd5Token(String md5Token) {
		this.md5Token = md5Token;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
