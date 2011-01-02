package com.google.mooveaze;

import android.app.Application;
import android.content.Context;
import com.google.mooveaze.lib.Log;

public class Mooveaze extends Application {
    private static Mooveaze instance;

    public Mooveaze() {
        Log.debug("starting up application");
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
