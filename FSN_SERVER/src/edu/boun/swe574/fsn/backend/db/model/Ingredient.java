package edu.boun.swe574.fsn.backend.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ingredient extends BaseModel {

	private static final long serialVersionUID = 635123582500250571L;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="UNIT", length=10)
	private String unit;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RECIPE_ID", nullable=true)
	private Recipe recipe;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FOOD_ID", nullable=false)
	private Food food;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
	
	
}
