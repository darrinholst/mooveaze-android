package com.google.mooveaze.model.repositories;

import android.content.ContentValues;
import android.provider.BaseColumns;
import com.google.mooveaze.model.Genre;

public class GenreRepository extends BaseRepository {
    /*
    1000|1000
1001|1001
1002|1002
1003|1003
1004|1004
1005|1005
1006|1006
1007|1007
1008|1008
1009|1009
1010|1010
1011|1011
1012|1012
1013|1013
1014|1014
1016|1016
1017|1017
1018|1018
1019|1019
1020|1020
1022|1022
1025|1025
1092|1092
1093|1093
     */
    private static final String TABLE_NAME = "genres";

    private static class Columns implements BaseColumns {
        public static final String NAME = "name";
    }

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            Columns._ID + " INTEGER PRIMARY KEY," +
            Columns.NAME + " TEXT" +
            ")";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public void add(Genre genre) {
        ContentValues values = new ContentValues();
        values.put(Columns._ID, genre.getId());
        values.put(Columns.NAME, genre.getName());
        database.insert(TABLE_NAME, null, values);
    }
}
