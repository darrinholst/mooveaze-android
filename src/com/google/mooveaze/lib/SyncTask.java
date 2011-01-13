package com.google.mooveaze.lib;

import android.os.AsyncTask;
import com.google.mooveaze.model.GenreMapping;
import com.google.mooveaze.model.Movie;
import com.google.mooveaze.model.repositories.GenreMappingRepository;
import com.google.mooveaze.model.repositories.MovieRepository;

import java.util.List;

public class SyncTask extends AsyncTask<Void, Integer, Integer> {
    protected Integer doInBackground(Void... voids) {
        try {
            Redbox redbox = Redbox.get20Instance();
            List<Movie> movies = redbox.getAllMovies();
            Log.debug("found " + movies.size() + " total titles");
            return addMovies(movies);
        }
        catch(Exception e) {
            Log.error(e);
        }

        return 0;
    }

    private int addMovies(List<Movie> movies) {
        MovieRepository movieRepository = new MovieRepository();
        GenreMappingRepository genreMappingRepository = new GenreMappingRepository();

        int moviesAdded = 0;

        for(Movie movie : movies) {
            if(movieRepository.contains(movie)) {
                break; //all the rest should already be there
            }

            if(movie.getReleaseDays() >= -14) {
                movieRepository.add(movie);

                for(Integer genreId : movie.getGenresIds()) {
                    genreMappingRepository.add(new GenreMapping(genreId, movie.getId()));
                }

                moviesAdded += 1;
            }
        }

        return moviesAdded;
    }
}
