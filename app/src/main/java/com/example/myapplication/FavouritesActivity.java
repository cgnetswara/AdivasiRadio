package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import data.ArticleDbHelper;
import data.DatabaseContract;

public class FavouritesActivity extends AppCompatActivity {

    static ArrayList<CardDetail> cardDetails;
    RecyclerView favouriteArticlesRecyclerView;
    static FavouriteActivityAdapter adapter;

    static TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        favouriteArticlesRecyclerView = findViewById(R.id.favouriteArticlesRecyclerView);
        favouriteArticlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyView = findViewById(R.id.emptyView);
        cardDetails = new ArrayList<CardDetail>();

        ArticleDbHelper dbHelper = new ArticleDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DatabaseContract.ArticleEntry.COLUMN_IS_FAVOURITE + " = ?";
        String[] selectionArgs = { "1" };


        Cursor cursor = db.query(DatabaseContract.ArticleEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        while(cursor.moveToNext()) {

            cardDetails.add(new CardDetail(cursor.getString(cursor.getColumnIndex(DatabaseContract.ArticleEntry.COLUMN_ARTICLE_HEADING)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.ArticleEntry.COLUMN_ARTICLE_CONTENT)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.ArticleEntry.COLUMN_AUDIO_RES_URL)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.ArticleEntry.COLUMN_ARTICLE_URL))));

        }

        if (cardDetails.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        }

        adapter = new FavouriteActivityAdapter(cardDetails);
        favouriteArticlesRecyclerView.setAdapter(adapter);

    }
}
