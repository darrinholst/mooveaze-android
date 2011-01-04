package com.google.mooveaze.view;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.google.mooveaze.R;
import com.google.mooveaze.lib.ImageTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MovieBinder implements SimpleCursorAdapter.ViewBinder {
    private Map<View, ImageTask> imageThreads = new HashMap<View, ImageTask>();

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
        final String imageUrl = "http://images.redbox.com/Images/Thumbnails/" + image;

        ImageTask currentTask = imageThreads.get(view);

        if(currentTask != null) {
            currentTask.cancel(true);
        }

        view.setImageBitmap(null);

        ImageTask imageTask = new ImageTask() {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                view.setImageBitmap(bitmap);
            }
        };

        imageTask.execute(imageUrl);
        imageThreads.put(view, imageTask);
    }

}
