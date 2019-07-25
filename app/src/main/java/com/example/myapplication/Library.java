package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Library extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        CardView bookButton = findViewById(R.id.bookCardView);
        CardView articleButton = findViewById(R.id.recentCardView);
        CardView favouriteSection = findViewById(R.id.favouriteCardView);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookSection.class);
                startActivity(intent);
            }
        });

        articleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecentArticles.class);
                startActivity(intent);
            }
        });

        favouriteSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                startActivity(intent);
            }
        });



    }
}
