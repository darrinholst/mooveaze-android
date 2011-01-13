package com.google.mooveaze.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Title {
    protected static Date parseDate(String date) throws JSONException {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(date);
        }
        catch(ParseException e) {
            throw new JSONException(e.getMessage());
        }
    }

    protected static List<Integer> idsFrom(JSONArray array) throws JSONException {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for(int i = 0; i < array.length(); i++) {
            ids.add(array.getInt(i));
        }

        return ids;
    }
}
