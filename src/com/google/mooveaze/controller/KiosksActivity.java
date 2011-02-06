package com.google.mooveaze.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.google.mooveaze.R;
import com.google.mooveaze.lib.GeoLocationTask;
import com.google.mooveaze.lib.Location;
import com.google.mooveaze.lib.LocationTask;
import com.google.mooveaze.lib.Redbox;
import com.google.mooveaze.model.Kiosk;
import com.google.mooveaze.view.KiosksAdapter;

import java.util.List;

public class KiosksActivity extends Activity implements View.OnClickListener {
    protected ProgressDialog progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kiosks);
        findKiosksAtCurrentLocation();
        this.findViewById(R.id.search_kiosks_button).setOnClickListener(this);
    }

    private void findKiosksAtCurrentLocation() {
        progress = ProgressDialog.show(this, "", "Retreiving current location...", true, false);

        new LocationTask() {
            protected void onPostExecute(final Location location) {
                findKiosksAtLocation(location);
            }
        }.execute();
    }

    private void findKiosksAtAddress(String address) {
        progress = ProgressDialog.show(this, "", "Retreiving location for address...", true, false);

        new GeoLocationTask() {
            protected void onPostExecute(final Location location) {
                findKiosksAtLocation(location);
            }
        }.execute(address);
    }

    private void findKiosksAtLocation(final Location location) {
        if(location == null) {
            progress.cancel();

            AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
            alertbox.setMessage("Unable to find location");

            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });

            alertbox.show();
        }
        else {
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
    }

    private void showKiosks(List<Kiosk> kiosks) {
        ListView list = (ListView) findViewById(R.id.kiosks);
        list.setAdapter(new KiosksAdapter(this, kiosks));
    }

    public void onClick(View view) {
        if(view.getId() == R.id.search_kiosks_button) {
            EditText input = (EditText) this.findViewById(R.id.search_kiosks_input);
            String address = input.getText().toString().trim();

            if(address.equals("")) {
                findKiosksAtCurrentLocation();
            }
            else {
                findKiosksAtAddress(address);
            }
        }
    }
}
