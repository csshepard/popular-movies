package com.example.chris.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chris.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.example.chris.popularmovies.themoviedb.model.MovieResult;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    static final String MOVIE_RESULT_TAG = "movieResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        Context context = getApplicationContext();
        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_RESULT_TAG)) {
            Bundle data = intent.getExtras();
            if (data != null) {
                MovieResult movieResult = data.getParcelable(MOVIE_RESULT_TAG);
                if (movieResult != null) {
                    Picasso.with(context)
                            .load(context.getString(R.string.tmdb_image_base_url) + movieResult.getPosterPath())
                            .into(binding.moviePoster);
                    binding.setMovieData(movieResult);
                }
            }
        } else {
            throw new UnsupportedOperationException("Missing Extra: " + MOVIE_RESULT_TAG);
        }
    }
}
