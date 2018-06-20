package com.example.chris.popularmovies.themoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResultList {

    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private List<VideoResult> videoResults;

    public VideoResultList() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoResult> getVideoResults() {
        return videoResults;
    }

    public void setVideoResults(List<VideoResult> videoResults) {
        this.videoResults = videoResults;
    }
}
