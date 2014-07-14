package com.example.basegame;

import java.util.Iterator;
import java.util.Random;

import com.example.basegame.Game.ChallengeIterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Gameplay extends Activity {

	private Game mGame;
	private Game.ChallengeIterator mChallengeIter;
	
	private TextView mFromField;
	private Button mChoiceButtons[];
	private TextView mScore;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplay);
		
		mFromField = (TextView) findViewById(R.id.from_field);
		/* TODO remove dependency on challenge here? */
		mChoiceButtons = new Button[Challenge.CHOICES_PER_CHALLENGE];
		mChoiceButtons[0] = (Button) findViewById(R.id.choice_1);
		mChoiceButtons[1] = (Button) findViewById(R.id.choice_2);
		mChoiceButtons[2] = (Button) findViewById(R.id.choice_3);
		mChoiceButtons[3] = (Button) findViewById(R.id.choice_4);
		
		mScore = (TextView) findViewById(R.id.score_field);
		
		/* TODO this is not yielding the right number of challenges + make this a CONSTANT */
		mGame = new Game(5, Game.GameMode.BINARY_DECIMAL);
		
		playGame(mGame);
	}
	
	private void playGame(Game game) {
		mChallengeIter = (ChallengeIterator) game.iterator();
		
		if (mChallengeIter.hasNext()) {
			setupChallenge(mChallengeIter.next());
		} else {
			/* Shouldn't get here: Game has no challenges */
		}
	}
	
	private void setupChallenge(Challenge challenge) {
		Random rand = new Random();
		int correctChoiceButtonIndex = rand.nextInt(Challenge.CHOICES_PER_CHALLENGE);
		Number num;
		/* TODO adjust these based on Game Mode */
		Number.Base fromBase = Number.Base.HEXADECIMAL;
		Number.Base toBase = Number.Base.BINARY;
		
		mScore.setText(String.valueOf(mGame.getGameScore()));
		
		num = challenge.getCorrectNumber();
		mFromField.setText(num.display(fromBase));
		
		mChoiceButtons[correctChoiceButtonIndex].setText(num.display(toBase));
		mChoiceButtons[correctChoiceButtonIndex].setTag(num);
		mChoiceButtons[correctChoiceButtonIndex].setOnClickListener(new ChoiceClickListener());
		
		int i = 0;
		for (int wrongChoiceButtonIndex = 0; wrongChoiceButtonIndex < Challenge.CHOICES_PER_CHALLENGE; ++wrongChoiceButtonIndex) {
			if (wrongChoiceButtonIndex == correctChoiceButtonIndex)
				continue;
			
			num = challenge.getWrongNumbers()[i];
			mChoiceButtons[wrongChoiceButtonIndex].setText(num.display(toBase));
			mChoiceButtons[wrongChoiceButtonIndex].setTag(num);
			mChoiceButtons[wrongChoiceButtonIndex].setOnClickListener(new ChoiceClickListener());
			i++;
		}
		

	}
	
	public class ChoiceClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			Button buttonClicked = (Button) v;
			String buttonText = buttonClicked.getText().toString();
			Number numClicked = (Number) buttonClicked.getTag();
			
			Challenge currentChallenge = mChallengeIter.current();
			if (currentChallenge.isCorrectAnswer(numClicked)) {
				Log.i("GAMEPLAY", buttonText + " is correct");
				mGame.setGameScore(mGame.getGameScore() + 1);
			} else {
				Log.i("GAMEPLAY", buttonText + " is incorrect");
			}
			
			if (mChallengeIter.hasNext()) {
				setupChallenge(mChallengeIter.next());
			} else {
				Log.i("GAMEPLAY", "Done");
				mFromField.setVisibility(TextView.INVISIBLE);
				for (int i = 0; i < Challenge.CHOICES_PER_CHALLENGE; i++)
					mChoiceButtons[i].setVisibility(Button.INVISIBLE);
			}
		
		}
	}
}
