package com.google.mooveaze.controller;

import android.app.Activity;
import android.os.Bundle;
import com.google.mooveaze.model.Movie;
import com.google.mooveaze.model.repositories.MovieRepository;


public class MovieActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String movieId = getIntent().getExtras().getString("movieId");

        Movie movie = new MovieRepository().get(movieId);
        System.out.println("movie.getName() = " + movie.getName());
        System.out.println("movie.getDescription() = " + movie.getDescription());
    }
}
