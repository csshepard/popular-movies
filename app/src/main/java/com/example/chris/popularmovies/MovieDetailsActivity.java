package com.example.chris.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.chris.popularmovies.database.AppDatabase;
import com.example.chris.popularmovies.database.Converters;
import com.example.chris.popularmovies.database.UserFavoriteEntry;
import com.example.chris.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.example.chris.popularmovies.themoviedb.model.MovieResult;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    static final String MOVIE_RESULT_TAG = "movieResult";

    private ActivityMovieDetailsBinding binding;

    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        boolean dbActivity = false;

        Context context = getApplicationContext();

        db = AppDatabase.getInstance(context);

        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_RESULT_TAG)) {
            Bundle data = intent.getExtras();
            if (data != null) {
                MovieResult movieResult = data.getParcelable(MOVIE_RESULT_TAG);
                if (movieResult != null) {
                    Uri posterPath = Uri.parse(context.getString(R.string.tmdb_image_base_url)).buildUpon()
                            .appendEncodedPath(context.getString(R.string.tmdb_image_w185) + movieResult.getPosterPath())
                            .build();
                    Log.d(TAG, "onBindViewHolder: " + posterPath);
                    Picasso.with(context)
                            .load(posterPath)
                            .placeholder(R.drawable.ic_confirmation_number_black_24dp)
                            .error(R.drawable.ic_broken_image_black_24dp)
                            .into(binding.moviePoster);
                    binding.setMovieData(movieResult);

                    MovieDetailsViewModelFactory factory = new MovieDetailsViewModelFactory(db, movieResult.getId());
                    final MovieDetailsViewModel viewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);

                    viewModel.getUserFavorite().observe(this, new Observer<UserFavoriteEntry>() {
                        @Override
                        public void onChanged(@Nullable UserFavoriteEntry userFavoriteEntry) {
                            Log.d(TAG, "onChanged: userFavorite");
                            toggleFavoriteSelector(userFavoriteEntry != null);
                        }
                    });

                    binding.favoriteSelected.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            v.setEnabled(false);
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    db.userFavoriteDao().deleteUserFavorite(viewModel.getUserFavorite().getValue());
                                }
                            });
//                            toggleFavoriteSelector(false);

                        }
                    });
                    binding.favoriteUnselected.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            v.setEnabled(false);
                            final UserFavoriteEntry userFavorite = Converters.convertToUserFavoriteEntry(binding.getMovieData());
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    db.userFavoriteDao().insertUserFavorite(userFavorite);
                                }
                            });
//                            toggleFavoriteSelector(true);
                        }
                    });


                }
            }
        } else {
            throw new UnsupportedOperationException("Missing Extra: " + MOVIE_RESULT_TAG);
        }
    }

    private void toggleFavoriteSelector(boolean favorite){
        if (binding != null) {
            if (favorite) {
                binding.favoriteSelected.setVisibility(View.VISIBLE);
                binding.favoriteSelected.setEnabled(true);
                binding.favoriteUnselected.setVisibility(View.GONE);
            } else {
                binding.favoriteUnselected.setVisibility(View.VISIBLE);
                binding.favoriteUnselected.setEnabled(true);
                binding.favoriteSelected.setVisibility(View.GONE);
            }
        }
    }

}
