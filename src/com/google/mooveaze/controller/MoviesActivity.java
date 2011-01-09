package com.google.mooveaze.controller;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.google.mooveaze.R;
import com.google.mooveaze.lib.Redbox;
import com.google.mooveaze.lib.SyncTask;
import com.google.mooveaze.model.repositories.MovieRepository;
import com.google.mooveaze.view.MovieBinder;

public class MoviesActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private ProgressDialog progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(false) {
            progress = ProgressDialog.show(this, "", "Initial movie sync in progress. Please wait...", true, false);

            new SyncTask() {
                protected void onPostExecute(Integer integer) {
                    progress.cancel();
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
                MovieRepository.Columns._ID,
                MovieRepository.Columns.NAME,
                MovieRepository.Columns.RELEASED,
                MovieRepository.Columns.RATING,
                MovieRepository.Columns.IMAGE
        };

        int[] to = new int[]{
                R.id.movie_id,
                R.id.movie_name,
                R.id.movie_released,
                R.id.movie_rating,
                R.id.movie_image
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.movie, cursor, columns, to);
        cursorAdapter.setViewBinder(new MovieBinder());
        setListAdapter(cursorAdapter);
        getListView().setOnItemClickListener(this);
        Redbox.getInstance();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String movieId = ((TextView) view.findViewById(R.id.movie_id)).getText().toString();
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.movies, menu);
        return true;
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case R.id.kiosks:
                startActivity(new Intent(this, KiosksActivity.class));
                return true;
        }

        return false;
    }
}
