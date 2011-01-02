package com.google.mooveaze.model;

public class GenreMapping {
    private int genreId;
    private int titleId;

    public GenreMapping(int genreId, int titleId) {
        this.genreId = genreId;
        this.titleId = titleId;
    }

    public int getGenreId() {
        return genreId;
    }

    public int getTitleId() {
        return titleId;
    }
}
