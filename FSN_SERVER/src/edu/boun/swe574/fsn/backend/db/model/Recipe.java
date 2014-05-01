package edu.boun.swe574.fsn.backend.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Recipe extends BaseModel {

	private static final long serialVersionUID = 9012412931235002571L;
	
	@Column(name="TITLE", length=150)
	private String title;
	
	@Column(name="DIRECTIONS", length=800)
	private String directions;
	
	@Column(name="DATE")
	private Date date;
	
	@Column(name="VERSION_NOTE", length=200)
	private String versionNote;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_RECIPE_ID", nullable=true)
	private Recipe parentRecipe;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getVersionNote() {
		return versionNote;
	}

	public void setVersionNote(String versionNote) {
		this.versionNote = versionNote;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Recipe getParentRecipe() {
		return parentRecipe;
	}

	public void setParentRecipe(Recipe parentRecipe) {
		this.parentRecipe = parentRecipe;
	}
}
