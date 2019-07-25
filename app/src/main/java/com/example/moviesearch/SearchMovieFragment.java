package com.example.moviesearch;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMovieFragment extends Fragment {
    private EditText search_query;
    private int option = 0;
    private ImageButton search_btn;

    private RecyclerView searchRecyclerView;
    List<TmdbMoviePojo> searchResult;
    private Context context;

    public SearchMovieFragment(Context context, int option) {
        this.context = context;
        this.option = option;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_movie_fragment, container, false);
        search_query = view.findViewById(R.id.search_query_et);
        searchRecyclerView = view.findViewById(R.id.search_movie_rv);
        search_btn = view.findViewById(R.id.search_btn);

        if(option==1){
            search_query.setHint("Movie Title....");
            search_query.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        else if(option==2) {
            search_query.setHint("Release Year.....");
            search_query.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        search_btn.setOnClickListener(this::searchMovie);
        return view;
    }

    public void searchMovie(View view) {
        if (search_query.getText().toString().length() > 0) {
            Call<MovieResponse> callSearchMovie = null;
            if (option == 1)
                callSearchMovie = Common.tmdb_movieApiService.getSearchTitle(search_query.getText().toString(), Common.tmdb_api_key);
            else if (option == 2) {
                callSearchMovie = Common.tmdb_movieApiService.getSearchYear(search_query.getText().toString(), Common.tmdb_api_key);
            }

            callSearchMovie.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<TmdbMoviePojo> searchResult = response.body().getTmdbMoviePojoResults();
                    searchResult.removeIf(item -> !(Arrays.asList(Common.lang).contains(item.getOriginalLanguage()) && item.getVoteCount() >= 1000));
                    if (searchResult.size() == 0)
                        Toast.makeText(context, "Sorry :(", Toast.LENGTH_SHORT).show();
                    else{
                        SearchMovieAdapter searchMovieAdapter = new SearchMovieAdapter(searchResult, context);
                        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        searchRecyclerView.setAdapter(searchMovieAdapter);
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else
            Toast.makeText(context, "Please enter search query", Toast.LENGTH_SHORT).show();
    }
}
