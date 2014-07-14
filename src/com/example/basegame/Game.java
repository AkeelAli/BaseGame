package com.example.basegame;

import java.util.ArrayList;
import java.util.Iterator;



public class Game implements Iterable<Challenge> {
	private int mNumChallenges;
	private ArrayList<Challenge> mChallenges;
	private GameMode mMode;	
	private int mCurrentChallengeIdx;
	private int mScore;
	
	public enum GameMode {
		BINARY_DECIMAL,
		HEX_DECIMAL,
		BINARY_HEX,
		MIXED,
	}
	
	public Game(ArrayList<Challenge> challenges, int numChallenges, GameMode mode) {
		/* TODO make sure that challenges is not empty */
		mChallenges = challenges;
		mNumChallenges = numChallenges;
		mMode = mode;
		mCurrentChallengeIdx = 0;
		mScore = 0;
	}
	
	/* Generate random Game */
	public Game(int numChallenges, GameMode mode) {
		mMode = mode;
		mNumChallenges = numChallenges;
		mChallenges = new ArrayList<Challenge>(numChallenges);
		
		for (int i = 0; i < numChallenges; ++i) {
			mChallenges.add(new Challenge());
		}
		
		mCurrentChallengeIdx = 0;
		mScore = 0;
	}
	
	public int getGameScore() {
		return mScore;
	}
	
	public void setGameScore(int score) {
		mScore = score;
	}
		
	public GameMode getGameMode() {
		return mMode;
	}

	@Override
	public Iterator<Challenge> iterator() {
		return new ChallengeIterator();
	}
	
	public class ChallengeIterator implements Iterator<Challenge> {
		@Override
		public boolean hasNext() {
			return (mCurrentChallengeIdx < (mNumChallenges - 1));
		}

		@Override
		public Challenge next() {
			return (mChallenges.get(mCurrentChallengeIdx++));
		}

		@Override
		public void remove() {
			/* Do nothing */				
		}
		
		public Challenge current() {
			return (mChallenges.get(mCurrentChallengeIdx - 1));
		}
	}
}
