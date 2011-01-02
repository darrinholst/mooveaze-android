package com.google.mooveaze.model;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class Movie extends Title {
    private int id;
    private String name;
    private List<Integer> genres;
    private Date released;
    private String image;
    private String format;
    private int releaseDays;
    private String rating;
    private String actors;
    private String description;

    public static Movie fromJson(JSONObject json) throws Exception {
        Movie movie = new Movie();
        movie.setId(json.getInt("ID"));
        movie.setName(json.getString("name"));
        movie.setGenres(idsFrom(json.getJSONArray("genreIDs")));
        movie.setReleased(parseDate(json.getString("release")));
        movie.setImage(json.getString("img"));
        movie.setFormat(json.getString("fmt"));
        movie.setReleaseDays(json.getInt("releaseDays"));
        movie.setRating(json.getString("rating"));
        return movie;
    }

    public boolean isMovie() {
        return true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenres(List<Integer> genres) {
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getGenres() {
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
}
