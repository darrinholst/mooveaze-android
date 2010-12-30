package com.google.moovease.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GenresActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Genres tab");
        setContentView(textview);
    }
}
