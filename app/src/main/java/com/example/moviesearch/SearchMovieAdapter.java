package com.example.moviesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.searchMovieViewHolder> {
    private List<TmdbMoviePojo> searchResult;
    private Context context;
    private RelativeLayout search_item;

    public SearchMovieAdapter(List<TmdbMoviePojo> searchResult, Context context) {
        this.searchResult = searchResult;
        this.context = context;
    }

    @NonNull
    @Override
    public searchMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_movie_item, parent,false);
        search_item = view.findViewById(R.id.search_item);
        return new searchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchMovieViewHolder holder, int position) {
        String cover_path = Common.person_url + searchResult.get(position).getPosterPath();
        Picasso.with(context).load(cover_path).into(holder.cover);
        holder.title.setText(searchResult.get(position).getTitle());
        holder.plot.setText(searchResult.get(position).getOverview());

        search_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) context;
                FragmentManager fm = activity.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_movie_frame, new MovieDetailsFragment(searchResult.get(position).getTitle(), cover_path, searchResult.get(position).getId().toString()));
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }

    public class searchMovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView cover;
        private TextView title;
        private TextView plot;

        public searchMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.searchmovie_cover_iv);
            title = itemView.findViewById(R.id.searchmovie_title_tv);
            plot = itemView.findViewById(R.id.searchmovie_plot_tv);
        }
    }
}
