package com.example.chris.popularmovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.chris.popularmovies.database.AppDatabase;

class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;

    MainActivityViewModelFactory(AppDatabase database){
        mDb = database;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(mDb);
    }
}
