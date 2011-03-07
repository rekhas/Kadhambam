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

public class arangam extends Activity {

	private Typeface tf;
	private LinkedList<String> words;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			words = (LinkedList<String>) savedInstanceState.getSerializable("data");
			Log.d("saved state", words.size() + "");
		} else {
			DBManager dbManager = new DBManager(getApplicationContext());
			words = dbManager.getWords();
		}
		tf = Typeface.createFromAsset(getAssets(), "fonts/TSC_Times.ttf");
		WordDragger dragger = new WordDragger(getApplicationContext(), tf,
				new FinishActivity());
		dragger.render(words);
		setContentView(dragger);
		
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE); 
		KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE); 
		lock.disableKeyguard();
	}

	public class FinishActivity {
		public void endGame() {
			startActivity(new Intent(arangam.this, EndGameActivity.class));
			finish();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.d("to be saved", words.size() + "");
		outState.putSerializable("data", words);
	}

}