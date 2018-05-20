package com.example.chris.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chris.popularmovies.databinding.MovieGridItemBinding;
import com.example.chris.popularmovies.themoviedb.model.MovieResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.PostersVH> {

    private final List<MovieResult> movies;
    private final Context context;
    private ItemClickListener mItemClickListener;


    MovieListAdapter(Context context) {
        this.context = context;
        this.movies = new ArrayList<>();
    }

    @NonNull
    @Override
    public PostersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        MovieGridItemBinding itemBinding =
                MovieGridItemBinding.inflate(layoutInflater, parent, false);
        return new PostersVH(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostersVH holder, int position) {
        Picasso.with(context)
                .load(context.getString(R.string.tmdb_image_base_url) + movies.get(position).getPosterPath())
                .into(holder.binding.moviePoster);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addAll(List<MovieResult> movies){
        for (MovieResult movie : movies){
            add(movie);
        }
    }

    private void add(MovieResult movie){
        this.movies.add(movie);
        notifyItemInserted(movies.size() - 1);

    }

    private void remove(MovieResult movie){
        int position = movies.indexOf(movie);
        if (position > -1){
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while(getItemCount() > 0){
            remove(getItem(0));
        }
    }

    public class PostersVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final MovieGridItemBinding binding;

        PostersVH(MovieGridItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
            binding.moviePoster.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    // convenience method for getting data at click position
    MovieResult getItem(int position) {
        return movies.get(position);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
