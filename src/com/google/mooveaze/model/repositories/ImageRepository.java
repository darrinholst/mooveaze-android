package com.google.mooveaze.model.repositories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import com.google.mooveaze.lib.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageRepository {
    private final static String IMAGE_URL = "http://images.redbox.com/Images/Thumbnails/";

    public Bitmap get(String name) {
        Bitmap image = null;

        try {
            image = getImageFromDisk(name);

            if(image == null) {
                image = getImageFromWeb(name);
            }
        }
        catch(Exception e) {
            Log.error(e);
        }

        return image;
    }

    private Bitmap getImageFromWeb(String name) throws Exception {
        URL url = new URL(IMAGE_URL + name);
        URLConnection connection = url.openConnection();
        Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        writeImageToDisk(name, bitmap);
        return bitmap;
    }

    private void writeImageToDisk(String name, Bitmap bitmap) throws Exception {
        File file = getDiskFile(name);

        if(file != null) {
            Log.debug("writing " + name + " to cache");
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        }
    }

    private File getDiskFile(String name) {
        File root = Environment.getExternalStorageDirectory();

        if(root.canWrite()) {
            File cacheRoot = new File(root, ".mooveaze");

            if(!cacheRoot.exists()) {
                cacheRoot.mkdir();
            }

            return new File(cacheRoot, name);
        }
        else {
            Log.debug(root.getAbsolutePath() + " is not writable");
        }

        return null;
    }

    private Bitmap getImageFromDisk(String name) throws Exception {
        File file = getDiskFile(name);

        if(file != null && file.exists()) {
            Log.debug("Returning " + name + " from cache");
            FileInputStream fileInputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            return bitmap;
        }

        return null;
    }
}
