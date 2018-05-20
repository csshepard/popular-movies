package com.example.chris.popularmovies.themoviedb.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResultList {

    @SerializedName("page")
    private Integer page;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("results")
    private List<MovieResult> movieResults;

    MovieResultList(){

    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieResult> getMovieResults() {
        return movieResults;
    }

    public void setMovieResults(List<MovieResult> movieResults) {
        this.movieResults = movieResults;
    }

}
