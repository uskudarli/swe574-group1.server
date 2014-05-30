package edu.boun.swe574.fsn.backend.ws.response.info;

import java.util.Date;

import edu.boun.swe574.fsn.backend.db.model.Recipe;

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
	
	public static RevisionInfo mapRecipe(Recipe r){
		
		RevisionInfo revInfo = new RevisionInfo();
		revInfo.setCurrentRecipeId(r.getId());
		//fix for Issue 52
		if(r.getParentRecipe() != null) {
			revInfo.setParentRecipeId(r.getParentRecipe().getId());
		}
		revInfo.setRevisionDate(r.getDate());
		revInfo.setRevisionNote(r.getVersionNote());
		revInfo.setUser(UserInfo.mapUser(r.getUser()));
		return revInfo;
		
	}
	
}
