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
	private int initialScore;
	private WordDragger dragger;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			words = (LinkedList<String>) savedInstanceState.getSerializable("data");
			initialScore = (Integer) savedInstanceState.getSerializable("score");
		} else {
			DBManager dbManager = new DBManager(getApplicationContext());
			words = dbManager.getWords();
			initialScore = 0;
		}
		tf = Typeface.createFromAsset(getAssets(), "fonts/TSC_Times.ttf");
		dragger = new WordDragger(getApplicationContext(), tf,
				new FinishActivity());
		dragger.render(words, initialScore);
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
		super.onSaveInstanceState(outState);
		outState.putSerializable("data", words);
		outState.putSerializable("score", dragger.getInitialScore());
	}

	@Override
	protected void onPause() {
		super.onPause();
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE); 
		KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE); 
		lock.reenableKeyguard();
	}
	
	

}