package edu.boun.swe574.fsn.backend.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Food")
@Table(name = "Food")
public class Food extends BaseModel {

	private static final long serialVersionUID = 6353698582500250571L;
	
	@Column(name = "NAME", length = 100)
	private String name;
	
	@Column(name = "DESCRIPTION", length= 250)
	private String description;
	
	@Column(name = "IS_USABLE")
	private Boolean isUsable;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOODID", nullable = true)
	private Food parent;
	
	// Getters & Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsUsable() {
		return isUsable;
	}

	public void setIsUsable(Boolean isUsable) {
		this.isUsable = isUsable;
	}
	
}
