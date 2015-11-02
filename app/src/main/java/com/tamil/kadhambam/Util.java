package com.tamil.kadhambam;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class Util {
	public static Typeface tf;

	public static void raiseAToast(String text, int length, int color, Context context) {
		Toast toast = new Toast(context);
		LinearLayout toastLayout = new LinearLayout(context);
		toastLayout.addView(styleView(new TextView(context), text,
				25, color));
		toast.setView(toastLayout);
		toast.setDuration(length);
		toast.show();
	}
	
	public static View styleView(TextView view, String text, int size, int color) {
		view.setText(text);
		view.setTextSize(size);
		view.setTextColor(color);
		view.setTypeface(tf);
		view.setPadding(10, 10, 10, 10);
		view.setGravity(Gravity.CENTER);
		return view;
	}

	public static Button createButton(Context context, String text, OnClickListener clickListener){
		Button button = new Button(context);
		styleFooterButton(button, text);
		button.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		button.setOnClickListener(clickListener);
		return button;
	}

	private static void styleFooterButton(Button button, String text) {
		button.setText(text);
//		button.setTypeface(tf);
		button.setTextSize(20);
	}

	public static TextView createTextView(Context context, String string) {
		TextView textView = new TextView(context);
		textView.setText(string);
//		textView.setTypeface(tf);
		return textView;
	}

}
