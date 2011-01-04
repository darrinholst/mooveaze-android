package com.google.mooveaze.view;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.google.mooveaze.R;
import com.google.mooveaze.lib.Log;
import com.google.mooveaze.model.repositories.ImageRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MovieBinder implements SimpleCursorAdapter.ViewBinder {
    private ImageRepository imageRepository = new ImageRepository();
    private Map<View, AsyncTask<String, Void, Bitmap>> imageThreads = new HashMap<View, AsyncTask<String, Void, Bitmap>>();

    public boolean setViewValue(View view, Cursor cursor, int i) {
        switch(view.getId()) {
            case R.id.movie_released:
                bindReleasedDate((TextView) view, cursor.getLong(i));
                return true;

            case R.id.movie_image:
                bindImage((ImageView) view, cursor.getString(i));
                return true;

            default:
                return false;
        }
    }

    private void bindReleasedDate(TextView view, long releasedDate) {
        String releaseDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date(releasedDate));
        view.setText(releaseDate);
    }

    private void bindImage(final ImageView view, final String image) {
        AsyncTask<String, Void, Bitmap> currentTask = imageThreads.get(view);

        if(currentTask != null) {
            currentTask.cancel(true);
        }

        view.setImageBitmap(null);

        AsyncTask<String, Void, Bitmap> newTask = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                return imageRepository.get(strings[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                view.setImageBitmap(bitmap);
            }
        };

        newTask.execute(image);
        imageThreads.put(view, newTask);
    }
}
