package com.shuklaAnurag0006.AdivasiRadio;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shuklaAnurag0006.myapplication.R;

import java.util.ArrayList;


public class BookSectionAdapter extends RecyclerView.Adapter<BookSectionAdapter.BookViewHolder> {

    private ArrayList<BookData> books;

    class BookViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ViewGroup parent;

        BookViewHolder(@NonNull View itemView, ViewGroup parent) {
            super(itemView);
            this.itemView = itemView;
            this.parent = parent;
        }
    }

    BookSectionAdapter(ArrayList<BookData> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookSectionAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_section_item, parent, false);

        Log.i("infoGotit", books.get(0).getBookName() + "ceffe");
        return new BookViewHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookSectionAdapter.BookViewHolder holder, final int position) {
        final BookData book = books.get(position);


        String bookTitle = book.getBookName();
        String bookAuthor = book.getAuthorName();
        String bookDate = book.getDate();

        TextView bookNameTextView = holder.itemView.findViewById(R.id.bookNameTextView);
        TextView authorTextView = holder.itemView.findViewById(R.id.authorTextView);
        TextView dateTextView = holder.itemView.findViewById(R.id.dateTextView);
        ImageView bookThumbnail = holder.itemView.findViewById(R.id.bookThumbnail);
//        CardView cardView = holder.itemView.findViewById(R.id.bookItemCardView);

        bookNameTextView.setText(bookTitle);
        authorTextView.setText(bookAuthor);
        dateTextView.setText(bookDate);
        bookThumbnail.setImageResource(book.getImageResourceId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.parent.getContext(), BookRead.class);
                intent.putExtra("currBookNo", position);
                holder.parent.getContext().startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}

