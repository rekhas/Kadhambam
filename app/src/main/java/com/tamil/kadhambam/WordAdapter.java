package com.tamil.kadhambam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tamil.TChar;

public class WordAdapter {
    private final LayoutInflater mInflater;

    public WordAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public View getView(TChar character, int index, View.OnTouchListener touchListener, View.OnDragListener dragListener) {
        View convertView = mInflater.inflate(R.layout.cell, null);
            TextView textView = (TextView) convertView.findViewById(R.id.draggableTamilChar);
            textView.setLayoutParams(new LinearLayout.LayoutParams(250, 250, 1));
        textView.setText(character.getChar());
        textView.setTypeface(Util.tf);
        textView.setTag(index);
        textView.setOnTouchListener(touchListener);
        convertView.setOnDragListener(dragListener);
        convertView.setTag(index);
        return convertView;
    }

    public View getView(TChar character){
        View convertView = mInflater.inflate(R.layout.completed_cell, null);
        TextView textView = (TextView) convertView.findViewById(R.id.draggableTamilChar);
        textView.setLayoutParams(new LinearLayout.LayoutParams(250, 250, 1));
        textView.setText(character.getChar());
        textView.setTypeface(Util.tf);
        return convertView;
    }

}
