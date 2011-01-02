package com.google.mooveaze.model.repositories;

import android.database.sqlite.SQLiteDatabase;

public class BaseRepository {
    protected SQLiteDatabase database;

    public BaseRepository() {
        this.database = Database.getInstance();
    }
}
