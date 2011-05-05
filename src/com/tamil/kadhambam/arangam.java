package com.tamil.kadhambam;

import java.util.LinkedList;

import com.tamil.TString;
import com.tamil.kadhambam.db.DBManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.AnalogClock;

public class arangam extends Activity {

	private Typeface tf;
	private LinkedList<TWord> words;
	private int initialScore;
	private int totalScore;
	private WordDragger dragger;
	private String difficultyLevel;
	
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
		tf = Typeface.createFromAsset(getAssets(), "fonts/TSC_Times.ttf");
		dragger = new WordDragger(getApplicationContext(), tf, new LevelCompleteActivity());
		dragger.render(words, initialScore, totalScore);
		setContentView(dragger);
		
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE); 
		KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE); 
		lock.disableKeyguard();
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
			       .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
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
	}

	@Override
	protected void onPause() {
		super.onPause();
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE); 
		KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE); 
		lock.reenableKeyguard();
	}

}