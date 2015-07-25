package com.tamil.kadhambam;

import android.content.ClipData;
import android.content.Context;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tamil.TChar;
import com.tamil.TString;
import com.tamil.kadhambam.arangam.LevelCompleteActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WordDragger extends LinearLayout {

	private final WordAdapter adapter;
	private LinearLayout charLayout;
	private LinkedList<TWord> words;
	private final Context context;
	private TString currentWord;
	private LinearLayout score;
	private Button nextButton;
	private Button skipButton;
	private Button hintButton;
	int initialScore;
	private TextView scoredPoints;
	private LevelCompleteActivity levelCompleteActivity;
	private int totalScore;
	private ArrayList<TChar> jumbledChars;

	public WordDragger(Context context, LevelCompleteActivity levelCompleteActivity) {
		super(context);
		adapter = new WordAdapter(context);
		this.levelCompleteActivity = levelCompleteActivity;
		this.context = context;
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundResource(R.drawable.old_paper);
		createScoreLayout(context);
		createCharLayout(context);
		createFooter(context);
	}

	private void createFooter(Context context) {
		LinearLayout footer = new LinearLayout(context);
		footer.addView(addHintButton(context));
		footer.addView(addSkipButton(context));
		footer.addView(addNextButton(context));
		addView(footer);
	}

	private void createCharLayout(Context context) {
		charLayout = new LinearLayout(context);
		charLayout.setGravity(Gravity.CENTER);
		charLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
		addView(charLayout);
	}

	private void createScoreLayout(Context context) {
		score = new LinearLayout(context);
		TextView scoreLabel = Util.createTextView(context, "Á¾¢ô¦Àñ¸û : ");
		scoredPoints = new TextView(context);
		scoredPoints.setText(initialScore+" / " + totalScore);
		score.setPadding(5, 0, 0, 0);
		score.addView(scoreLabel);
		score.addView(scoredPoints);
		addView(score);
	}

	private Button addNextButton(Context context) {
		nextButton = Util.createButton(context, "«Îò¾Ð", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextButton.setVisibility(INVISIBLE);
				hintButton.setVisibility(VISIBLE);
				skipButton.setVisibility(VISIBLE);
				currentWord = words.getFirst().getWord();
				renderWord(currentWord.getJumbledChars(), false);
			}
		});
		nextButton.setVisibility(INVISIBLE);
		return nextButton;
	}

	private Button addSkipButton(Context context) {
		skipButton = Util.createButton(context, "Skip", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				words.removeFirst();
				if(!words.isEmpty())
				{
					currentWord = words.getFirst().getWord();
					renderWord(currentWord.getJumbledChars(), false);
				}else {
					levelCompleteActivity.newLevel();
				}				
			}			
		});
		return skipButton;
	}
	
	private Button addHintButton(final Context context) {
		hintButton = Util.createButton(context, "Hint", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Util.raiseAToast(words.getFirst().getHint(), Toast.LENGTH_LONG, 0xffD8D8D8, context);
			}
		});
		return hintButton;
	}

	public void render(LinkedList<TWord> words, int initialScore, int totalScore) {
		System.out.println(words.size());
		this.words = words;
		this.initialScore = initialScore;
		this.totalScore = totalScore;
		scoredPoints.setText(initialScore+" / " + totalScore);
		currentWord = words.getFirst().getWord();
		renderWord(currentWord.getJumbledChars(), false);
	}

	private void renderWord(final ArrayList<TChar> tamilChars, boolean isComplete) {
		jumbledChars = tamilChars;
		charLayout.removeAllViews();
		if (isComplete) {
			completeView();
			return;
		}
		for (TChar aChar: tamilChars){
			charLayout.addView(adapter.getView(aChar, tamilChars.indexOf(aChar), touchListener, dragListener));
		}
	}

	OnTouchListener touchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				ClipData data = ClipData.newPlainText("", "");
				View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				view.setVisibility(View.INVISIBLE);
				return true;
			}
			if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
				view.setVisibility(View.VISIBLE);
			}
			return false;

		}
	};

	OnDragListener dragListener = new OnDragListener(){
		@Override
		public boolean onDrag(View v, DragEvent event) {
			if(event.getAction() == DragEvent.ACTION_DROP){
				View view = (View) event.getLocalState();
				int from = (Integer) view.getTag(); //5
				int to = (Integer) v.getTag(); //4
				TChar temp = jumbledChars.remove(from);
				jumbledChars.add(to, temp);
				renderWord(jumbledChars, currentWord.getChars().equals(jumbledChars));
			}
			return true;
		}
	};
	
	private void changeScore() {
		initialScore++;
		scoredPoints.setText(initialScore+" / " + totalScore);			
	}

	private void completeView() {
		List<TChar> tChars = currentWord.getChars();
		changeScore();

		for (TChar tChar : tChars) {
			TextView charView = new TextView(context);
			Util.styleView(charView, tChar.getChar(), 20, 0xFFFFFFFF);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(250, 250, 1);
			charView.setLayoutParams(layoutParams);
			charView.setBackgroundResource(R.layout.completed_bg);
			charLayout.addView(charView);
		}
		words.removeFirst();
		if (!words.isEmpty()) {
			nextButton.setVisibility(VISIBLE);
			hintButton.setVisibility(INVISIBLE);
			skipButton.setVisibility(INVISIBLE);
		} else {
			levelCompleteActivity.newLevel();
		}
	}

	public int getInitialScore() {
		return initialScore;
	}
}
