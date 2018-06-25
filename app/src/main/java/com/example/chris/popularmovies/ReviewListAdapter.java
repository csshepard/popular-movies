package com.example.chris.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.chris.popularmovies.databinding.ReviewListItemBinding;
import com.example.chris.popularmovies.themoviedb.model.ReviewResult;

import java.util.ArrayList;
import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewsVH> {

    private static final String TAG = ReviewListAdapter.class.getSimpleName();
    private final List<ReviewResult> reviews;


    ReviewListAdapter() {
        this.reviews = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ReviewListItemBinding itemBinding =
                ReviewListItemBinding.inflate(layoutInflater, parent, false);
        return new ReviewsVH(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsVH holder, int position) {
        String reviewAuthor = reviews.get(position).getAuthor();
        String reviewContent = reviews.get(position).getContent();
        holder.binding.reviewAuthor.setText(reviewAuthor);
        holder.binding.reviewContent.setText(reviewContent);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void addAll(List<ReviewResult> reviews){
        for (ReviewResult review: reviews){
            add(review);
        }
    }

    public void add(ReviewResult review){
        this.reviews.add(review);
        notifyItemInserted(reviews.size() - 1);

    }

    private void remove(ReviewResult review){
        int position = reviews.indexOf(review);
        if (position > -1){
            reviews.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while(getItemCount() > 0){
            remove(getItem(0));
        }
    }

    class ReviewsVH extends RecyclerView.ViewHolder {

        private final ReviewListItemBinding binding;

        ReviewsVH(ReviewListItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    ReviewResult getItem(int position) {
        return reviews.get(position);
    }

}
