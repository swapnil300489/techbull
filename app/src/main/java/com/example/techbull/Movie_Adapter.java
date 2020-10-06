package com.example.techbull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techbull.pojo.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Movie_Adapter extends RecyclerView.Adapter<Movie_Adapter.MovieAdapter_ViewHolder>{

    private Context context;
    private List<Movie.Search> searchList;

    public Movie_Adapter(Context context, List<Movie.Search> searchList) {
        this.context = context;
        this.searchList = searchList;
    }


    @NonNull
    @Override
    public MovieAdapter_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieAdapter_ViewHolder(LayoutInflater.from(context).inflate(R.layout.movieitem_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter_ViewHolder holder, int position) {
        holder.id_movie_txt.setText(searchList.get(position).getTitle());

        Picasso.get().load(searchList.get(position).getPoster()).into(holder.id_movie_img);

        holder.id_year_txt.setText(searchList.get(position).getYear());

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    static class  MovieAdapter_ViewHolder extends RecyclerView.ViewHolder {

        TextView id_movie_txt, id_year_txt;
        ImageView id_movie_img;

        public MovieAdapter_ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_movie_txt = itemView.findViewById(R.id.id_movie_txt);
            id_year_txt = itemView.findViewById(R.id.id_year_txt);
            id_movie_img = itemView.findViewById(R.id.id_movie_img);

        }
    }
}
