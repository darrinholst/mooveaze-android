package com.google.mooveaze.model.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.google.mooveaze.lib.Log;
import com.google.mooveaze.model.Movie;

public class MovieRepository extends BaseRepository {
    private static final String TABLE_NAME = "movies";

    public static class Columns implements BaseColumns {

        public static final String NAME = "name";
        public static final String RELEASED = "released";
        public static final String RATING = "rating";
        public static final String ACTORS = "actors";
        public static final String DESCRIPTION = "description";
        public static final String FORMAT = "format";
        public static final String RUNNING_TIME = "running_time";
        public static final String IMAGE = "image";
    }

    public boolean contains(Movie movie) {
        Cursor cursor = database.rawQuery("select count(*) from " + TABLE_NAME + " where " + Columns._ID + " = " + movie.getId(), new String[]{});
        cursor.moveToNext();
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public void add(Movie movie) {
        Log.debug("adding " + movie.getName());
        ContentValues values = new ContentValues();
        values.put(Columns._ID, movie.getId());
        values.put(Columns.NAME, movie.getName());
        values.put(Columns.RELEASED, movie.getReleased().getTime());
        values.put(Columns.RATING, movie.getRating());
        values.put(Columns.FORMAT, movie.getFormat());
        values.put(Columns.IMAGE, movie.getImage());
        database.insert(TABLE_NAME, null, values);
    }

    public Cursor all() {
        return database.rawQuery("select * from " + TABLE_NAME + " order by " + Columns.RELEASED + " desc", new String[0]);
    }

    public Movie get(String movieId) {
        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME + " where " + Columns._ID + " = ?", new String[]{movieId});
        cursor.moveToNext();
        Movie movie = null;

        try {
            movie = Movie.fromCursor(cursor);
        }
        catch(Exception e) {
            Log.error(e);
        }

        cursor.close();
        return movie;
    }
}
