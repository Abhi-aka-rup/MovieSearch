package com.example.moviesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.castCrewViewHolder> {
    private List<CastCrewPojo> results;
    private Context context;

    public CastAdapter(List<CastCrewPojo> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public castCrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_fragment_item,parent,false);
        return new castCrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull castCrewViewHolder holder, int position) {
        Picasso.with(context).load(Common.person_url + results.get(position).getProfilePath()).into(holder.pic);
        holder.name.setText(results.get(position).getName());
        holder.role.setText(results.get(position).getCharacter());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class castCrewViewHolder extends RecyclerView.ViewHolder{
        private ImageView pic;
        private TextView name;
        private TextView role;

        public castCrewViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.castcrew_pic_cv);
            name = itemView.findViewById(R.id.castcrew_name_tv);
            role = itemView.findViewById(R.id.castcrew_role_tv);
        }
    }
}