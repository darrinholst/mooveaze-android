package com.google.mooveaze.model.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.mooveaze.lib.Log;
import com.google.mooveaze.model.repositories.GenreMappingRepository;
import com.google.mooveaze.model.repositories.GenreRepository;
import com.google.mooveaze.model.repositories.MovieRepository;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mooveaze";
    private static final int DATABASE_VERSION = 9;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.debug("Creating database");
        db.execSQL(MovieRepository.CREATE_TABLE);
        db.execSQL(GenreRepository.CREATE_TABLE);
        db.execSQL(GenreMappingRepository.CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.debug("Upgrading database");
        db.execSQL(MovieRepository.DROP_TABLE);
        db.execSQL(GenreRepository.DROP_TABLE);
        db.execSQL(GenreMappingRepository.DROP_TABLE);
        onCreate(db);
    }
}
