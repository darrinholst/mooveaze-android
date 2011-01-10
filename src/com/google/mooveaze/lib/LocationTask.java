package com.google.mooveaze.lib;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;
import com.google.mooveaze.Mooveaze;

public class LocationTask extends AsyncTask<Void, Void, Location> {
    protected Location doInBackground(Void... voids) {
        LocationManager locationService = (LocationManager) Mooveaze.getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationService.getBestProvider(criteria, true);

        if(provider == null) {
            return null;
        }

        android.location.Location location = locationService.getLastKnownLocation(provider);
        return new Location(location.getLatitude(), location.getLongitude());
    }
}
