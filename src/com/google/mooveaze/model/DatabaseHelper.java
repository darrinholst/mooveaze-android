package com.google.mooveaze.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import com.google.mooveaze.lib.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mooveaze";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.debug("Creating database");

        db.execSQL("CREATE TABLE " + MOVIE_TABLE_NAME + " ("
                + MovieColumns._ID + " INTEGER PRIMARY KEY,"
                + MovieColumns.TITLE + " TEXT,"
                + MovieColumns.RELEASED + " INTEGER"
                + ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.debug("Upgrading database");
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        onCreate(db);
    }

    public static final String MOVIE_TABLE_NAME = "movies";

    public interface MovieColumns extends BaseColumns {
        public static final String TITLE = "title";
        public static final String RELEASED = "released";
    }
}
