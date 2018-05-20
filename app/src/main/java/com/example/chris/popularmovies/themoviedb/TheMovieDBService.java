package com.example.chris.popularmovies.themoviedb;

import com.example.chris.popularmovies.themoviedb.model.MovieResultList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDBService {

    @GET("movie/popular")
    Call<MovieResultList> getPopularMovies(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("movie/top_rated")
    Call<MovieResultList> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") Integer page);

}
