package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;

public class BookRead extends AppCompatActivity {

    CardStackView bookPageStackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);

        bookPageStackView = findViewById(R.id.bookStackView);
        CardStackLayoutManager cardStackLayoutManager =  new CardStackLayoutManager(this);
        cardStackLayoutManager.setCanScrollHorizontal(false);
        cardStackLayoutManager.setDirections(Direction.VERTICAL);
        cardStackLayoutManager.setCanScrollVertical(true);

        bookPageStackView.setLayoutManager(cardStackLayoutManager);

        ArrayList<BookPage> bookPages = DataUtils.getBook();


        bookPageStackView.setAdapter(new BookPageAdapter(bookPages));

    }
}
