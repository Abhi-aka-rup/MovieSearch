package com.example.moviesearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsFragment extends Fragment {

    private ImageView cover;
    private TextView title;
    private RatingBar rating;
    private TextView plot;
    private RecyclerView cast_rv;

    private String movie_title;
    private String cover_path;
    private String movie_id;

    public MovieDetailsFragment(String movie_title, String cover_path, String movie_id) {
        this.movie_title = movie_title;
        this.cover_path = cover_path;
        this.movie_id = movie_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.moviedetails_fragment_layout, container,false);
        cover = view.findViewById(R.id.moviedetails_cover_iv);
        title = view.findViewById(R.id.moviedetails_title_tv);
        rating = view.findViewById(R.id.moviedetails_rating_rb);
        plot = view.findViewById(R.id.moviedetails_plot_tv);
        cast_rv = view.findViewById(R.id.moviedetails_cast_rv);

        Call<OmdbMoviePojo> callItemDetails = Common.omdb_movieApiService.getItemDetails(movie_title, Common.omdb_api_key);
        callItemDetails.enqueue(new Callback<OmdbMoviePojo>() {
            @Override
            public void onResponse(Call<OmdbMoviePojo> call, Response<OmdbMoviePojo> response) {
                OmdbMoviePojo omdbMoviePojoItem = response.body();

                Picasso.with(view.getContext()).load(cover_path).into(cover);
                title.setText(omdbMoviePojoItem.getTitle());
                rating.setRating(Float.parseFloat(omdbMoviePojoItem.getImdbRating().substring(0,1))/2);
                plot.setText(omdbMoviePojoItem.getPlot());
            }

            @Override
            public void onFailure(Call<OmdbMoviePojo> call, Throwable t) {
                Toast.makeText(view.getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<MovieResponse> callCasts = Common.tmdb_movieApiService.getCast(movie_id, Common.tmdb_api_key);
        callCasts.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<CastCrewPojo> cast = response.body().getCastResults();

                CastAdapter castAdapter = new CastAdapter(cast, view.getContext());
                cast_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
                cast_rv.setAdapter(castAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Sorry :(", Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }
}
