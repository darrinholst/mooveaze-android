package com.google.moovease.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MoviesActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Movies tab");
        setContentView(textview);
    }
}
