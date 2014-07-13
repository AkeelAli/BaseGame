package com.example.basegame;

import java.util.ArrayList;
import java.util.Iterator;



public class Game implements Iterable<Challenge> {
	private int mNumChallenges;
	private ArrayList<Challenge> mChallenges;
	private GameMode mMode;	
	
	public enum GameMode {
		BINARY_DECIMAL,
		HEX_DECIMAL,
		BINARY_HEX,
		MIXED,
	}
	
	public Game(ArrayList<Challenge> challenges, int numChallenges, GameMode mode) {
		mChallenges = challenges;
		mNumChallenges = numChallenges;
		mMode = mode;
	}
	
	/* Generate random Game */
	public Game(int numChallenges, GameMode mode) {
		mMode = mode;
		mNumChallenges = numChallenges;
		mChallenges = new ArrayList<Challenge>(numChallenges);
		
		for (int i = 0; i < numChallenges; ++i) {
			mChallenges.add(new Challenge());
		}
		
	}
	
	public ArrayList<Challenge> getChallenges() {
		return mChallenges;
	}
	
	public GameMode getGameMode() {
		return mMode;
	}

	@Override
	public Iterator<Challenge> iterator() {
		return mChallenges.iterator();		
	}
	
}
