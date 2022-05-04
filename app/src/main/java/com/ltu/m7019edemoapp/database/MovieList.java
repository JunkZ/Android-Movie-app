package com.ltu.m7019edemoapp.database;

import com.ltu.m7019edemoapp.model.Movie;

import java.util.List;

public class MovieList {
    private List<Movie> Movielist;

    public void setMovieList(List<Movie> Movielist) {
        this.Movielist = Movielist;
    }

    public List<Movie> getMovielist() {
        return Movielist;
    }

}
