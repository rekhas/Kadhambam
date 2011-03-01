package com.tamil.kadhambam;

import java.util.LinkedList;

import com.tamil.kadhambam.db.DBManager;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

public class arangam extends Activity {

	private Typeface tf;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		LinkedList<String> words = new LinkedList<String>();
//		words.add("Å¢¨ÇÂ¡ðÎ");
//		words.add("¾Á¢ú¿¡Î");
//		words.add("Å¢Î¾¨Ä");
		DBManager dbManager = new DBManager(getApplicationContext());
		LinkedList<String> words = dbManager.getWords();

		tf = Typeface.createFromAsset(getAssets(), "fonts/TSC_Times.ttf");
		WordDragger dragger = new WordDragger(getApplicationContext(), tf);
		dragger.render(words);
		setContentView(dragger);

	}

}