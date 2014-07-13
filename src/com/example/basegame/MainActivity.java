package com.example.basegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button newGameButton = (Button) findViewById(R.id.new_game);
		newGameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(MainActivity.this, Gameplay.class);
				startActivity(intent);
			
			}
		});
	}
}
