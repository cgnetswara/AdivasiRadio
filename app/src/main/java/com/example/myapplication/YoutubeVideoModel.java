package com.example.myapplication;

/**
 * Created by sonu on 10/11/17.
 * class to set and get the video id, title and duration for a video
 */

public class YoutubeVideoModel {
    private String videoId, title,duration;

    public String getVideoId() {
        return videoId;
    }

    public YoutubeVideoModel(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "YoutubeVideoModel{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}