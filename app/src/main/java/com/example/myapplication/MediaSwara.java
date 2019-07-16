package com.example.myapplication;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.DragEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;

public class MediaSwara extends AppCompatActivity {

    CardStackView cardStackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_swara);


        cardStackView = findViewById(R.id.cardStackView);
        CardStackLayoutManager cardStackLayoutManager =  new CardStackLayoutManager(this);
        cardStackLayoutManager.setCanScrollHorizontal(false);
        cardStackLayoutManager.setDirections(Direction.VERTICAL);
        cardStackLayoutManager.setCanScrollVertical(true);

        cardStackView.setLayoutManager(cardStackLayoutManager);

        ArrayList<CardDetail> cardDetails = DataUtils.getArticles();


        cardStackView.setAdapter(new NewsCardAdapter(cardDetails));

    }
}
