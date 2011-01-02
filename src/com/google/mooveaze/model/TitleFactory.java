package com.google.mooveaze.model;

import org.json.JSONObject;

public class TitleFactory {
    public Title fromJson(JSONObject json) throws Exception {
        if(json.getString("productType").equals("1")) {
            return Movie.fromJson(json);
        }
        else {
            return new Title();
        }
    }
}
