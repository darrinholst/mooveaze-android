package com.google.mooveaze.model;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Title {
    public boolean isMovie() {
        return false;
    }

    protected static Date parseDate(String date) throws Exception {
        return new SimpleDateFormat("yyyyMMdd").parse(date);
    }

    protected static List<Integer> idsFrom(JSONArray array) throws Exception {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for(int i = 0; i < array.length(); i++) {
            ids.add(array.getInt(i));
        }

        return ids;
    }
}
