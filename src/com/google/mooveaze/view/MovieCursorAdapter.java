package com.google.mooveaze.view;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.google.mooveaze.R;
import com.google.mooveaze.lib.Log;
import com.google.mooveaze.model.repositories.MovieRepository;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieCursorAdapter extends SimpleCursorAdapter {
    private int layout;

    public MovieCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.layout = layout;
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(layout, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        bindImage(view, cursor);
        bindName(view, cursor);
        bindReleasedDate(view, cursor);
        bindRating(view, cursor);
    }

    private void bindImage(View view, Cursor cursor) {
        int imageIndex = cursor.getColumnIndex(MovieRepository.Columns.IMAGE);
        ImageView imageView = (ImageView) view.findViewById(R.id.movie_image);
        String imageUrl = "http://images.redbox.com/Images/Thumbnails/" + cursor.getString(imageIndex);

        new AsyncTask<Object, Void, Bitmap>() {
            public ImageView imageView;

            @Override
            protected Bitmap doInBackground(Object... params) {
                this.imageView = (ImageView) params[1];

                try {
                    URL url = new URL(params[0].toString());
                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(true);
                    return BitmapFactory.decodeStream(connection.getInputStream());
                }
                catch(Exception e) {
                    Log.error(e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }.execute(imageUrl, imageView);
    }

    private void bindName(View view, Cursor cursor) {
        System.out.println("view = " + view);
        int nameIndex = cursor.getColumnIndex(MovieRepository.Columns.NAME);
        String name = cursor.getString(nameIndex);
        System.out.println("name = " + name);
        ((TextView) view.findViewById(R.id.movie_name)).setText(name);
    }

    private void bindReleasedDate(View view, Cursor cursor) {
        int releasedIndex = cursor.getColumnIndex(MovieRepository.Columns.RELEASED);
        String releaseDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date(cursor.getLong(releasedIndex)));
        ((TextView) view.findViewById(R.id.movie_released)).setText(releaseDate);
    }

    private void bindRating(View view, Cursor cursor) {
        int ratingIndex = cursor.getColumnIndex(MovieRepository.Columns.RATING);
        ((TextView) view.findViewById(R.id.movie_rating)).setText(cursor.getString(ratingIndex));
    }
}
