package com.tamil.kadhambam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tamil.TChar;
import com.tamil.TString;
import com.tamil.kadhambam.arangam.LevelCompleteActivity;

public class WordDragger extends LinearLayout {

	private LinearLayout charLayout;
	private LinkedList<TWord> words;
	private final Context context;
	private final Typeface tf;
	private TString currentWord;
	private LinearLayout footer;
	private LinearLayout score;
	private Button nextButton;
	private Button skipButton;
	private Button hintButton;
	private int leftPos = 0;
	int initialScore;
	private TextView scoredPoints;
	private LevelCompleteActivity levelCompleteActivity;
	private int totalScore;

	public WordDragger(Context context, Typeface tf, LevelCompleteActivity levelCompleteActivity) {
		super(context);
		this.levelCompleteActivity = levelCompleteActivity;
		setOrientation(LinearLayout.VERTICAL);
		this.context = context;
		this.tf = tf;
		createScoreLayout(context);
		addView(score);
		createCharLayout(context);
		addView(charLayout);
		setBackgroundResource(R.drawable.bg);
		createFooter(context);
		addView(footer);
	}

	private void createFooter(Context context) {
		footer = new LinearLayout(context);
		addHintButton(context);
		addSkipButton(context);
		addNextButton(context);
	}

	private void createCharLayout(Context context) {
		charLayout = new LinearLayout(context);
		charLayout.setGravity(Gravity.CENTER);
		charLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));
	}

	private void createScoreLayout(Context context) {
		score = new LinearLayout(context);
		setScore(context);
	}

	private void setScore(Context context) {
		TextView scoreLabel = new TextView(context);
		scoreLabel.setTypeface(tf);
		scoreLabel.setText("Á¾¢ô¦Àñ¸û : ");
		scoredPoints = new TextView(context);
		scoredPoints.setText(initialScore+" / " + totalScore);
		score.setPadding(5, 0, 0, 0);
		score.addView(scoreLabel);
		score.addView(scoredPoints);
	}

	private void addNextButton(Context context) {
		nextButton = new Button(context);
		styleFooterButton(nextButton, "«Îò¾Ð");
		nextButton.setVisibility(INVISIBLE);
		nextButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextButton.setVisibility(INVISIBLE);
				hintButton.setVisibility(VISIBLE);
				currentWord = words.getFirst().getWord();
				rerender(currentWord.getJumbledChars(), false);
			}
		});
		footer.addView(nextButton);		
	}

	private void addSkipButton(Context context) {
		skipButton = new Button(context);
		styleFooterButton(skipButton, "skip");
		skipButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		skipButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				words.removeFirst();
				if(!words.isEmpty())
				{
					currentWord = words.getFirst().getWord();
					rerender(currentWord.getJumbledChars(), false);
				}else {
					levelCompleteActivity.newLevel();
				}
				
			}
			
		});
		footer.addView(skipButton);
	}
	
	private void addHintButton(Context context) {
		hintButton = new Button(context);
		styleFooterButton(hintButton, "Hint");
		hintButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				raiseAToast(words.getFirst().getHint(), Toast.LENGTH_LONG, 0xffD8D8D8);
			}
		});
		hintButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		footer.addView(hintButton);
	}

	private void styleFooterButton(Button button, String text) {
		button.setText(text);
		button.setTypeface(tf);
		button.setTextSize(20);
	}

	public void render(LinkedList<TWord> words, int initialScore, int totalScore) {
		System.out.println(words.size());
		this.words = words;
		this.initialScore = initialScore;
		this.totalScore = totalScore;
		scoredPoints.setText(initialScore+" / " + totalScore);
		currentWord = words.getFirst().getWord();
		rerender(currentWord.getJumbledChars(), false);
	}

	private void rerender(final List<TChar> tamilChars, boolean isComplete) {
		charLayout.removeAllViews();
		if (isComplete) {
			completeView();
			return;

		}
		for (TChar tChar : tamilChars) {
			Button charView = new Button(context);
			styleView(charView, tChar.getChar(), 20, 0xFF000000);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50, 1);
			charView.setLayoutParams(layoutParams);
			charView.setBackgroundResource(R.layout.char_bg);
			charView.setDrawingCacheEnabled(true);
			charLayout.addView(charView);
			charView.setOnTouchListener(new DragListener(tamilChars));
		}
	}

	private void completeView() {
		List<TChar> tChars = currentWord.getChars();

		for (TChar tChar : tChars) {
			Button charView = new Button(context);
			styleView(charView, tChar.getChar(), 20, 0xFFFFFFFF);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50, 1);
			charView.setLayoutParams(layoutParams);
			charView.setBackgroundResource(R.layout.completed_bg);
			charLayout.addView(charView);
		}
		words.removeFirst();
		if (!words.isEmpty()) {
			nextButton.setVisibility(VISIBLE);
			hintButton.setVisibility(INVISIBLE);
		} else {
			levelCompleteActivity.newLevel();
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

	private void raiseAToast(String text, int length, int color) {
		Toast toast = new Toast(context);
		LinearLayout toastLayout = new LinearLayout(context);
		toastLayout.addView(styleView(new TextView(context), text,
				25, color));
		toast.setView(toastLayout);
		toast.setDuration(length);
		toast.show();
	}

	private class DragListener implements OnTouchListener {

		private final List<TChar> tChars;

		public DragListener(List<TChar> tChars) {
			this.tChars = tChars;
		}

		@Override
		public boolean onTouch(View view, MotionEvent me) {
			if (me.getAction() == MotionEvent.ACTION_DOWN) {
				leftPos = view.getLeft();
				view.setBackgroundResource(R.layout.drag_bg);
			}
			if(me.getAction() == MotionEvent.ACTION_MOVE) {
				int yPos = (int)me.getRawY();
				int xPos = (int)me.getRawX();
				int height = view.getHeight();
				int width = view.getWidth();
				view.layout(xPos-(width/2), yPos-(2*height), xPos+(width/2), yPos-height);
				((Button) view).setGravity(Gravity.CENTER);
				view.setPadding(10, 10, 10, 10);
				view.bringToFront();
			}
			if (me.getAction() == MotionEvent.ACTION_UP) {
				List<TChar> newList = constructNewTamilWord(charLayout, tChars,
						me, leftPos);

				boolean isWordFound = currentWord.getChars().equals(newList);
				if (isWordFound && !words.isEmpty()) {
					raiseAToast("Å¡úòÐì¸û", Toast.LENGTH_SHORT, 0xff0A2A0A);
					changeScore();
				}
				rerender(newList, isWordFound);
			}
			return false;
		}

		private void changeScore() {
			initialScore++;
			scoredPoints.setText(initialScore+" / " + totalScore);
			
		}

		private List<TChar> constructNewTamilWord(
				final LinearLayout charLayout, final List<TChar> tamilChars, 
				MotionEvent me, int leftPos) {
			int offset = charLayout.getWidth() / tamilChars.size();
			int targetPosition = ((int) me.getRawX() / offset);
			targetPosition = (targetPosition >= tamilChars.size()) ? tamilChars
					.size() - 1 : targetPosition;
			int currentPosition = leftPos / offset;
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

	public int getInitialScore() {
		return initialScore;
	}
}
