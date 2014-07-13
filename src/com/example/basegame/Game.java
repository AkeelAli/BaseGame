package com.example.basegame;

public class Game {
	private int mNumChallenges;
	private Challenge mChallenges[];
	private GameMode mMode;
	
	public enum GameMode {
		BINARY_DECIMAL,
		HEX_DECIMAL,
		BINARY_HEX,
		MIXED,
	}
	
	public Game(Challenge challenges[], int numChallenges, GameMode mode) {
		mChallenges = challenges;
		mNumChallenges = numChallenges;
		mMode = mode;
	}
	
	/* Generate random Game */
	public Game(int numChallenges, GameMode mode) {
		mMode = mode;
		mNumChallenges = numChallenges;
		mChallenges = new Challenge[numChallenges];
		
		for (int i = 0; i < numChallenges; ++i) {
			mChallenges[i] = new Challenge();
		}
		
	}
	
}
