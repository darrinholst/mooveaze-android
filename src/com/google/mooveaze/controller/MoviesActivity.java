package com.google.mooveaze.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.mooveaze.model.Movie;

public class MoviesActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);

        if(Movie.count(this) == 0) {
            textview.setText("Sync some movies");

            new Movie().save(this);
        }
        else {
            textview.setText("Show some movies");
        }

        setContentView(textview);
    }
}
