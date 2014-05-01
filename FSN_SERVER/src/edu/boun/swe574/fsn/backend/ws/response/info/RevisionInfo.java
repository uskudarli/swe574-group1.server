package edu.boun.swe574.fsn.backend.ws.response.info;

import java.util.Date;

public class RevisionInfo {
	
	private Date revisionDate;
	private UserInfo user;
	private String revisionNote;
	private long currentRecipeId;
	private long parentRecipeId;
	
	public Date getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public String getRevisionNote() {
		return revisionNote;
	}
	public void setRevisionNote(String revisionNote) {
		this.revisionNote = revisionNote;
	}
	public long getCurrentRecipeId() {
		return currentRecipeId;
	}
	public void setCurrentRecipeId(long currentRecipeId) {
		this.currentRecipeId = currentRecipeId;
	}
	public long getParentRecipeId() {
		return parentRecipeId;
	}
	public void setParentRecipeId(long parentRecipeId) {
		this.parentRecipeId = parentRecipeId;
	}
	
}
