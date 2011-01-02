package com.google.mooveaze.controller;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.google.mooveaze.R;
import com.google.mooveaze.model.repositories.GenreRepository;
import com.google.mooveaze.model.repositories.MovieRepository;

public class GenresActivity extends ListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genres);

        Cursor cursor = new GenreRepository().all();
        startManagingCursor(cursor);

        String[] columns = new String[]{GenreRepository.Columns.NAME};
        int[] to = new int[]{R.id.genre_name};

        this.setListAdapter(new SimpleCursorAdapter(this, R.layout.genre, cursor, columns, to));
    }
}
