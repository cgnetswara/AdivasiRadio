package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;

import java.util.ArrayList;

import data.ArticleDbHelper;
import data.DatabaseContract.ArticleEntry;

public class RecentArticles extends AppCompatActivity {

    static ArrayList<CardDetail> cardDetails;
    RecyclerView recentArticlesRecyclerView;
    static ArticleHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_articles);

        recentArticlesRecyclerView = findViewById(R.id.recentArticlesRecyclerView);
        recentArticlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardDetails = new ArrayList<CardDetail>();

        ArticleDbHelper dbHelper = new ArticleDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor = db.query(ArticleEntry.TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()) {

            cardDetails.add(new CardDetail(cursor.getString(cursor.getColumnIndex(ArticleEntry.COLUMN_ARTICLE_HEADING)),
                    cursor.getString(cursor.getColumnIndex(ArticleEntry.COLUMN_ARTICLE_CONTENT)),
                    cursor.getString(cursor.getColumnIndex(ArticleEntry.COLUMN_AUDIO_RES_URL)),
                    cursor.getString(cursor.getColumnIndex(ArticleEntry.COLUMN_ARTICLE_URL))));

        }

        adapter = new ArticleHistoryAdapter(cardDetails);
        recentArticlesRecyclerView.setAdapter(adapter);



    }
}
