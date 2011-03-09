package com.tamil.kadhambam;

import java.util.LinkedList;

import com.tamil.kadhambam.db.DBManager;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class DifficultyLevelSelection extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(getApplicationContext());
		Button easyButton = new Button(getApplicationContext()); 
		Button mediumButton = new Button(getApplicationContext()); 
		Button hardButton = new Button(getApplicationContext());
		easyButton.setText("Easy");
		mediumButton.setText("Medium");
		hardButton.setText("Hard");
		easyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DifficultyLevelSelection.this, arangam.class);
				intent.putExtra("difficultyLevel", ((Button) v).getText());
				startActivity(intent);
				
			}
		});
		layout.addView(easyButton);
		layout.addView(mediumButton);
		layout.addView(hardButton);
		layout.setBackgroundResource(R.drawable.brown_wood);
		layout.setGravity(Gravity.CENTER);
		layout.setPadding(20, 20, 0, 0);
		setContentView(layout);
	}
	
//	public void run() {
//		startActivity(new Intent(DifficultyLevelSelection.this, arangam.class));
//	}
	
}