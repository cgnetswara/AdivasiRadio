package com.example.myapplication;

import android.content.ContentValues;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

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
    FloatingActionButton forwardButtonFloating;
    FloatingActionButton backwardButtonFloating;
    FloatingActionButton ttsButtonImageView;
    public static MediaPlayer mediaPlayer;
    public static ImageView currImageView;
    ImageView backActivityImageView;
    ImageView favouriteButton;
    static Boolean playerInitialised = false;
    Boolean TTS_On = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_swara);


        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        final CardView loadingLayout = findViewById(R.id.loadingCard);
        forwardButtonFloating = findViewById(R.id.forwardArrow);
        backwardButtonFloating = findViewById(R.id.backwardArrow);
        ttsButtonImageView = findViewById(R.id.ttsButton);
        backActivityImageView = findViewById(R.id.backActivityImageView);
        favouriteButton = findViewById(R.id.favourite_button1);

        if(!isNetworkAvailable()) {
            LinearLayout noInternet = findViewById(R.id.no_internet_view);
            noInternet.setVisibility(View.VISIBLE);
            return;
        }

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
                CardDetail card = cardDetails.get(position);
                favourite = card.getIs_favourite();
                Log.i("I am inside", favourite + "");
                if (card.getIs_favourite()) {
                    favouriteButton.setImageResource(R.drawable.ic_like_1);
                } else {
                    favouriteButton.setImageResource(R.drawable.ic_heart);
                }
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                Log.i("position", position + " " + (cardDetails.size()-1));
                insertIntoDatabase(cardDetails.get(position));
                mediaPlayer.stop();
                dashboard_activity.stopSpeaking();
                ttsButtonImageView.setImageResource(R.drawable.tts_icon);
                TTS_On = false;
                if (currImageView != null) currImageView.setImageResource(R.drawable.play_button);
                playerInitialised = false;

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

        forwardButtonFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStackView.swipe();
            }
        });

        backwardButtonFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStackView.rewind();
            }
        });

        ttsButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TTS_On) {
                    dashboard_activity.sayText(cardDetails.get(currentCardPos).getArticle(), TextToSpeech.QUEUE_FLUSH);
                    ttsButtonImageView.setImageResource(R.drawable.tts_pause_icon);
                    TTS_On = true;
                } else{
                    ttsButtonImageView.setImageResource(R.drawable.tts_icon);
                    dashboard_activity.stopSpeaking();
                    TTS_On = false;
                }
            }
        });


        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favourite) {
                    favouriteButton.setImageResource(R.drawable.ic_like_1);
                    favourite = true;
                } else {
                    favouriteButton.setImageResource(R.drawable.ic_heart);
                    favourite = false;
                }
            }
        });

        backActivityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(mediaSwara_activity);
            }
        });

        PageDownloader downloader = new PageDownloader();
        downloader.execute();

        cardStackView.setAdapter(new NewsCardAdapter(cardDetails));

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    public void insertIntoDatabase(CardDetail card) {

        ContentValues values = new ContentValues();
        values.put(ArticleEntry.COLUMN_ARTICLE_CONTENT, card.getArticle());
        values.put(ArticleEntry.COLUMN_ARTICLE_HEADING, card.getArticleHeading());
        values.put(ArticleEntry.COLUMN_ARTICLE_URL, card.getArticleURL());
        values.put(ArticleEntry.COLUMN_AUDIO_RES_URL, card.getAudioUrl());
        if (favourite) {
            values.put(ArticleEntry.COLUMN_IS_FAVOURITE, 1);
            card.setIs_favourite(true);
        } else {
            values.put(ArticleEntry.COLUMN_IS_FAVOURITE, 0);
        }
        long index = db.insert(ArticleEntry.TABLE_NAME, null, values);
        Log.i("databaseInfo", index + "");

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
            dashboard_activity.stopSpeaking();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
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
                        articles.add(new CardDetail(hLink.text(), p.get(0).text(), audioLink.attr("src"), e.selectFirst("a").attr("href")));
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
                Log.i("leeength", cardDetails.size() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
