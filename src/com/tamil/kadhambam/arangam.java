package com.tamil.kadhambam;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class arangam extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(getApplicationContext());
        String string = "≈¢Œ”®»";
        
		textView.setText(string.length() + "");
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/TSC_Comic.ttf");
        textView.setTypeface(tf);
        setContentView(textView);
    }
}