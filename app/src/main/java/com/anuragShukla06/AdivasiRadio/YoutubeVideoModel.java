package com.anuragShukla06.AdivasiRadio;

/**
 * Created by sonu on 10/11/17.
 * class to set and get the video id, title and duration for a video
 */

public class YoutubeVideoModel {
    private String videoId, title,duration;

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public YoutubeVideoModel(String videoId, String title) {
        this.videoId = videoId;
        this.title = title;
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