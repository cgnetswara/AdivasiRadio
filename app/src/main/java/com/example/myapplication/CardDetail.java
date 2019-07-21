package com.example.myapplication;

import android.media.MediaPlayer;

import java.io.IOException;

public class CardDetail {

    private final int NO_IMAGE = -1;
    private String mAudioURL;
    private String mArticleHeading;
    private String mArticle;
    MediaPlayer mediaPlayer;

    CardDetail(String articleHeading, String article, String audioURL) {
        mAudioURL = audioURL;
        mArticleHeading = articleHeading;
        mArticle = article;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    CardDetail(String articleHeading, String article) {
        mArticleHeading = articleHeading;
        mArticle = article;
    }

    public String getAudioUrl() {
        return mAudioURL;
    }

    public String getArticleHeading() {
        return mArticleHeading;
    }

    public String getArticle() {
        return mArticle;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}
