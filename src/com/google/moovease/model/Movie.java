package com.google.moovease.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Movie {
    public static int count(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from movies", new String[0]);
        cursor.moveToNext();
        return cursor.getInt(0);
    }

    public void save(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MovieColumns.TITLE, "Cool Movie Title");
        db.insert("movies", null, values);
    }
}
