package com.tamil.kadhambam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import com.tamil.TChar;
import com.tamil.TString;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WordDragger extends LinearLayout{

	private LinearLayout charLayout;
	private LinkedList<String> words;
	private final Context context;
	private final Typeface tf;
	private TString currentWord;

	public WordDragger(Context context, Typeface tf) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		this.context = context;
		this.tf = tf;
		charLayout = new LinearLayout(context);
		addView(charLayout);
	}

	public void render(LinkedList<String> words) {
		this.words = words;
		currentWord = new TString(words.removeFirst());
		rerender(currentWord.getJumbledChars(), false);
	}

	private void rerender(final List<TChar> tamilChars, boolean isComplete) {
		if (isComplete) {
			if(words.isEmpty()) return;
			currentWord = new TString(words.removeFirst());
		}
		final List<TChar> tChars = isComplete ? currentWord.getJumbledChars() : tamilChars;
		for (TChar tChar : tChars) {
			Button charView = new Button(context);
			styleView(charView, tChar.getChar(), 18, 0xFFFFFFFF);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50, 1);
			charView.setLayoutParams(layoutParams);
			charView.setBackgroundResource(isComplete ? R.layout.completed_bg : R.layout.char_bg);
			charLayout.addView(charView);
			charView.setOnTouchListener(new DragListener(tChars));
		}
	}

	private View styleView(TextView view, String text, int size, int color) {
		view.setText(text);
		view.setTextSize(size);
		view.setTextColor(color);
		view.setTypeface(tf);
		view.setPadding(10, 10, 10, 10);
		view.setGravity(Gravity.CENTER);
		return view;
	}

	private class DragListener implements OnTouchListener {

		private final List<TChar> tChars;

		public DragListener(List<TChar> tChars) {
			this.tChars = tChars;
		}

		@Override
		public boolean onTouch(View view, MotionEvent me) {
			if (me.getAction() == MotionEvent.ACTION_DOWN) {
				view.setBackgroundResource(R.layout.drag_bg);
			}
			if (me.getAction() == MotionEvent.ACTION_UP) {
				List<TChar> newList = constructNewTamilWord(charLayout, tChars, view, me);

				boolean isWordFound = currentWord.getChars().equals(newList);
				charLayout.removeAllViews();
				rerender(newList, isWordFound);
				if (isWordFound) {
					Toast toast = new Toast(context);
					toast.setView(styleView(new TextView(context), "Å¡úòÐì¸û", 25, 0xff00ff00));
					toast.setDuration(Toast.LENGTH_SHORT);
					toast.show();
					return false;
				}
			}
			return false;
		}

		private List<TChar> constructNewTamilWord(
				final LinearLayout charLayout, final List<TChar> tamilChars,
				View view, MotionEvent me) {
			int offset = charLayout.getWidth() / tamilChars.size();
			int targetPosition = ((int) me.getRawX() / offset);
			int currentPosition = view.getLeft() / offset;
			List<TChar> newList = new ArrayList<TChar>();
			TChar tempChar = null;
			for (int i = 0; i < tamilChars.size(); i++) {
				if (i == currentPosition) {
					tempChar = tamilChars.get(i);
				} else if (i == targetPosition && tempChar != null
						&& newList.size() == i + 1) {
					newList.add(tempChar);
				} else {
					newList.add(tamilChars.get(i));
				}
			}
			if (tamilChars.size() != newList.size()) {
				newList.add(targetPosition, tempChar);
			}
			return newList;
		}
	}
}
