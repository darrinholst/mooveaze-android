package com.google.mooveaze.lib;

import android.os.AsyncTask;
import com.google.mooveaze.model.Genre;
import com.google.mooveaze.model.GenreMapping;
import com.google.mooveaze.model.Movie;
import com.google.mooveaze.model.TitleFactory;
import com.google.mooveaze.model.repositories.GenreMappingRepository;
import com.google.mooveaze.model.repositories.GenreRepository;
import com.google.mooveaze.model.repositories.MovieRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyncTask extends AsyncTask<Void, Integer, Integer> {
    private static final String TITLES_URL = "http://reboxed.semicolonapps.com/fumullins.js";

    protected Integer doInBackground(Void... voids) {
        try {
            Log.debug("retrieving titles from " + TITLES_URL);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(TITLES_URL));

            int status = response.getStatusLine().getStatusCode();
            Log.debug("Status code is " + status);

            if(status == 200) {
                InputStream inputStream = response.getEntity().getContent();
                JSONArray titles = convertToJson(toString(inputStream));
                Log.debug("found " + titles.length() + " total titles");
                return addMovies(titles);
            }
        }
        catch(Exception e) {
            Log.error(e);
        }

        return 0;
    }

    private int addMovies(JSONArray titles) {
        TitleFactory factory = new TitleFactory();
        MovieRepository movieRepository = new MovieRepository();
        GenreRepository genreRepository = new GenreRepository();
        GenreMappingRepository genreMappingRepository = new GenreMappingRepository();

        int moviesAdded = 0;

        for(int i = 0; i < titles.length(); i++) {
            try {
                Movie movie = (Movie) factory.fromJson(titles.getJSONObject(i));

                if(movie != null && movie.getReleaseDays() >= 0 && !movieRepository.contains(movie)) {
                    movieRepository.add(movie);

                    for(Integer genreId : movie.getGenres()) {
                        genreRepository.add(new Genre(genreId, "" + genreId));
                        genreMappingRepository.add(new GenreMapping(genreId, movie.getId()));
                    }

                    moviesAdded += 1;
                }
            }
            catch(ClassCastException e) {
                //not a movie I guess
            }
            catch(Exception e) {
                Log.error(e);
            }
        }

        return moviesAdded;
    }

    private String toString(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        StringWriter sw = new StringWriter();

        char[] buffer = new char[4096];
        int read;

        try {
            while((read = reader.read(buffer)) > 0) {
                sw.write(buffer, 0, read);
            }
        }
        finally {
            is.close();
        }

        return sw.toString();
    }

    private JSONArray convertToJson(String js) throws Exception {
        Pattern pattern = Pattern.compile(".*= *(\\[.*\\]).*", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(js);

        if(matcher.matches()) {
            return new JSONArray(matcher.group(1));
        }

        return new JSONArray();
    }
}
