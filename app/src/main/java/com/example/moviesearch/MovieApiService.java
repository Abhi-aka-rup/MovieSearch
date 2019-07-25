package com.example.moviesearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/top_rated?language=en-US&page=1")
    Call<MovieResponse> getTopRated(@Query("api_key") String api_key);

    @GET("movie/now_playing?language=en-US&page=1")
    Call<MovieResponse> getNewReleases(@Query("api_key") String api_key);

    @GET("search/movie")
    Call<MovieResponse> getSearchTitle(@Query("query") String title, @Query("api_key") String api_key);

    @GET("discover/movie?vote_average.gte=6&vote_count.gte=500")
    Call<MovieResponse> getSearchYear(@Query("primary_release_year") String primary_release_year, @Query("api_key") String api_key);

    @GET("/?plot=full")
    Call<OmdbMoviePojo> getItemDetails(@Query("t") String title, @Query("apikey") String apikey);

    @GET("movie/{id}/casts")
    Call<MovieResponse> getCast(@Path("id") String movie_id, @Query("api_key") String api_key);
}
