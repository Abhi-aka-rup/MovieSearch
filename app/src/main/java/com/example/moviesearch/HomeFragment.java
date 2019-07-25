package com.example.moviesearch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView homeRecyclerView;
    Context context;
    List<TmdbMoviePojo> tmdbTopMovie, tmdbNewMovie;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);

        //Fetching Top Rated
        Call<MovieResponse> callTopRated = Common.tmdb_movieApiService.getTopRated(Common.tmdb_api_key);
        callTopRated.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                tmdbTopMovie = response.body().getTmdbMoviePojoResults();
                tmdbTopMovie.removeIf(item -> !(Arrays.asList(Common.lang).contains(item.getOriginalLanguage()) && item.getVoteCount()>=1000));

                //Fetching New Releases
                Call<MovieResponse> callNewMovie = Common.tmdb_movieApiService.getNewReleases(Common.tmdb_api_key);
                callNewMovie.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        tmdbNewMovie = response.body().getTmdbMoviePojoResults();
                        tmdbNewMovie.removeIf(item -> !(Arrays.asList(Common.lang).contains(item.getOriginalLanguage()) && item.getVoteCount()>=1000));

                        homeRecyclerView = view.findViewById(R.id.main_rated_rv);
                        HomeAdapter topRatedMovie = new HomeAdapter(tmdbTopMovie, context);
                        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        homeRecyclerView.setAdapter(topRatedMovie);

                        homeRecyclerView = view.findViewById(R.id.main_new_rv);
                        HomeAdapter newMovieReleases = new HomeAdapter(tmdbNewMovie, context);
                        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        homeRecyclerView.setAdapter(newMovieReleases);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }
}
