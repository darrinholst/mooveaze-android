package com.google.mooveaze.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import com.google.mooveaze.R;
import com.google.mooveaze.lib.Location;
import com.google.mooveaze.lib.LocationTask;
import com.google.mooveaze.lib.Redbox;
import com.google.mooveaze.model.Kiosk;
import com.google.mooveaze.view.KiosksAdapter;

import java.util.List;

public class KiosksActivity extends Activity {
    private ProgressDialog progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(true) {
            progress = ProgressDialog.show(this, "", "Retreiving current location...", true, false);

            new LocationTask() {
                protected void onPostExecute(final Location location) {
                    progress.setMessage("Finding kiosks...");

                    new AsyncTask<Void, Void, List<Kiosk>>() {
                        protected List<Kiosk> doInBackground(Void... voids) {
                            return Redbox.getInstance().findKiosksAt(location);
                        }

                        protected void onPostExecute(List<Kiosk> kiosks) {
                            progress.cancel();
                            showKiosks(kiosks);
                        }
                    }.execute();
                }
            }.execute();
        }
    }

    private void showKiosks(List<Kiosk> kiosks) {
        setContentView(R.layout.kiosks);
        ListView list = (ListView) findViewById(R.id.kiosks);
        list.setAdapter(new KiosksAdapter(this, kiosks));
    }
}
