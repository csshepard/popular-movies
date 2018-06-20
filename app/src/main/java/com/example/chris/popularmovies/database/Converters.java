package com.example.chris.popularmovies.database;

import com.example.chris.popularmovies.themoviedb.model.MovieResult;

public class Converters {

    public static MovieResult convertToMovieResult(UserFavoriteEntry userFavoriteEntry){
        return new MovieResult(
                userFavoriteEntry.getId(),
                userFavoriteEntry.getTitle(),
                userFavoriteEntry.getReleaseDate(),
                userFavoriteEntry.getVoteAverage(),
                userFavoriteEntry.getOverview(),
                userFavoriteEntry.getPosterPath());
    }

    public static UserFavoriteEntry convertToUserFavoriteEntry(MovieResult movieResult){
        return new UserFavoriteEntry(
                movieResult.getId(),
                movieResult.getTitle(),
                movieResult.getReleaseDate(),
                movieResult.getVoteAverage(),
                movieResult.getOverview(),
                movieResult.getPosterPath());
    }
}
