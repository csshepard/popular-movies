package com.example.chris.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chris.popularmovies.databinding.MovieGridItemBinding;
import com.example.chris.popularmovies.themoviedb.model.MovieResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.PostersVH> {

    private static final String TAG = MovieListAdapter.class.getSimpleName();
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
        Uri posterPath = Uri.parse(context.getString(R.string.tmdb_image_base_url)).buildUpon()
                .appendEncodedPath(context.getString(R.string.tmdb_image_w185) + movies.get(position).getPosterPath())
                .build();
        Log.d(TAG, "onBindViewHolder: " + posterPath);
        Picasso.with(context)
                .load(posterPath)
                .placeholder(R.drawable.ic_confirmation_number_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
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

    public void add(MovieResult movie){
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

    MovieResult getItem(int position) {
        return movies.get(position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
