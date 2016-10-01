package com.example.chapter07.provider;

import com.example.chapter07.R;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class TestProvider extends Activity implements OnClickListener {
	private String newId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b2_provider);
		findViewById(R.id.b2_insert).setOnClickListener(this);
		findViewById(R.id.b2_update).setOnClickListener(this);
		findViewById(R.id.b2_delete).setOnClickListener(this);
		findViewById(R.id.b2_select).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b2_insert: {
			Uri uri = Uri
					.parse("content://com.example.chapter06.database/book");
			ContentValues values = new ContentValues();
			values.put("name", "A Clash of Kings");
			values.put("author", "George Martin");
			values.put("pages", 1040);
			values.put("price", 22.85);
			Uri newUri = getContentResolver().insert(uri, values);
			newId = newUri.getPathSegments().get(1);
			break;
		}
		case R.id.b2_select: {
			Uri uri = Uri
					.parse("content://com.example.chapter06.database/book");
			Cursor cursor = getContentResolver().query(uri, null, null, null,
					null);
			if (cursor.moveToFirst()) {
				do {
					String name = cursor.getString(cursor
							.getColumnIndex("name"));
					String author = cursor.getString(cursor
							.getColumnIndex("author"));
					int pages = cursor.getInt(cursor.getColumnIndex("pages"));
					double price = cursor.getDouble(cursor
							.getColumnIndex("price"));
					Log.d("TestSQLite", "book name is " + name);
					Log.d("TestSQLite", "book author is " + author);
					Log.d("TestSQLite", "book pages is " + pages);
					Log.d("TestSQLite", "book price is " + price);
				} while (cursor.moveToNext());
			}
			cursor.close();
			break;
		}
		case R.id.b2_update: {
			Uri uri = Uri
					.parse("content://com.example.chapter06.database/book/"
							+ newId);
			ContentValues values = new ContentValues();
			values.put("name", "A Storm of Swords");
			values.put("author", "George Martin");
			values.put("pages", 1216);
			values.put("price", 24.05);
			getContentResolver().update(uri, values, null, null);
			break;
		}
		case R.id.b2_delete: {
			Uri uri = Uri
					.parse("content://com.example.chapter06.database/book/"
							+ newId);
			getContentResolver().delete(uri, null, null);
			break;
		}

		default:
			break;
		}
	}

}
