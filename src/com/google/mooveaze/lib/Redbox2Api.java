package com.google.mooveaze.lib;

import com.google.mooveaze.model.Movie;
import org.json.JSONObject;

public class Redbox2Api extends RedboxApi {
    private static final String MOVIE_DETAILS_URL = "http://www.redbox.com/api/Product/GetDetail/";

    public Redbox2Api(RestClient client, String key) {
        super(client, key);
    }

    public void addMovieDetails(Movie movie) {
        try {
            JSONObject json = new JSONObject();
            json.put("productType", "1");
            json.put("id", movie.getId());
            json.put("descCharLimit", "2000");

            JSONObject details = post(MOVIE_DETAILS_URL, json).getJSONObject("d");

            if(details.getBoolean("success")) {
                JSONObject data = details.getJSONObject("data");
                movie.setDescription(data.getString("desc"));
                movie.setActors(join(data.getJSONArray("starring")));
                movie.setRunningTime(data.getString("len"));
                movie.setGenres(join(data.getJSONArray("genre")));
            }
        }
        catch(Exception e) {
            Log.error(e);
        }
    }

    protected RestClient.Header[] getPostHeaders() {
        return new RestClient.Header[]{
                new RestClient.Header("__K", key),
                new RestClient.Header("X-Requested-With", "XMLHttpRequest"),
                new RestClient.Header("Content-Type", "application/json")
        };
    }

}
