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

public class Redbox1Api extends RedboxApi {
    private static final String MOVIE_DETAILS_URL = "http://www.redbox.com/data.svc/Title";
    private static final String LOCATE_KIOSKS_URL = "http://www.redbox.com/ajax.svc/Kiosk/GetNearbyKiosks/";
    private static final String TITLES_URL = "http://www.redbox.com/data.svc/Title/js";

    public Redbox1Api(RestClient client, String key) {
        super(client, key);
    }

    @Override
    protected RestClient.Header[] getPostHeaders() {
        return new RestClient.Header[]{
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
                    Movie movie = (Movie) titleFactory.fromOldJson(jsonMovies.getJSONObject(i));
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

    protected List<Kiosk> findKiosksAt(Location location) {
        ArrayList<Kiosk> kiosks = new ArrayList<Kiosk>();

        try {
            JSONObject json = locateKioskRequest(location);
            JSONObject jsonKiosks = post(LOCATE_KIOSKS_URL, json).getJSONObject("d");
            parseLocateKioskResponse(kiosks, jsonKiosks);
        }
        catch(Exception e) {
            Log.error(e);
        }

        return kiosks;
    }

    private void parseLocateKioskResponse(ArrayList<Kiosk> kiosks, JSONObject jsonKiosks) throws JSONException {
        JSONArray profiles = jsonKiosks.getJSONArray("profiles");
        JSONArray states = jsonKiosks.getJSONArray("states");

        for(int i = 0; i < profiles.length(); i++) {
            try {
                JSONObject jsonState = states.getJSONObject(i);
                JSONArray inventory = null;

                try {
                    inventory = jsonState.getJSONArray("Inv");
                }
                catch(JSONException e) {
                }

                if(jsonState.getBoolean("Online") && (inventory == null || inventory.getJSONObject(0).getInt("Qty") > 0)) {
                    kiosks.add(buildKioskFromJson(profiles.getJSONObject(i)));
                }
            }
            catch(JSONException e) {
                Log.error(e);
                //ignore it
            }
        }
    }

    private JSONObject locateKioskRequest(Location location) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("latitude", location.lat);
        json.put("longitude", location.lng);
        json.put("radius", 50);
        json.put("maxKiosks", 50);
        json.put("mcdOnly", false);
        json.put("getInv", false);
        json.put("pageSize", 50);
        json.put("page", 1);
        json.put("__K", key);
        return json;
    }

    private Kiosk buildKioskFromJson(JSONObject json) throws JSONException {
        Kiosk kiosk = new Kiosk();
        kiosk.setId(json.getInt("ID"));
        kiosk.setAddress(json.getString("Addr"));
        kiosk.setCity(json.getString("City"));
        kiosk.setDistance(json.getDouble("Dist"));
        kiosk.setName(json.getString("Name"));
        kiosk.setState(json.getString("St"));
        kiosk.setZip(json.getString("Zip"));
        kiosk.setVendor(json.getString("Vdr"));
        return kiosk;
    }
}
