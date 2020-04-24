package com.shuklaAnurag0006.AdivasiRadio;

import com.shuklaAnurag0006.myapplication.R;

import java.util.ArrayList;

public class DataUtils {

    public static final String DEVELOPER_KEY = "AIzaSyCs1Zwwxo2yuAkML624yxv46zyVxPgDOjU";

    public static  ArrayList<String> getURLs () {
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("6CpFXoE5emI");
        urls.add("yD_amVU9Tg4");
        urls.add("KebYUSVJ8uE");
        return urls;
    }



    public static ArrayList<BookData> getBooks() {

        ArrayList<BookData> books = new ArrayList<BookData>();

        ArrayList<BookPage> bookPages = new ArrayList<BookPage>();

        bookPages.add(new BookPage("गेडा तेबातया वेनेहानो अयता|\n" +
                "बातया वेनेहानो!\n" +
                "\u0010प तुन साफ़ \u0015कयानता|\n" +
                "सफ तु\n" +
                "\u0018ता!\n", R.drawable.page_one));
        bookPages.add(new BookPage("बासके नरला थाना थ\u0018दाना मार ओ अ\u0018ता ,\n" +
                "असके सरोज तीजाइनाता ?\n" +
                "जोगा तीजाइनता!", R.drawable.page_two));
        bookPages.add(new BookPage("अयोय गेडा पा धी| \n उर\u0014-उर!\u0014 उर\u0014-उर\u0014!",
                R.drawable.page_three));
        bookPages.add(new BookPage("असके \u0015गदाला \u0015पटेआनी कोलाया दा \u0015तदाना,\n" +
                "कारना अ\u0018ता असकेभोर तीजाइ\u0018ता तोर?\n" +
                "\u0015व सग हानी पूड\u0015यग \u0015त\u0018तागा!\n", R.drawable.page_four));

        String authorName = "Sejal Mehta";
        String date = "20/5/2019";

        books.add(new BookData("अरे, इद सबय बोल तिन्जेतुड", authorName, bookPages, date, R.drawable.book_thumbnail));
        return books;


    }

}
