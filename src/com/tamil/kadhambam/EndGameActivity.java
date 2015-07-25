package com.tamil.kadhambam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EndGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endgame);
		Thread logoThread = new Thread() {
			long currTime = 0;
			long waitTime = 2000L;

			@Override
			public void run() {
				try {
					super.run();
					while (currTime < waitTime) {
						sleep(100);
						currTime += 100;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		logoThread.start();
	}
}
