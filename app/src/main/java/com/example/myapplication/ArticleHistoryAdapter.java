package com.example.myapplication;

import android.content.Intent;
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

    }

    @Override
    public int getItemCount() {
        return cardDetails.size();
    }
}
