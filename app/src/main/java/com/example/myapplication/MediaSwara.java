package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.internal.CardStackDataObserver;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.UnaryOperator;

public class MediaSwara extends AppCompatActivity {

    CardStackView cardStackView;
    public static MediaSwara mediaSwara_activity;
    ArrayList<CardDetail> cardDetails = DataUtils.getArticles();
    public String URL = "http://www.cgnetswara.org/";
    CardView loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_swara);

        final CardView loadingLayout = findViewById(R.id.loadingCard);

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

            }

            @Override
            public void onCardDisappeared(View view, int position) {
                Log.i("position", position + " " + (cardDetails.size()-1));
                if (position == (cardStackView.getAdapter().getItemCount()-1)) {
                    loadingLayout.setVisibility(View.VISIBLE);
                    PageDownloader downloader = new PageDownloader();
                    downloader.execute();
                }

            }
        };

        mediaSwara_activity = this;
        cardStackView = findViewById(R.id.cardStackView);


        CardStackLayoutManager cardStackLayoutManager =  new CardStackLayoutManager(this, listener);
        cardStackLayoutManager.setCanScrollHorizontal(false);
        cardStackLayoutManager.setDirections(Direction.VERTICAL);
        cardStackLayoutManager.setCanScrollVertical(true);

        cardStackView.setLayoutManager(cardStackLayoutManager);



//        ArrayList<CardDetail> cardDetails = new ArrayList<>();
        if (cardDetails.size() == 0) {
            setContentView(R.layout.emprty_article);
        }



        cardStackView.setAdapter(new NewsCardAdapter(cardDetails));

    }


    private class PageDownloader extends AsyncTask<Void, Void, ArrayList<CardDetail>> {


        @Override
        protected ArrayList<CardDetail> doInBackground(Void... params) {
            ArrayList<CardDetail> articles = new ArrayList<CardDetail>();
            try {
                // Connect to the web site
                Document document = Jsoup.connect(URL).get();
                // Get the html document articles
                Elements articleElements = document.getElementsByClass("report");
                for(Element e: articleElements) {
                    Elements p = e.getElementsByTag("p");
                    Element hLink = document.selectFirst("h3 a");
                    if (p.size() != 0) {
                        articles.add(new CardDetail(hLink.text(), p.get(0).text()));
                        Log.i("this", hLink.text() + " " + p.get(0).text());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return articles;
        }

        @Override
        protected void onPostExecute(ArrayList<CardDetail> nCardDetails) {
            super.onPostExecute(nCardDetails);
            try {
                // if articles length is 0 handle it.
                cardDetails.clear();
                cardDetails.addAll(nCardDetails);
                cardStackView.getAdapter().notifyDataSetChanged();
                loadingLayout = findViewById(R.id.loadingCard);
                loadingLayout.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
