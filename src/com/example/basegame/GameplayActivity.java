package com.example.basegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.example.basegame.Game.ChallengeIterator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameplayActivity extends Activity {

	private Game mGame;
	private Game.ChallengeIterator mChallengeIter;
	private Game.GameMode mGameMode;
	private CountDownTimer mTimer;
	
	private TextView mFromField;
	private Button mChoiceButtons[];
	private TextView mScore;
	private TextView mTime;
	private TextView mChallenge;
	
	/* CONSTANTS */
	/* TODO define and use xml colors with default gray color for buttons instead of reseting every challenge */
	private final static int BUTTON_COLOR = Color.GRAY;
	private final static int BUTTON_CORRECT_COLOR = Color.GREEN;
	private final static int BUTTON_INCORRECT_COLOR = Color.RED;
	
	private final static int NUM_CHALLENGES_PER_GAME = 8;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplay);
		
		/* Determine GameMode */
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			/* Assume default game mode = mixed */
			mGameMode = Game.GameMode.MIXED;
		} else {
			String gameModeStr = extras.getString("GameMode");
			mGameMode = Game.GameMode.valueOf(gameModeStr);
		}
		
		mFromField = (TextView) findViewById(R.id.from_field);
		mChoiceButtons = new Button[Challenge.CHOICES_PER_CHALLENGE];
		mChoiceButtons[0] = (Button) findViewById(R.id.choice_1);
		mChoiceButtons[1] = (Button) findViewById(R.id.choice_2);
		mChoiceButtons[2] = (Button) findViewById(R.id.choice_3);
		mChoiceButtons[3] = (Button) findViewById(R.id.choice_4);
		mChallenge = (TextView) findViewById(R.id.challenge_field);
		mScore = (TextView) findViewById(R.id.score_field);
		mTime = (TextView) findViewById(R.id.time_field);
		
		mGame = new Game(NUM_CHALLENGES_PER_GAME, mGameMode);
		
		playGame(mGame);
	}
	
	private void playGame(Game game) {
		mChallengeIter = (ChallengeIterator) game.iterator();
		
		redraw();
		
		startTimer();
	}
	
	private void startTimer() {
		mTimer = new CountDownTimer(Game.TIMER_TOTAL_TIME_MS, Game.TIMER_INTERVAL_BETWEEN_TICKS_MS) {

			@Override
			public void onTick(long millisUntilFinished) {
				setAndDrawTimeBonus(millisUntilFinished);
			}

			@Override
			public void onFinish() {
				setAndDrawTimeBonus(0);
				gameDone(true);
			}
			
		}.start();
	}

	
	private void redraw() {
		drawScore();
		
		if (mChallengeIter.hasNext()) {
			drawChallenge(mChallengeIter.next());
		} else {
			gameDone(false);
		}
	}
	
	private void gameDone(boolean timesUp) {
		mTimer.cancel();
		
		if (timesUp)
			Log.i("GAMEPLAY", "Time's up! Game Over!");
		else
			Log.i("GAMEPLAY", "Game Done Successfully!");
		
		mFromField.setVisibility(TextView.INVISIBLE);
		for (int i = 0; i < Challenge.CHOICES_PER_CHALLENGE; i++)
			mChoiceButtons[i].setVisibility(Button.INVISIBLE);
	}
	
	private void setAndDrawTimeBonus(long timeBonus) {
		mGame.setTimeBonus(timeBonus);
		mTime.setText(String.valueOf(mGame.getTimeBonus()));
	}
	
	private void drawScore() {
		mScore.setText(String.valueOf(mGame.getGameScore()));
	}
	
	private void drawChallenge(Challenge challenge) {
		Random rand = new Random();
		int correctChoiceButtonIndex = rand.nextInt(Challenge.CHOICES_PER_CHALLENGE);
		Number num;
		Number.Base fromToBases[] = new Number.Base[2];
		
		/* Get Bases for this challenge (from base and to base) */
		getBases(mGame.getGameMode(), fromToBases);
		
		/* Setup Challenge number */
		mChallenge.setText(mChallengeIter.nextIndex() + "/" + mGame.getNumChallenges());
		
		/* Setup From Field */
		num = challenge.getCorrectNumber();
		mFromField.setText(num.display(fromToBases[0]));
		
		/* Setup Correct Choice Button */
		mChoiceButtons[correctChoiceButtonIndex].setBackgroundColor(BUTTON_COLOR);
		mChoiceButtons[correctChoiceButtonIndex].setText(num.display(fromToBases[1]));
		mChoiceButtons[correctChoiceButtonIndex].setTag(num);
		mChoiceButtons[correctChoiceButtonIndex].setOnClickListener(new ChoiceClickListener());
		
		/* Setup Wrong Choice Buttons */
		int i = 0;
		for (int wrongChoiceButtonIndex = 0; wrongChoiceButtonIndex < Challenge.CHOICES_PER_CHALLENGE; ++wrongChoiceButtonIndex) {
			if (wrongChoiceButtonIndex == correctChoiceButtonIndex)
				continue;
			
			num = challenge.getWrongNumbers()[i];
			
			mChoiceButtons[wrongChoiceButtonIndex].setBackgroundColor(BUTTON_COLOR);
			mChoiceButtons[wrongChoiceButtonIndex].setText(num.display(fromToBases[1]));
			mChoiceButtons[wrongChoiceButtonIndex].setTag(num);
			mChoiceButtons[wrongChoiceButtonIndex].setOnClickListener(new ChoiceClickListener());
			i++;
		}
	
	}
	
	private void getBases(Game.GameMode gameMode, Number.Base fromToBases[]) {
		ArrayList<Number.Base> possibleBases = new ArrayList<Number.Base>();
		Random rand = new Random();
		
		switch(gameMode) {
		case BINARY_DECIMAL:
			possibleBases.add(Number.Base.BINARY);
			possibleBases.add(Number.Base.DECIMAL);
			break;
			
		case HEX_DECIMAL:
			possibleBases.add(Number.Base.HEXADECIMAL);
			possibleBases.add(Number.Base.DECIMAL);
			break;
			
		case BINARY_HEX:
			possibleBases.add(Number.Base.BINARY);
			possibleBases.add(Number.Base.HEXADECIMAL);	
			break;
			
		case MIXED:
		default:
			possibleBases.add(Number.Base.BINARY);
			possibleBases.add(Number.Base.HEXADECIMAL);
			possibleBases.add(Number.Base.DECIMAL);
			break;
		}
		
		fromToBases[0] = possibleBases.remove(rand.nextInt(possibleBases.size()));
		fromToBases[1] = possibleBases.remove(rand.nextInt(possibleBases.size()));
	}
	
	public class ChoiceClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			final Button buttonClicked = (Button) v;
			Number numClicked = (Number) buttonClicked.getTag();
			Challenge currentChallenge = mChallengeIter.current();
			boolean correct = currentChallenge.isCorrectAnswer(numClicked);
			
			if (correct) {
				buttonClicked.setBackgroundColor(BUTTON_CORRECT_COLOR);
				mGame.addPoint();
			} else {
				buttonClicked.setBackgroundColor(BUTTON_INCORRECT_COLOR);
			}
			
		    // Sleep for a moment to hold the button color, then move to next challenge
		    Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		        	 buttonClicked.setBackgroundColor(BUTTON_COLOR);
		        	 redraw();
		         } 
		    }, 200 /* Sleep 0.2 seconds */);
			
		}
	}
}
