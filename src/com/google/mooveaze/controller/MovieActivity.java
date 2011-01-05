package com.google.mooveaze.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import com.google.mooveaze.R;
import com.google.mooveaze.lib.Redbox2;
import com.google.mooveaze.model.Movie;
import com.google.mooveaze.model.repositories.MovieRepository;


public class MovieActivity extends Activity {
    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String movieId = getIntent().getExtras().getString("movieId");

        final Movie movie = new MovieRepository().get(movieId);

        if(movie.getDescription() == null) {
            progressDialog = ProgressDialog.show(this, "", "Retrieving movie details...", true, false);

            new AsyncTask<Void, Void, Void>() {
                protected Void doInBackground(Void... voids) {
                    Redbox2.getInstance().addMovieDetails(movie);
                    return null;
                }

                protected void onPostExecute(Void aVoid) {
                    progressDialog.cancel();
                    new MovieRepository().update(movie);
                    showMovie(movie);
                }
            }.execute();
        }
        else {
            showMovie(movie);
        }
    }

    private void showMovie(Movie movie) {
        setContentView(R.layout.movie_detail);
        setText(R.id.movie_detail_title, movie.getName());
        setText(R.id.movie_detail_starring, movie.getActors());
        setText(R.id.movie_detail_description, movie.getDescription());
    }

    protected void setText(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }
}

