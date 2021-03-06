package com.google.mooveaze.controller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import com.google.mooveaze.R;

public class HomeActivity extends TabActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        TabHost tabHost = getTabHost();

        Intent intent = new Intent(this, MoviesActivity.class);
        TabHost.TabSpec spec = tabHost.newTabSpec("movies").setIndicator("Movies").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent(this, KiosksActivity.class);
        spec = tabHost.newTabSpec("kiosks").setIndicator("Kiosks").setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}
