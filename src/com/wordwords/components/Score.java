package com.wordwords.components;

public class Score {

	private int score ;
	private int maxLimit ;
	public Score() {
		score = 0 ;
		maxLimit = 0 ;
	}
	
	public int getScore() {
		return score;
	}

	public int getMaxLimit() {
		return maxLimit;
	}
	public String getMessage() {
		return "Your score is : " + score + " / "+ maxLimit; 
	}
	
	
	public void setScore(int score) {
		this.score = score;
	}

	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}
}
