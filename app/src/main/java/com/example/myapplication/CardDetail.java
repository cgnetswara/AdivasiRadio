package com.example.myapplication;

public class CardDetail {

    private final int NO_IMAGE = -1;
    private int mImgRscId = NO_IMAGE;
    private String mArticleHeading;
    private String mArticle;

    CardDetail(int imageResourceId, String articleHeading, String article) {
        mImgRscId = imageResourceId;
        mArticleHeading = articleHeading;
        mArticle = article;
    }

    CardDetail(String articleHeading, String article) {
        mArticleHeading = articleHeading;
        mArticle = article;
    }

    public int getImgRscId() {
        if (mImgRscId == NO_IMAGE) {
            return -1;
        }
        return mImgRscId;
    }

    public String getArticleHeading() {
        return mArticleHeading;
    }

    public String getArticle() {
        return mArticle;
    }
}
