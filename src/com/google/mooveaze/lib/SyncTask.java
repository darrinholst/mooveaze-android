package com.google.mooveaze.lib;

import android.os.AsyncTask;
import com.google.mooveaze.model.Genre;
import com.google.mooveaze.model.GenreMapping;
import com.google.mooveaze.model.Movie;
import com.google.mooveaze.model.TitleFactory;
import com.google.mooveaze.model.repositories.GenreMappingRepository;
import com.google.mooveaze.model.repositories.GenreRepository;
import com.google.mooveaze.model.repositories.MovieRepository;
import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyncTask extends AsyncTask<Void, Integer, Integer> {
    private static final String TITLES_URL = "http://reboxed.semicolonapps.com/fumullins.js";

    protected Integer doInBackground(Void... voids) {
        try {
            Log.debug("retrieving titles from " + TITLES_URL);
            RestClient.Response response = new RestClient().get(TITLES_URL);
            Log.debug("Status code is " + response.statusCode);

            if(response.statusCode == 200) {
                JSONArray titles = convertToJson(response.entity);
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

    private JSONArray convertToJson(String js) throws Exception {
        Pattern pattern = Pattern.compile(".*= *(\\[.*\\]).*", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(js);

        if(matcher.matches()) {
            return new JSONArray(matcher.group(1));
        }

        return new JSONArray();
    }
}
