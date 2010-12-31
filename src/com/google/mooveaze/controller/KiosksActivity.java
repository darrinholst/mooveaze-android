package com.google.mooveaze.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class KiosksActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Kiosks tab");
        setContentView(textview);
    }
}
