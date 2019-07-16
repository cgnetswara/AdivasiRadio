package com.example.myapplication;

public class BookPage {

    private String mBookPageText;
    private int mImageResId;

    BookPage(String bookPageText, int imageResourceId) {
        mBookPageText = bookPageText;
        mImageResId = imageResourceId;
    }

    String getBookPageText() {
        return mBookPageText;
    }

    int getImageResId() {
        return mImageResId;
    }

}
