package com.shuklaAnurag0006.AdivasiRadio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shuklaAnurag0006.myapplication.R;

import java.util.ArrayList;

public class BookPageAdapter extends RecyclerView.Adapter<BookPageAdapter.BookPageViewHolder> {

    private ArrayList<BookPage> mBookPages;

    class BookPageViewHolder extends RecyclerView.ViewHolder {
        public BookPageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public BookPageAdapter(ArrayList<BookPage> bookPages) {
        mBookPages = bookPages;
    }

    @NonNull
    @Override
    public BookPageAdapter.BookPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bookPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_page, parent, false);

        BookPageViewHolder viewHolder = new BookPageViewHolder(bookPageView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookPageAdapter.BookPageViewHolder holder, int position) {

        BookPage bookPage = mBookPages.get(position);
        View v = holder.itemView;
        TextView bookPageTextView = v.findViewById(R.id.bookPageText);
        ImageView bookPageImageView = v.findViewById(R.id.bookPageImage);

        bookPageImageView.setImageResource(bookPage.getImageResId());
        bookPageTextView.setText(bookPage.getBookPageText());

    }

    @Override
    public int getItemCount() {
        return mBookPages.size();
    }
}
