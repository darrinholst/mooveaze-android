package com.google.mooveaze.lib;

import com.google.mooveaze.model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Redbox2 {
    private static final String MOVIE_DETAILS_URL = "http://www.redbox.com/api/Product/GetDetail/";
    private static Redbox2 instance;

    private RestClient client;
    private String key;

    public static Redbox2 getInstance() {
        if(instance == null) {
            instance = new Redbox2();
        }

        return instance;
    }

    private Redbox2() {
        client = new RestClient();
    }

    private JSONObject post(String endpoint, JSONObject entity) {
        RestClient.Header[] headers = {
                new RestClient.Header("__K", getKey()),
                new RestClient.Header("X-Requested-With", "XMLHttpRequest"),
                new RestClient.Header("Content-Type", "application/json")
        };

        RestClient.Response response = client.post(endpoint, entity.toString(), headers);

        if(response.statusCode == 200) {
            try {
                return new JSONObject(response.entity);
            }
            catch(Exception e) {
                Log.error(e);
            }
        }

        return new JSONObject();
    }

    public void addMovieDetails(Movie movie) {
        try {
            JSONObject json = new JSONObject();
            json.put("productType", "1");
            json.put("id", movie.getId());
            json.put("descCharLimit", "2000");

            JSONObject details = post(MOVIE_DETAILS_URL, json);
            addMovieDetails(movie, details);
        }
        catch(Exception e) {
            Log.error(e);
        }
    }

    private void addMovieDetails(Movie movie, JSONObject details) {
        try {
            JSONObject d = details.getJSONObject("d");

            if(d.getBoolean("success")) {
                JSONObject data = d.getJSONObject("data");
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

    private String join(JSONArray starring) throws Exception {
        StringBuffer sb = new StringBuffer();
        String separator = "";

        for(int i = 0; i < starring.length(); i++) {
            sb.append(separator).append(starring.getString(i));
            separator = ", ";
        }

        return sb.toString();
    }


    private String getKey() {
        if(key == null) {
            RestClient.Response response = client.get("http://www.redbox.com", new RestClient.Header[]{new RestClient.Header("Cookie", "RB_2.0=1")});
            Pattern pattern = Pattern.compile(".*rb\\.api\\.key *= *[',\"](.*?)[',\"].*", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(response.entity);

            if(matcher.matches()) {
                key = matcher.group(1);
                Log.debug("Found 2.0 key - " + key);
            }
        }

        return key;
    }
}
