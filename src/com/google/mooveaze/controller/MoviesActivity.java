package com.google.mooveaze.controller;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import com.google.mooveaze.R;
import com.google.mooveaze.lib.SyncTask;
import com.google.mooveaze.model.repositories.MovieRepository;
import com.google.mooveaze.view.MovieCursorAdapter;

public class MoviesActivity extends ListActivity {
    private static final int PROGRESS_DIALOG = 0;
    private ProgressDialog progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(false) {
            showDialog(PROGRESS_DIALOG);

            new SyncTask() {
                protected void onPostExecute(Integer integer) {
                    if(progress != null) progress.cancel();
                    showMovies();
                }
            }.execute();
        }
        else {
            showMovies();
        }
    }

    private void showMovies() {
        setContentView(R.layout.movies);

        Cursor cursor = new MovieRepository().all();
        startManagingCursor(cursor);

        String[] columns = new String[]{
                MovieRepository.Columns.NAME,
                MovieRepository.Columns.RELEASED,
                MovieRepository.Columns.RATING
        };

        int[] to = new int[]{
                R.id.movie_name,
                R.id.movie_released,
                R.id.movie_rating
        };

        this.setListAdapter(new MovieCursorAdapter(this, R.layout.movie, cursor, columns, to));
    }

    protected Dialog onCreateDialog(int id) {
        if(id == PROGRESS_DIALOG) {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setMessage("Initial movie sync in progress. Please wait...");
            return progress;
        }

        return null;
    }
}
