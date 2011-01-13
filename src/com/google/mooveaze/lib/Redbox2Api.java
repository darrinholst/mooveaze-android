package com.google.mooveaze.lib;

import com.google.mooveaze.model.Kiosk;
import com.google.mooveaze.model.Movie;
import com.google.mooveaze.model.TitleFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Redbox2Api extends RedboxApi {
    private static final String TITLES_URL = "http://www.redbox.com/api/product/js/__titles";
    private static final String MOVIE_DETAILS_URL = "http://www.redbox.com/api/Product/GetDetail/";
    private static final String LOCATE_KIOSKS_URL = "http://www.redbox.com/api/Store/GetStores/";

    public Redbox2Api(RestClient client, String key) {
        super(client, key);
    }

    public boolean is20() {
        return true;
    }

    protected RestClient.Header[] getPostHeaders() {
        return new RestClient.Header[]{
                new RestClient.Header("__K", key),
                new RestClient.Header("X-Requested-With", "XMLHttpRequest"),
                new RestClient.Header("Content-Type", "application/json")
        };
    }

    public List<Movie> getAllMovies() {
        String titles = get(TITLES_URL);
        return parseGetAllMovies(titles);
    }

    private List<Movie> parseGetAllMovies(String js) {
        TitleFactory titleFactory = new TitleFactory();
        Pattern pattern = Pattern.compile(".*= *(\\[.*\\]).*", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(js);
        JSONArray jsonMovies = new JSONArray();
        List<Movie> movies = new ArrayList<Movie>();

        try {
            if(matcher.matches()) {
                jsonMovies = new JSONArray(matcher.group(1));
            }

            for(int i = 0; i < jsonMovies.length(); i++) {
                try {
                    Movie movie = (Movie) titleFactory.fromJson(jsonMovies.getJSONObject(i));
                    movies.add(movie);
                }
                catch(ClassCastException e) {
                    //not a movie
                }
            }
        }
        catch(JSONException e) {
            Log.error(e);
        }

        return movies;
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

    protected List<Kiosk> findKiosksAt(Location location) {
        ArrayList<Kiosk> kiosks = new ArrayList<Kiosk>();

        try {
            JSONObject json = locationKiosksRequest(location);
            JSONObject details = post(LOCATE_KIOSKS_URL, json).getJSONObject("d");

            if(details.getBoolean("success")) {
                parseLocateKiosksResponse(kiosks, details.getJSONArray("data"));
            }
        }
        catch(Exception e) {
            Log.error(e);
        }

        return kiosks;
    }

    private void parseLocateKiosksResponse(ArrayList<Kiosk> kiosks, JSONArray jsonKiosks) throws JSONException {
        for(int i = 0; i < jsonKiosks.length(); i++) {
            JSONObject jsonKiosk = jsonKiosks.getJSONObject(i);
            JSONObject status = jsonKiosk.getJSONObject("status");

            if(status.getBoolean("online")) {
                Kiosk kiosk = new Kiosk();
                kiosk.setId(jsonKiosk.getInt("id"));

                JSONObject profile = jsonKiosk.getJSONObject("profile");
                kiosk.setName(profile.getString("name"));
                kiosk.setVendor(profile.getString("vendor"));
                kiosk.setAddress(profile.getString("addr"));
                kiosk.setCity(profile.getString("city"));
                kiosk.setState(profile.getString("state"));
                kiosk.setZip(profile.getString("zip"));

                JSONObject proximity = jsonKiosk.getJSONObject("proximity");
                kiosk.setDistance(proximity.getDouble("dist"));
                kiosks.add(kiosk);
            }
        }
    }

    private JSONObject locationKiosksRequest(Location location) throws JSONException {
        JSONObject json = new JSONObject();
        JSONObject filters = new JSONObject();
        JSONObject proximity = new JSONObject();
        proximity.put("lat", location.lat);
        proximity.put("lng", location.lng);
        proximity.put("radius", 50);
        filters.put("proximity", proximity);
        json.put("filters", filters);
        JSONObject resultOptions = new JSONObject();
        resultOptions.put("max", 50);
        resultOptions.put("profile", true);
        resultOptions.put("status", true);
        resultOptions.put("proximity", true);
        resultOptions.put("user", true);
        json.put("resultOptions", resultOptions);
        return json;
    }

}
