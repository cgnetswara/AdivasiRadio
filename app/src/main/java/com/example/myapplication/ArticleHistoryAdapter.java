package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArticleHistoryAdapter extends RecyclerView.Adapter<ArticleHistoryAdapter.ArticleViewHolder> {

    ArrayList<CardDetail> cardDetails;

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public ArticleHistoryAdapter(ArrayList<CardDetail> cardDetails) {
        this.cardDetails = cardDetails;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_history_item, parent, false);
        ArticleViewHolder viewHolder = new ArticleViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        CardDetail card = cardDetails.get(position);
        String heading = card.getArticleHeading();
        TextView textView = holder.itemView.findViewById(R.id.articleHeading);
        textView.setText(heading);

    }

    @Override
    public int getItemCount() {
        return cardDetails.size();
    }
}
