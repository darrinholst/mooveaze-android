package com.google.mooveaze.lib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;

public class ImageTask extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            connection.setUseCaches(true);
            return BitmapFactory.decodeStream(connection.getInputStream());
        }
        catch(Exception e) {
            Log.error(e);
            return null;
        }
    }
}
