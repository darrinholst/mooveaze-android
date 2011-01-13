package com.google.mooveaze.model;

import org.json.JSONException;
import org.json.JSONObject;

public class TitleFactory {
    public Title fromJson(JSONObject json) throws JSONException {
        if(json.getString("productType").equals("1")) {
            return Movie.fromJson(json);
        }
        else {
            return new Title();
        }
    }
}
