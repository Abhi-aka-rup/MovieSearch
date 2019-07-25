package com.example.moviesearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<TmdbMoviePojo> tmdbMoviePojoResults;

    @SerializedName("cast")
    private List<CastCrewPojo> castResults;

    public List<TmdbMoviePojo> getTmdbMoviePojoResults() {
        return tmdbMoviePojoResults;
    }

    public List<CastCrewPojo> getCastResults() {
        return castResults;
    }
}
