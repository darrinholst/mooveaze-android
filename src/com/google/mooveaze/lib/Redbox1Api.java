package com.google.mooveaze.lib;

import com.google.mooveaze.model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Redbox1Api extends RedboxApi {
    private static final String MOVIE_DETAILS_URL = "http://www.redbox.com/data.svc/Title";

    public Redbox1Api(RestClient client, String key) {
        super(client, key);
    }

    public void addMovieDetails(Movie movie) {
        try {
            JSONObject json = new JSONObject();
            json.put("type", "Title");
            json.put("pk", "ID");
            JSONArray statements = new JSONArray();
            JSONObject statement = new JSONObject();
            JSONObject filters = new JSONObject();
            filters.put("ID", movie.getId());
            statement.put("filters", filters);
            statements.put(statement);
            json.put("statements", statements);
            json.put("__K", key);

            String response = rawPost(MOVIE_DETAILS_URL, json);
            Pattern pattern = Pattern.compile("\\{.*?\\:(.*)\\}");
            Matcher matcher = pattern.matcher(response);

            if(matcher.matches()) {
                JSONObject data = new JSONObject(matcher.group(1));
                movie.setDescription(data.getString("Desc"));
                movie.setActors(data.getString("Actors"));
                movie.setRunningTime(data.getString("RunningTime"));
                movie.setGenres(data.getString("Genre"));
            }
        }
        catch(Exception e) {
            Log.error(e);
        }
    }

    @Override
    protected RestClient.Header[] getPostHeaders() {
        return new RestClient.Header[]{
                new RestClient.Header("X-Requested-With", "XMLHttpRequest"),
                new RestClient.Header("Content-Type", "application/json")
        };

    }
}
