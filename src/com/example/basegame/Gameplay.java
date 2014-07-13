package com.example.basegame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class Gameplay extends Activity {

	private Game mGame;
	
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
		
		mGame = new Game(1, Game.GameMode.BINARY_DECIMAL);
				
	}
}
