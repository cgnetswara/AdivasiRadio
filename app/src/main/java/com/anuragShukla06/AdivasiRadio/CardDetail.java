package com.anuragShukla06.AdivasiRadio;

import android.media.MediaPlayer;

import java.io.IOException;

public class CardDetail {

    private final int NO_IMAGE = -1;
    private String mAudioURL;
    private String mArticleHeading;
    private String mArticle;
    private String mArticleURL;


    public void setIs_favourite(Boolean is_favourite) {
        this.is_favourite = is_favourite;
    }

    private Boolean is_favourite = false;
    MediaPlayer mediaPlayer;

    CardDetail(String articleHeading, String article, String audioURL, String articleURL) {
        mAudioURL = audioURL;
        mArticleHeading = articleHeading;
        mArticle = article;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mArticleURL = articleURL;
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

    public String getArticleURL() { return mArticleURL; }

    public Boolean getIs_favourite() {
        return is_favourite;
    }

}
