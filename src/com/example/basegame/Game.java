package com.example.basegame;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.CountDownTimer;



public class Game implements Iterable<Challenge> {
	private int mNumChallenges;
	private ArrayList<Challenge> mChallenges;
	private GameMode mMode;	
	private int mCurrentChallengeIdx;
	private int mScore;
	private long mTimeBonus;
	
	/* CONSTANTS */
	public final static int TIMER_INTERVAL_BETWEEN_TICKS_MS = 100;
	public final static int TIMER_TOTAL_TIME_MS = 40000;
	public final static long INITIAL_TIME_BONUS = TIMER_TOTAL_TIME_MS;
	
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
		mTimeBonus = INITIAL_TIME_BONUS;
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
		mTimeBonus = INITIAL_TIME_BONUS;
	}
	
	public void setTimeBonus(long timeBonus) {
		mTimeBonus = timeBonus;
	}
	
	public long getTimeBonus() {
		return mTimeBonus;
	}
	
	public void addPoint() {
		mScore += 1;
	}
	
	public int getGameScore() {
		return mScore;
	}

	public GameMode getGameMode() {
		return mMode;
	}
	
	public int getNumChallenges() {
		return mNumChallenges;
	}
	
	@Override
	public Iterator<Challenge> iterator() {
		return new ChallengeIterator();
	}
	
	public class ChallengeIterator implements Iterator<Challenge> {
		@Override
		public boolean hasNext() {
			return (mCurrentChallengeIdx < mNumChallenges);
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
		
		public int nextIndex() {
			return (mCurrentChallengeIdx);
		}
	}
}
