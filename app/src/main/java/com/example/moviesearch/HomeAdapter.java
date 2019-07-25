package com.example.moviesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.homeViewHolder> {

    private List<TmdbMoviePojo> tmdbMoviePojoResults;
    private Context context;
    private LinearLayout movie_item;

    public HomeAdapter(List<TmdbMoviePojo> tmdbMoviePojoResults, Context context) {
        this.tmdbMoviePojoResults = tmdbMoviePojoResults;
        this.context = context;
    }

    @NonNull
    @Override
    public homeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_item,parent,false);
        movie_item = view.findViewById(R.id.movie_item);
        return new homeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final homeViewHolder holder, final int position) {
        String cover_path = Common.person_url + tmdbMoviePojoResults.get(position).getPosterPath();
        Picasso.with(context).load(cover_path).into(holder.moviePic);
        holder.movieTitle.setText(tmdbMoviePojoResults.get(position).getTitle());

        movie_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) context;
                FragmentManager fm = activity.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_movie_frame, new MovieDetailsFragment(tmdbMoviePojoResults.get(position).getTitle(), cover_path, tmdbMoviePojoResults.get(position).getId().toString()));
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tmdbMoviePojoResults.size();
    }

    public class homeViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviePic;
        private TextView movieTitle;
        public homeViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePic = itemView.findViewById(R.id.movie_cover_iv);
            movieTitle = itemView.findViewById(R.id.movie_title_tv);
        }
    }
}
