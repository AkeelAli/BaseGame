package com.example.basegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameOptionsActivity extends Activity {

	private static final int NUM_GAME_MODE_BUTTONS = 4;
	private Button mGameModeButtons[] = new Button[NUM_GAME_MODE_BUTTONS];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_options);
		
		mGameModeButtons[0] = (Button) findViewById(R.id.game_mode_binary_decimal);
		mGameModeButtons[1] = (Button) findViewById(R.id.game_mode_hex_decimal);
		mGameModeButtons[2] = (Button) findViewById(R.id.game_mode_binary_hex);
		mGameModeButtons[3] = (Button) findViewById(R.id.game_mode_mixed);
		
		for (int i = 0; i < NUM_GAME_MODE_BUTTONS; ++i) {
			mGameModeButtons[i].setOnClickListener(new GameModeButtonOnClickListener());
		}
		
	}
	
	public class GameModeButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String extraData;
			Button buttonClicked = (Button) v;
			
			switch(buttonClicked.getId()) {
			case R.id.game_mode_binary_decimal:
				extraData = "BINARY_DECIMAL";
				break;
			case R.id.game_mode_hex_decimal:
				extraData = "HEX_DECIMAL";
				break;
			case R.id.game_mode_binary_hex:
				extraData = "BINARY_HEX";
				break;
			case R.id.game_mode_mixed:
			default:
				extraData = "MIXED";
				break;
			}
			
			
			Intent intent = new Intent(GameOptionsActivity.this, GameplayActivity.class);
			intent.putExtra("GameMode", extraData);
			startActivity(intent);
			
		}
	}
}
