package com.shuklaAnurag0006.AdivasiRadio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.shuklaAnurag0006.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;

import static com.shuklaAnurag0006.AdivasiRadio.Dashboard.dashboard_activity;

public class BookRead extends AppCompatActivity {

    CardStackView bookPageStackView;
    FloatingActionButton forwardButtonFloating;
    FloatingActionButton backwardButtonFloating;
    FloatingActionButton ttsButtonImageView;
    Boolean TTS_On = false;
    static int currentPagePos;
    Dashboard dashboard = Dashboard.getDashboard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);

        bookPageStackView = findViewById(R.id.bookStackView);
        forwardButtonFloating = findViewById(R.id.forwardArrow);
        backwardButtonFloating = findViewById(R.id.backwardArrow);
        ttsButtonImageView = findViewById(R.id.ttsButton);

        Intent intent = getIntent();
        int bookPos = intent.getIntExtra("currBookNo", -1);
        final ArrayList<BookPage> bookPages = BookSection.books.get(bookPos).getBookpages();

        forwardButtonFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookPageStackView.swipe();
            }
        });

        backwardButtonFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookPageStackView.rewind();
            }
        });

        ttsButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TTS_On) {
                    dashboard_activity.sayText(bookPages.get(currentPagePos).getBookPageText(), TextToSpeech.QUEUE_FLUSH);
                    ttsButtonImageView.setImageResource(R.drawable.tts_pause_icon);
                    TTS_On = true;
                } else{
                    ttsButtonImageView.setImageResource(R.drawable.tts_icon);
                    dashboard_activity.stopSpeaking();
                    TTS_On = false;
                }
            }
        });

        CardStackListener listener = new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }

            @Override
            public void onCardSwiped(Direction direction) {

            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {

            }

            @Override
            public void onCardAppeared(View view, int position) {

                currentPagePos = position;

            }

            @Override
            public void onCardDisappeared(View view, int position) {
                dashboard_activity.stopSpeaking();
                ttsButtonImageView.setImageResource(R.drawable.tts_icon);
                TTS_On = false;

            }
        };

        CardStackLayoutManager cardStackLayoutManager =  new CardStackLayoutManager(this, listener);
        cardStackLayoutManager.setCanScrollHorizontal(false);
        cardStackLayoutManager.setDirections(Direction.VERTICAL);
        cardStackLayoutManager.setCanScrollVertical(true);

        bookPageStackView.setLayoutManager(cardStackLayoutManager);

        bookPageStackView = findViewById(R.id.bookStackView);


        bookPageStackView.setAdapter(new BookPageAdapter(bookPages));

    }

    @Override
    protected void onStop() {
        super.onStop();
        dashboard.stopSpeaking();
    }
}
