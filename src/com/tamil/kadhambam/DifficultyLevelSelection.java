package com.tamil.kadhambam;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DifficultyLevelSelection extends Activity {
	
	private KeyguardManager keyguardManager;
	private KeyguardLock keyGuardLock;

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
		keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
		keyGuardLock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
		keyGuardLock.disableKeyguard();
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
