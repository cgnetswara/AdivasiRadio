package com.shuklaAnurag0006.AdivasiRadio;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shuklaAnurag0006.myapplication.R;

public class NewsCardAdapter extends RecyclerView.Adapter<NewsCardAdapter.CardHolderView> {

    ArrayList<CardDetail> mCardDetailArrayList;
    private Dashboard dashboard_activity = Dashboard.getDashboard();

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
            final ImageView[] targetImageView = new ImageView[1];

            final CardDetail card = mCardDetailArrayList.get(position);
            articleHeadingTextView.setText(card.getArticleHeading());
            articleTextView.setText(card.getArticle());

            final MediaPlayer player = MediaSwara.mediaPlayer;

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    ImageView imageView = MediaSwara.currImageView;
                    imageView.setTag(dashboard_activity.getString(R.string.pause_tag));
                    imageView.setImageResource(R.drawable.pause_button);
                    Log.i("ImageChange", "should change");
                    player.start();
                }
            });

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ImageView imageView = MediaSwara.currImageView;
                    try {
                        imageView.setImageResource(R.drawable.play_button);
                        imageView.setTag(dashboard_activity.getString(R.string.play_tag));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    Log.i("completedMedia", "completed");
                }
            });

            playPauseImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(playPauseImageView.getTag().toString().equals(dashboard_activity.getString(R.string.play_tag))) {
                        if (!MediaSwara.playerInitialised) {
                            try {
                                player.reset();
                                player.setDataSource(card.getAudioUrl());
                                MediaSwara.playerInitialised = true;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        MediaSwara.currImageView = playPauseImageView;
                        playPauseImageView.setImageResource(R.drawable.audio_loading);
                        playPauseImageView.setTag(dashboard_activity.getString(R.string.loading_audio_tag));
                        player.prepareAsync();
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






        }


    }

    @Override
    public int getItemCount() {
        return mCardDetailArrayList.size();
    }

}
