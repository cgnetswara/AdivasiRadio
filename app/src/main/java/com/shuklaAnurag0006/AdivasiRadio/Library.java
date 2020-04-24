package com.shuklaAnurag0006.AdivasiRadio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shuklaAnurag0006.myapplication.R;

public class Library extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        CardView bookButton = findViewById(R.id.bookCardView);
        CardView articleButton = findViewById(R.id.recentCardView);
        CardView favouriteSection = findViewById(R.id.favouriteCardView);
        CardView theWireSection = findViewById(R.id.wireCardView);

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

        theWireSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TheWire.class);
                startActivity(intent);
            }
        });



    }
}
