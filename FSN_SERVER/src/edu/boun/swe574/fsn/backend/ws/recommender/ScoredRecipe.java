package edu.boun.swe574.fsn.backend.ws.recommender;

import edu.boun.swe574.fsn.backend.db.model.Recipe;

public class ScoredRecipe implements Comparable<ScoredRecipe>{
	
	private Recipe recipe;
	private double score;
	
	public ScoredRecipe(Recipe r, double score){
		this.recipe = r;
		this.score = score;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public int compareTo(ScoredRecipe o) {
		return o.getScore() >= this.getScore() ? 1 : -1;
	}
	
	
}
