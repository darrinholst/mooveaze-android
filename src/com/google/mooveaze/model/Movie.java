package com.google.mooveaze.model;

import android.database.Cursor;
import com.google.mooveaze.model.repositories.MovieRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class Movie extends Title {
    private int id;
    private String name;
    private List<Integer> genresIds;
    private String genres;
    private Date released;
    private String image;
    private String format;
    private int releaseDays;
    private String rating;
    private String actors;
    private String description;
    private String runningTime;

    public static Movie fromJson(JSONObject json) throws JSONException {
        Movie movie = new Movie();
        movie.setId(json.getInt("ID"));
        movie.setName(json.getString("name"));
        movie.setGenresIds(idsFrom(json.getJSONArray("genreIDs")));
        movie.setReleased(parseDate(json.getString("release")));
        movie.setImage(json.getString("img"));
        movie.setFormat(json.getString("fmt"));
        movie.setReleaseDays(json.getInt("releaseDays"));
        movie.setRating(json.getString("rating"));
        return movie;
    }

    public static Movie fromCursor(Cursor cursor) throws Exception {
        Movie movie = new Movie();

        movie.setId(cursor.getInt(cursor.getColumnIndex(MovieRepository.Columns._ID)));
        movie.setName(cursor.getString(cursor.getColumnIndex(MovieRepository.Columns.NAME)));
        movie.setReleased(new Date(cursor.getLong(cursor.getColumnIndex(MovieRepository.Columns.RELEASED))));
        movie.setImage(cursor.getString(cursor.getColumnIndex(MovieRepository.Columns.IMAGE)));
        movie.setRating(cursor.getString(cursor.getColumnIndex(MovieRepository.Columns.RATING)));
        movie.setActors(cursor.getString(cursor.getColumnIndex(MovieRepository.Columns.ACTORS)));
        movie.setDescription(cursor.getString(cursor.getColumnIndex(MovieRepository.Columns.DESCRIPTION)));
        movie.setRunningTime(cursor.getString(cursor.getColumnIndex(MovieRepository.Columns.RUNNING_TIME)));

        return movie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenresIds(List<Integer> genresIds) {
        this.genresIds = genresIds;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setReleased(Date released) {
        this.released = released;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setReleaseDays(int releaseDays) {
        this.releaseDays = releaseDays;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getGenresIds() {
        return genresIds;
    }

    public String getGenres() {
        return genres;
    }

    public Date getReleased() {
        return released;
    }

    public String getImage() {
        return image;
    }

    public String getFormat() {
        return format;
    }

    public int getReleaseDays() {
        return releaseDays;
    }

    public String getRating() {
        return rating;
    }

    public String getActors() {
        return actors;
    }

    public String getDescription() {
        return description;
    }

    public String getRunningTime() {
        return runningTime;
    }
}
