package com.example.basegame;

import java.util.Iterator;

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
	private Iterator<Challenge> mChallengeIter;
	
	private TextView mFromField;
	private Button mChoiceButtons[];
	
	
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
		
		mGame = new Game(5, Game.GameMode.BINARY_DECIMAL);
		
		playGame(mGame);
	}
	
	private void playGame(Game game) {
		mChallengeIter = game.iterator();
		
		if (mChallengeIter.hasNext()) 
			setupChallenge(mChallengeIter.next());
	}
	
	private void setupChallenge(Challenge challenge) {
		mFromField.setText(String.valueOf(challenge.getCorrectNumber().getValue()));
		
		for (int i = 0; i < Challenge.WRONG_CHOICES; ++i) {
			mChoiceButtons[i].setText(String.valueOf(challenge.getWrongNumbers()[i].getValue()));
			mChoiceButtons[i].setOnClickListener(new ChoiceClickListener());
		}
		
		mChoiceButtons[3].setText(String.valueOf(challenge.getCorrectNumber().getValue()));
		mChoiceButtons[3].setOnClickListener(new ChoiceClickListener());
	}
	
	public class ChoiceClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			Button buttonClicked = (Button) v;
			String buttonText = buttonClicked.getText().toString();
			
			Log.i("GAMEPLAY", buttonText + " clicked");
			
			if (mChallengeIter.hasNext()) {
				setupChallenge(mChallengeIter.next());
			} else {
				Log.i("GAMEPLAY", "Done");
			}
		
		}
	}
}
