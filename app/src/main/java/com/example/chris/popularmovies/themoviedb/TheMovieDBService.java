package com.example.chris.popularmovies.themoviedb;

import com.example.chris.popularmovies.themoviedb.model.MovieResultList;
import com.example.chris.popularmovies.themoviedb.model.ReviewResultList;
import com.example.chris.popularmovies.themoviedb.model.VideoResultList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBService {

    @GET("movie/popular")
    Call<MovieResultList> getPopularMovies(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("movie/top_rated")
    Call<MovieResultList> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("movie/{movie_id}/videos")
    Call<VideoResultList> getMovieVideos(@Path("movie_id") Integer movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResultList> getMovieReviews(@Path("movie_id") Integer movieId, @Query("api_key") String apiKey, @Query("page") Integer page);

}
