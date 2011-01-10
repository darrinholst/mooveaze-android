package com.google.mooveaze.lib;

import com.google.mooveaze.model.Kiosk;
import com.google.mooveaze.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public abstract class RedboxApi {
    protected RestClient client;
    protected String key;

    public RedboxApi(RestClient client, String key) {
        this.client = client;
        this.key = key;
    }

    protected abstract void addMovieDetails(Movie movie);

    protected abstract List<Kiosk> findKiosksAt(Location location);

    protected abstract RestClient.Header[] getPostHeaders();

    protected JSONObject post(String endpoint, JSONObject entity) {
        try {
            return new JSONObject(rawPost(endpoint, entity));
        }
        catch(JSONException e) {
            Log.error(e);
            return new JSONObject();
        }
    }

    protected String rawPost(String endpoint, JSONObject entity) {
        Log.debug("Posting to " + endpoint);
        RestClient.Response response = client.post(endpoint, entity.toString(), getPostHeaders());

        if(response.statusCode == 200) {
            Log.debug(response.entity);
            return response.entity;
        }
        else {
            Log.debug("Status code " + response.statusCode);
            return "{}";
        }
    }

    protected String join(JSONArray starring) throws Exception {
        StringBuffer sb = new StringBuffer();
        String separator = "";

        for(int i = 0; i < starring.length(); i++) {
            sb.append(separator).append(starring.getString(i));
            separator = ", ";
        }

        return sb.toString();
    }
}
