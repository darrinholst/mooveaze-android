package com.google.mooveaze.model.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.google.mooveaze.model.Genre;

public class GenreRepository extends BaseRepository {
    private static final String TABLE_NAME = "genres";

    public static class Columns implements BaseColumns {

        public static final String NAME = "name";
    }

    public void add(Genre genre) {
        ContentValues values = new ContentValues();
        values.put(Columns._ID, genre.getId());
        values.put(Columns.NAME, genre.getName());
        database.insert(TABLE_NAME, null, values);
    }

    public Cursor all() {
        return database.rawQuery("select * from " + TABLE_NAME + " order by " + Columns.NAME + " asc", new String[0]);
    }
}
