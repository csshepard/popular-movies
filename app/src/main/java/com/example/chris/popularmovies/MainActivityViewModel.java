package com.example.chris.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.chris.popularmovies.database.AppDatabase;
import com.example.chris.popularmovies.database.UserFavoriteEntry;

import java.util.List;

class MainActivityViewModel extends ViewModel {

    private LiveData<List<UserFavoriteEntry>> userFavorites;

    MainActivityViewModel(AppDatabase database) {
        userFavorites = database.userFavoriteDao().loadAllUserFavorites();
    }

    public LiveData<List<UserFavoriteEntry>> getUserFavorites(){
        return userFavorites;
    }
}
