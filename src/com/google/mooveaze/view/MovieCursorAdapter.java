package com.google.mooveaze.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.google.mooveaze.R;
import com.google.mooveaze.lib.Log;
import com.google.mooveaze.model.repositories.MovieRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieCursorAdapter extends SimpleCursorAdapter {
    private int layout;

    public MovieCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.layout = layout;
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        bindView(view, context, cursor);
        return view;
    }

    public void bindView(View view, Context context, Cursor cursor) {
        int nameIndex = cursor.getColumnIndex(MovieRepository.Columns.NAME);
        ((TextView) view.findViewById(R.id.movie_name)).setText(cursor.getString(nameIndex));

        int releasedIndex = cursor.getColumnIndex(MovieRepository.Columns.RELEASED);
        String releaseDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date(cursor.getLong(releasedIndex)));
        ((TextView) view.findViewById(R.id.movie_released)).setText(releaseDate);

        int ratingIndex = cursor.getColumnIndex(MovieRepository.Columns.RATING);
        ((TextView) view.findViewById(R.id.movie_rating)).setText(cursor.getString(ratingIndex));
    }
}
