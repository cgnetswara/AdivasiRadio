package com.example.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NewsCardAdapter extends RecyclerView.Adapter<NewsCardAdapter.CardHolderView> {

    ArrayList<CardDetail> mCardDetailArrayList;
    private Dashboard dashboard_activity = Dashboard.getDashboard();
    MediaPlayer player = new MediaPlayer();

    public NewsCardAdapter(ArrayList<CardDetail> cardDetailArrayList) {
        mCardDetailArrayList = cardDetailArrayList;
    }

    public class CardHolderView extends RecyclerView.ViewHolder {

        View newsCardView;


        public CardHolderView(@NonNull View itemView) {
            super(itemView);
            newsCardView = itemView;
        }
    }


    @NonNull
    @Override
    public NewsCardAdapter.CardHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardHolderView cardHolderView;


        Log.i("constructorIn", "zero" + mCardDetailArrayList.size());

        Log.i("constructorIn2", "" + mCardDetailArrayList.size());

        View newsCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        cardHolderView = new CardHolderView(newsCardView);

        return cardHolderView;

    }

    @Override
    public void onBindViewHolder(@NonNull CardHolderView holder, int position) {

        if (mCardDetailArrayList.size() != 0) {
            ImageView cardImageView = holder.newsCardView.findViewById(R.id.cardImageTextView);
            TextView articleHeadingTextView = holder.newsCardView.findViewById(R.id.cardHeadingTextView);
            TextView articleTextView = holder.newsCardView.findViewById(R.id.cardArticleTextView);
            final ImageView playPauseImageView = holder.newsCardView.findViewById(R.id.playPauseImageView);

            final CardDetail card = mCardDetailArrayList.get(position);
            articleHeadingTextView.setText(card.getArticleHeading());
            articleTextView.setText(card.getArticle());

            final MediaPlayer player = card.getMediaPlayer();

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    player.start();
                    playPauseImageView.setTag(dashboard_activity.getString(R.string.pause_tag));
                    playPauseImageView.setImageResource(R.drawable.pause_button);
                }
            });

            playPauseImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(playPauseImageView.getTag().toString().equals(dashboard_activity.getString(R.string.play_tag))) {
                        player.prepareAsync();
                        playPauseImageView.setImageResource(R.drawable.audio_loading);
                        playPauseImageView.setTag(dashboard_activity.getString(R.string.loading_tag));
                    }
                    else if (playPauseImageView.getTag().toString().equals(dashboard_activity.getString(R.string.pause_tag))) {
                        player.stop();
                        playPauseImageView.setImageResource(R.drawable.play_button);
                        playPauseImageView.setTag(dashboard_activity.getString(R.string.play_tag));
                    }

                }
            });




//            try {
//                player.release();
//                player = new MediaPlayer();
//                player.setDataSource(card.getAudioUrl());
//                Log.i("audiooo2" + card.getArticleHeading(), card.getAudioUrl());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            holder.newsCardView.findViewById(R.id.readButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dashboard_activity.sayText(card.getArticle(), TextToSpeech.QUEUE_FLUSH);
                }
            });




            cardImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return mCardDetailArrayList.size();
    }

}
