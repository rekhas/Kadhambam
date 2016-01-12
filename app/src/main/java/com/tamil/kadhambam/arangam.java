package com.tamil.kadhambam;

import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;

import com.tamil.kadhambam.db.DBManager;

public class arangam extends Activity {

	private LinkedList<TWord> words;
	private int initialScore;
	private int totalScore;
	private WordDragger dragger;
	private String difficultyLevel;
	private KeyguardManager keyguardManager;
	private KeyguardLock keyGuardLock;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			words = (LinkedList<TWord>) savedInstanceState.getSerializable("data");
			initialScore = (Integer) savedInstanceState.getSerializable("score");
			totalScore = (Integer) savedInstanceState.getSerializable("totalScore");
		} else {
			DBManager dbManager = new DBManager(getApplicationContext());
			difficultyLevel = (String) getIntent().getExtras().get("difficultyLevel");
			words = dbManager.getWords(difficultyLevel.toLowerCase());
			initialScore = 0;
			totalScore = words.size();
		}
		Util.tf = Typeface.createFromAsset(getAssets(), "fonts/kavit.ttf");
		dragger = new WordDragger(getApplicationContext(), new LevelCompleteActivity());
		dragger.render(words, initialScore, totalScore);
		setContentView(dragger);
		
		keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
		keyGuardLock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
		keyGuardLock.disableKeyguard();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        new LevelCompleteActivity().newLevel();
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	public class LevelCompleteActivity {
		public void newLevel() {
			AlertDialog.Builder builder = new AlertDialog.Builder(arangam.this);
			builder.setMessage("Level complete")
			       .setCancelable(false)
			       .setPositiveButton("Select difficulty level", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   startActivity(new Intent(arangam.this, DifficultyLevelSelection.class));
			        	   finish();
			           }
			       })
			       .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   keyGuardLock.reenableKeyguard();
			               finish();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("data", words);
		outState.putSerializable("score", dragger.getInitialScore());
		outState.putSerializable("totalScore", totalScore);
	}

	@Override
	protected void onPause() {
		super.onPause();
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE); 
		KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE); 
		lock.reenableKeyguard();
	}

}