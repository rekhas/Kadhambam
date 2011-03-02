package com.tamil.kadhambam;

import java.util.LinkedList;

import com.tamil.kadhambam.db.DBManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

public class arangam extends Activity {

	private Typeface tf;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBManager dbManager = new DBManager(getApplicationContext());
		LinkedList<String> words = dbManager.getWords();

		tf = Typeface.createFromAsset(getAssets(), "fonts/TSC_Times.ttf");
		WordDragger dragger = new WordDragger(getApplicationContext(), tf, new FinishActivity());
		dragger.render(words);
		setContentView(dragger);
	}
	
	public class FinishActivity {
		public void endGame(){
			startActivity(new Intent(arangam.this, EndGameActivity.class));
			finish();
		}
	}

}