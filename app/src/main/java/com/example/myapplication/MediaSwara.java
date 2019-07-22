package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import java.util.Objects;
import java.util.function.UnaryOperator;

import data.ArticleDbHelper;
import data.DatabaseContract.ArticleEntry;

import static com.example.myapplication.Dashboard.dashboard_activity;

public class MediaSwara extends AppCompatActivity {

    CardStackView cardStackView;
    public static MediaSwara mediaSwara_activity;
    ArrayList<CardDetail> cardDetails;
    public String BASE_URL = "http://cgnetswara.org/index.php?page=";
    int pageCount = 1;
    static int currentCardPos;
    CardView loadingLayout;
    SQLiteDatabase db;
    Boolean favourite = false;
    MenuItem favouriteStar;
    ImageView forwardButtonImageView;
    ImageView backwardButtonImageView;
    ImageView ttsButtonImageView;
    public static MediaPlayer mediaPlayer;
    public static ImageView currImageView;
    static Boolean playerInitialised = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_swara);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        final CardView loadingLayout = findViewById(R.id.loadingCard);
        forwardButtonImageView = findViewById(R.id.forwardArrow);
        backwardButtonImageView = findViewById(R.id.backwardArrow);
        ttsButtonImageView = findViewById(R.id.ttsButtonImageView);

        mediaPlayer = new MediaPlayer();

        cardDetails = new ArrayList<CardDetail>();
        loadingLayout.setVisibility(View.VISIBLE);

        ArticleDbHelper dbHelper = new ArticleDbHelper(this);
        db = dbHelper.getWritableDatabase();

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
                currentCardPos = position;
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                Log.i("position", position + " " + (cardDetails.size()-1));
                insertIntoDatabase(cardDetails.get(position));
                mediaPlayer.stop();
                currImageView.setImageResource(R.drawable.play_button);
                playerInitialised = false;

                if (favourite) favouriteStar.setIcon(R.drawable.favourite_icon);

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
        cardStackLayoutManager.setDirections(Direction.VERTICAL);
        cardStackLayoutManager.setCanScrollVertical(true);

        cardStackView.setLayoutManager(cardStackLayoutManager);

//        ArrayList<CardDetail> cardDetails = new ArrayList<>();

        forwardButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStackView.swipe();
            }
        });

        backwardButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStackView.rewind();
            }
        });

        ttsButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboard_activity.sayText(cardDetails.get(currentCardPos).getArticle(), TextToSpeech.QUEUE_FLUSH);
            }
        });

        PageDownloader downloader = new PageDownloader();
        downloader.execute();

        cardStackView.setAdapter(new NewsCardAdapter(cardDetails));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.media_swara_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        favouriteStar = item;

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.favourite_button:
                if (!favourite) {
                    item.setIcon(R.drawable.favourite_selected_icon);
                    favourite = true;
                } else {
                    item.setIcon(R.drawable.favourite_icon);
                    favourite = false;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    public void insertIntoDatabase(CardDetail card) {

        ContentValues values = new ContentValues();
        values.put(ArticleEntry.COLUMN_ARTICLE_CONTENT, card.getArticle());
        values.put(ArticleEntry.COLUMN_ARTICLE_HEADING, card.getArticleHeading());
        values.put(ArticleEntry.COLUMN_ARTICLE_URL, card.getArticleURL());
        values.put(ArticleEntry.COLUMN_AUDIO_RES_URL, card.getAudioUrl());
        long index = db.insert(ArticleEntry.TABLE_NAME, null, values);
        Log.i("databaseInfo", index + "");

    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    private class PageDownloader extends AsyncTask<Void, Void, ArrayList<CardDetail>> {


        @Override
        protected ArrayList<CardDetail> doInBackground(Void... params) {
            ArrayList<CardDetail> articles = new ArrayList<CardDetail>();
            try {
                // Connect to the web site
                Document document = Jsoup.connect(BASE_URL + pageCount).get();
                // Get the html document articles
                Elements articleElements = document.getElementsByClass("report");
                for(Element e: articleElements) {
                    Elements p = e.getElementsByTag("p");
                    Element hLink = e.selectFirst("h3 a");
                    Element audioLink = e.selectFirst("audio");
                    Log.i("audiooo"  + hLink.text(), audioLink.attr("src"));
                    if (p.size() != 0) {
                        articles.add(new CardDetail(hLink.text(), p.get(0).text(), audioLink.attr("src"), e.select("h3 a[ref]").text()));
                        Log.i("this", hLink.text() + " " + p.get(0).text());
                    }
                }

                Thread.sleep(1000); // To facilate smooth transition

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return articles;
        }

        @Override
        protected void onPostExecute(ArrayList<CardDetail> nCardDetails) {
            super.onPostExecute(nCardDetails);
            try {
                // if articles length is 0 handle it.
                int topIndex = cardDetails.size();
                cardDetails.addAll(nCardDetails);



                cardStackView.getAdapter().notifyItemRangeChanged(topIndex, cardDetails.size()-topIndex);
                loadingLayout = findViewById(R.id.loadingCard);
                loadingLayout.setVisibility(View.GONE);
                pageCount += 1;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
