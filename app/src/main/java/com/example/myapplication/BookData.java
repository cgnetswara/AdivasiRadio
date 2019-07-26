package com.example.myapplication;

import java.util.ArrayList;

public class BookData {

    private String bookName;
    private String authorName;
    private String date;
    private ArrayList<BookPage> bookpages;
    private int imageResourceId;

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<BookPage> getBookpages() {
        return bookpages;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public BookData(String bookName, String authorName, ArrayList<BookPage> bookpages, String date, int imageResourceId) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.date = date;
        this.bookpages = bookpages;
        this.imageResourceId = imageResourceId;
    }
}
