package com.example.basegame;

import java.util.ArrayList;
import java.util.Random;

public class Number {
	public static final int MAX_NUMBER = 15;
	public static final int MIN_NUMBER = 1;
	
	private int mNumber;
	
	public enum Base {
		HEXADECIMAL,
		DECIMAL,
		BINARY,
		OCTAL,
	}
	
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
	
	@Override
	public boolean equals(Object num) {
		if (num instanceof Number)
			return (((Number)num).getValue() == this.getValue());
		else
			return false;
	}
	
	public String display(Base base) {
		String display;
		
		switch (base) {
		case DECIMAL:
			display = String.valueOf(mNumber);
			break;
		
		case HEXADECIMAL:
			display = String.format("0x%X", mNumber);
			break;
			
		case BINARY:
			display = (String.format("%4s", Integer.toBinaryString(mNumber)).replace(' ', '0'));
			break;
			
		case OCTAL:
			display = String.format("0o%3o", mNumber);
			break;
			
		default:
			display= "err";
			break;
		}
		
		return display;
	}
}
