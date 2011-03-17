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
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class DifficultyLevelSelection extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.difficultylevel);
		setButtonClickHandler(findViewById(R.id.easy));
		setButtonClickHandler(findViewById(R.id.medium));
		setButtonClickHandler(findViewById(R.id.hard));
		findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		
	}

	private void setButtonClickHandler(View button) {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DifficultyLevelSelection.this, arangam.class);
				intent.putExtra("difficultyLevel", ((Button) v).getText());
				startActivity(intent);
				finish();
			}
		});
	}
		
}
