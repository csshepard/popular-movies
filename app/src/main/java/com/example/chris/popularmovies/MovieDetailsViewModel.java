package com.example.chris.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.chris.popularmovies.database.AppDatabase;
import com.example.chris.popularmovies.database.UserFavoriteEntry;

public class MovieDetailsViewModel extends ViewModel {

    private LiveData<UserFavoriteEntry> userFavorite;

    MovieDetailsViewModel(AppDatabase database, int movieId) {
        userFavorite = database.userFavoriteDao().loadUserFavoriteByMovieId(movieId);
    }

    public LiveData<UserFavoriteEntry> getUserFavorite(){
        return userFavorite;
    }
}
