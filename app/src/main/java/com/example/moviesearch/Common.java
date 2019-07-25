package com.example.moviesearch;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Common {
    public static String[] lang = {"hi", "en", "ja"};
    public static String person_url = "https://image.tmdb.org/t/p/w200";

    public static String omdb_api_key = "3fb5b3d5";
    public static String tmdb_api_key = "fb7faf7e1ff212aa2e9fb55e97814f0a";

    private static String omdb_base_url = "https://www.omdbapi.com";
    private static String tmdb_base_url = "https://api.themoviedb.org/3/";

    private static Retrofit omdb_retrofit = new Retrofit.Builder()
            .baseUrl(omdb_base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static Retrofit tmdb_retrofit = new Retrofit.Builder()
            .baseUrl(tmdb_base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();;

    public static MovieApiService omdb_movieApiService = omdb_retrofit.create(MovieApiService.class);;
    public static MovieApiService tmdb_movieApiService = tmdb_retrofit.create(MovieApiService.class);;
}
