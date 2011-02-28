package com.tamil.kadhambam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tamil.TChar;
import com.tamil.TString;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class arangam extends Activity {
	private final static int START_DRAGGING = 0;
	private final static int STOP_DRAGGING = 1;

	private FrameLayout frameLayout;
	private int status;
	private LayoutParams params;

	private ImageView image;
	private Typeface tf;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		frameLayout = new FrameLayout(getApplicationContext());
		final LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView textView = new TextView(getApplicationContext());
		String string = "≈¢Œ”®»≈¢";

		textView.setText(string);
		tf = Typeface.createFromAsset(getAssets(),
				"fonts/TSC_Times.ttf");
		textView.setTypeface(tf);
		layout.addView(textView);
		final LinearLayout charLayout = new LinearLayout(getApplicationContext());
		final List<TChar> tamilChars = new TString(string).getChars();
		rerender(charLayout, tamilChars);
		layout.addView(charLayout);

		setContentView(layout);

	}
	private void rerender(final LinearLayout charLayout,
			final List<TChar> tamilChars) {
		for (TChar tChar : tamilChars) {
			Button charView = new Button(getApplicationContext());
			charView.setTypeface(tf);
			charView.setText(tChar.getChar());
			charView.setTextColor(0xFF000000);
			charView.setGravity(Gravity.CENTER);
			charView.setPadding(10, 10, 10, 10);
			charView.setTextSize(18);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50, 1);
			charView.setLayoutParams(layoutParams);
			charView.setBackgroundResource(R.layout.headerbg);
			charLayout.addView(charView);
			charView.setDrawingCacheEnabled(true);
			charView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View view, MotionEvent me) {
					if (me.getAction() == MotionEvent.ACTION_UP) {
						status = STOP_DRAGGING;
						int offset = charLayout.getWidth()/tamilChars.size();
						int targetPosition = ((int)me.getRawX()/offset);
						int currentPosition = view.getLeft()/offset;
						List<TChar> newList = new ArrayList<TChar>();
						TChar tempChar = null;
						for (int i = 0; i < tamilChars.size(); i++) {
							if(i == currentPosition){
								tempChar = tamilChars.get(i);
							} else if (i == targetPosition && tempChar != null && newList.size() == i+1){
								newList.add(tempChar);
							} 
							else{
								newList.add(tamilChars.get(i));
							}
						}
						if(tamilChars.size() != newList.size()){
							newList.add(targetPosition, tempChar);
						}
						charLayout.removeAllViews();
						rerender(charLayout, newList);
					} 
					return false;
				}
			});

		}
	}
}