package com.example.basegame;

import java.util.ArrayList;

public class Challenge {
	public static final int CHOICES_PER_CHALLENGE = 4;
	public static final int WRONG_CHOICES = CHOICES_PER_CHALLENGE - 1;
	
	private Number mCorrectNumber;
	private Number mWrongNumbers[] = new Number[WRONG_CHOICES];
	
	public Challenge(Number correct, Number wrong[]) {
		/* TODO ensure proper number of wrong Numbers supplied (i.e. WRONG_CHOICES) */
		mCorrectNumber = correct;
		for (int i = 0; i < WRONG_CHOICES; ++i) {
			mWrongNumbers[i] = wrong[i];
		}
	}
	
	/* Generate random Challenge */
	public Challenge() {
		ArrayList<Number> exclusions = new ArrayList<Number>();
		
		mCorrectNumber = new Number();
		exclusions.add(mCorrectNumber);
		
		for (int i = 0; i < WRONG_CHOICES; ++i) {
			mWrongNumbers[i] = new Number(exclusions);
			exclusions.add(mWrongNumbers[i]);
		}
	}
	
	public boolean isCorrectAnswer(Number answer) {
		return answer.equals(mCorrectNumber);
	}
	
	public Number getCorrectNumber() {
		return mCorrectNumber;
	}
	
	public Number[] getWrongNumbers() {
		return mWrongNumbers;
	}
	
}
