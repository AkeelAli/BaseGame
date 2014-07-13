package com.example.basegame;

import java.util.ArrayList;
import java.util.Random;

public class Number {
	public static final int MAX_NUMBER = 15;
	public static final int MIN_NUMBER = 1;
	
	private int mNumber;
	
	public Number(int number) {
		/* TODO add restriction for +ve integer */
		mNumber = number;
	}
	
	/* Generate Random Number */
	public Number() {
		Random rand = new Random();
		mNumber = rand.nextInt(MAX_NUMBER) + MIN_NUMBER;
	}
	
	/* Generate Random Number outside exclusions */
	public Number(ArrayList<Number> exclusions) {
		int randInt;
		boolean excluded = false;
		Random rand = new Random();
		
		do {
			excluded = false;
			randInt = rand.nextInt(MAX_NUMBER) + MIN_NUMBER;
			
			for (int i = 0; i < exclusions.size(); ++i) {
				if (exclusions.get(i).getValue() == randInt) {
					excluded = true;
					break;
				}
			}
		
		} while (excluded);
		
		mNumber = randInt;
	}
	
	public int getValue() {
		return mNumber;
	}
	
	public boolean equals(Number num) {
		return (num.getValue() == this.getValue());
	}
}
