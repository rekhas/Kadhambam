package com.tamil.kadhambam.db;

import java.util.Collections;
import java.util.LinkedList;

import com.tamil.TString;
import com.tamil.kadhambam.TWord;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
	Context context;

	public DBManager(Context context) {
		this.context = context;		
	}

	public LinkedList<TWord> getWords(String difficultyLevel) {
		DBHelper helper = new DBHelper(context);
		SQLiteDatabase db =  helper.openDatabase();
		helper.close();
		LinkedList<TWord> items = new LinkedList<TWord>();
		Cursor cursor;
		try {
			cursor = db.query("words", new String[]{"word","hint"}, "difficulty_level = ?", new String[]{difficultyLevel}, null, null, null,null);
			
			while (cursor.moveToNext()) {
				items.add(new TWord(new TString(cursor.getString(0)), cursor.getString(1)));
			}
			cursor.close();

		} catch (SQLException e) {
			Log.d("DB ERROR", e.toString());
			e.printStackTrace();
		}
		db.close();
		Collections.shuffle(items);
		return items;
	}
}