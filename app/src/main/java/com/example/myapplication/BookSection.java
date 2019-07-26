package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class BookSection extends AppCompatActivity {

    static ArrayList<BookData> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_section);
//        //TODO: Make it general case to read all the books
//        CardView cardView = findViewById(R.id.book1);
//
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), BookRead.class);
//                startActivity(intent);
//            }
//        });

        books = DataUtils.getBooks();
        RecyclerView booksRecyclerView = findViewById(R.id.bookRecyclerView);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BookSectionAdapter adapter = new BookSectionAdapter(books);
        booksRecyclerView.setAdapter(adapter);

    }
}
