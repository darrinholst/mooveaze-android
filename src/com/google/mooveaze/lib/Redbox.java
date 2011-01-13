package com.google.mooveaze.lib;

import com.google.mooveaze.model.Kiosk;
import com.google.mooveaze.model.Movie;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Redbox {
    private static Redbox instance;
    private RedboxApi api;

    public static Redbox getInstance() {
        if(instance == null) {
            instance = new Redbox();
        }

        return instance;
    }

    public static Redbox get20Instance() {
        Redbox redbox = getInstance();

        for(int i = 0; i < 5 && !redbox.is20(); i++) {
            instance = null;
            redbox = getInstance();
        }

        return redbox;
    }

    private boolean is20() {
        return api.is20();
    }

    private Redbox() {
        RestClient client = new RestClient();
        RestClient.Response response = client.get("http://www.redbox.com");
        Pattern pattern = Pattern.compile(".*rb\\.api\\.key *= *[',\"](.*?)[',\"].*", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response.entity);

        if(matcher.matches()) {
            Log.debug("Found api 2 key - " + matcher.group(1));
            api = new Redbox2Api(client, matcher.group(1));
        }
        else {
            pattern = Pattern.compile(".*__K.*value=\"(.*?)\".*", Pattern.DOTALL);
            matcher = pattern.matcher(response.entity);

            if(matcher.matches()) {
                Log.debug("Found api 1 key - " + matcher.group(1));
                api = new Redbox1Api(client, matcher.group(1));
            }
            else {
                throw new RuntimeException("Could not find redbox api key");
            }
        }
    }

    public List<Kiosk> findKiosksAt(Location location) {
        return api.findKiosksAt(location);
    }

    public void addMovieDetails(Movie movie) {
        api.addMovieDetails(movie);
    }

    public List<Movie> getAllMovies() {
        return api.getAllMovies();
    }
}
