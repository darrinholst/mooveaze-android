package com.google.mooveaze.model.repositories;

import android.content.ContentValues;
import android.provider.BaseColumns;
import com.google.mooveaze.model.GenreMapping;

public class GenreMappingRepository extends BaseRepository {
    private static final String TABLE_NAME = "genres_titles";

    private static class Columns implements BaseColumns {
        public static final String GENRE_ID = "genre_id";
        public static final String TITLE_ID = "title_id";
    }

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            Columns.GENRE_ID + " INTEGER," +
            Columns.TITLE_ID + " INTEGER," +
            "PRIMARY KEY (" + Columns.GENRE_ID + ", " + Columns.TITLE_ID + ")" +
            ")";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public void add(GenreMapping mapping) {
        ContentValues values = new ContentValues();
        values.put(Columns.GENRE_ID, mapping.getGenreId());
        values.put(Columns.TITLE_ID, mapping.getTitleId());
        database.insert(TABLE_NAME, null, values);
    }
}
