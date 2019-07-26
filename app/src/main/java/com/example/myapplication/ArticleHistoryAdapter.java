package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import data.ArticleDbHelper;
import data.DatabaseContract;
import data.DatabaseContract.ArticleEntry;

public class ArticleHistoryAdapter extends RecyclerView.Adapter<ArticleHistoryAdapter.ArticleViewHolder> {

    ArrayList<CardDetail> cardDetails;

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ViewGroup parent;

        public ArticleViewHolder(@NonNull View itemView, ViewGroup parent) {
            super(itemView);
            this.itemView = itemView;
            this.parent = parent;
        }
    }

    public ArticleHistoryAdapter(ArrayList<CardDetail> cardDetails) {
        this.cardDetails = cardDetails;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_history_item, parent, false);
        ArticleViewHolder viewHolder = new ArticleViewHolder(v, parent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticleViewHolder holder, final int position) {
        final CardDetail card = cardDetails.get(position);
        String heading = card.getArticleHeading();
        TextView textView = holder.itemView.findViewById(R.id.articleHeading);
        Button deleteButton = holder.itemView.findViewById(R.id.delete_button);
        textView.setText(heading);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.parent.getContext(), History_card_detail.class);
                intent.putExtra("heading", card.getArticleHeading());
                intent.putExtra("article", card.getArticle());

                holder.parent.getContext().startActivity(intent);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleDbHelper dbHelper = new ArticleDbHelper(holder.parent.getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String selection = ArticleEntry.COLUMN_AUDIO_RES_URL + "=?";
                String[] selectionArgs = { card.getAudioUrl()};
                int deletedRows = db.delete(ArticleEntry.TABLE_NAME, selection, selectionArgs);

                Log.i("deletedd", deletedRows + "");

                Toast.makeText(holder.parent.getContext(), "Article deleted", Toast.LENGTH_LONG).show();
                RecentArticles.adapter.notifyDataSetChanged();
                cardDetails.remove(position);

            }
        });


    }

    @Override
    public int getItemCount() {
        return cardDetails.size();
    }
}
