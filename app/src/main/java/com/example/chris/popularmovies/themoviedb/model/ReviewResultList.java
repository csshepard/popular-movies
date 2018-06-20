package com.example.chris.popularmovies.themoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResultList {

    @SerializedName("id")
    private Integer id;
    @SerializedName("page")
    private Integer page;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;
    @SerializedName("results")
    private List<ReviewResult> reviewResults;

    public ReviewResultList() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<ReviewResult> getReviewResults() {
        return reviewResults;
    }

    public void setReviewResults(List<ReviewResult> reviewResults) {
        this.reviewResults = reviewResults;
    }
}
