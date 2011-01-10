package com.google.mooveaze.lib;

import android.os.AsyncTask;

public class LocationTask extends AsyncTask<Void, Void, Location> {
    protected Location doInBackground(Void... voids) {
        try {
            Thread.sleep(2000);
        }
        catch(InterruptedException e) {
        }

        return new Location("41.836828", "-94.092407");
    }
}
