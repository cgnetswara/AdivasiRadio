package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class History_card_detail extends AppCompatActivity {

    TextView recentHeading;
    TextView recentArticle;
    FloatingActionButton ttsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_card_detail);

        recentHeading = findViewById(R.id.recentHeadingTextView);
        recentArticle = findViewById(R.id.recentArticleTextView);
        ttsButton = findViewById(R.id.tts_speak_button);
        Intent intent = getIntent();
        String heading = intent.getStringExtra("heading");
        final String article = intent.getStringExtra("article");

        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dashboard dashboard = Dashboard.getDashboard();
                dashboard.sayText(article, TextToSpeech.QUEUE_FLUSH);
            }
        });

        recentHeading.setText(heading);
        recentArticle.setText(article);


    }
}
