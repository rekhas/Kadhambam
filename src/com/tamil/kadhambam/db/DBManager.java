package com.tamil.kadhambam.db;

import java.util.Collections;
import java.util.LinkedList;

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

	public LinkedList<String> getWords() {
		DBHelper helper = new DBHelper(context);
		SQLiteDatabase db =  helper.openDatabase();
		helper.close();
		LinkedList<String> items = new LinkedList<String>();
		Cursor cursor;
		try {
			cursor = db.query("words", null, null, null, null, null, null,null);
			
			while (cursor.moveToNext()) {
				items.add(new String(cursor.getString(1)));
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