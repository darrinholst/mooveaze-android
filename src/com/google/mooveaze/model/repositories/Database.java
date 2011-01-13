package com.google.mooveaze.model.repositories;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.mooveaze.Mooveaze;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Database extends SQLiteOpenHelper {
    private static final String DB_PATH = "/data/data/com.google.mooveaze/databases/";
    private static final int DB_VERSION = 0;
    private static final String DB_NAME = "mooveaze_" + DB_VERSION;

    private SQLiteDatabase theDatabase;
    private static Database instance;

    public static SQLiteDatabase getInstance() {
        if(instance == null) {
            instance = new Database();
            instance.createDataBase();
        }

        return instance.theDatabase;
    }

    public Database() {
        super(Mooveaze.getContext(), DB_NAME, null, 1);
    }

    private void createDataBase() {
        if(noDatabaseYet()) {
            getReadableDatabase();

            try {
                copyDataBase();
            }
            catch(IOException e) {
                throw new Error("Error copying database");
            }
        }

        openDataBase();
    }

    private boolean noDatabaseYet() {
        try {
            String myPath = DB_PATH + DB_NAME;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
            return false;
        }
        catch(SQLiteException e) {
            return true;
        }
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = Mooveaze.getContext().getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);

        byte[] buffer = new byte[1024];
        int length;

        while((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private void openDataBase() {
        theDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(theDatabase != null) {
            theDatabase.close();
        }

        super.close();
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
