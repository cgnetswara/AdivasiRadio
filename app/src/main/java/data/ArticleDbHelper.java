package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import data.DatabaseContract.ArticleEntry;

public class ArticleDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "ArticlesData.db";
    public static final int DATABASE_VERSION = 1;

    public ArticleDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ARTICLES_TABLE =  "CREATE TABLE " + DatabaseContract.ArticleEntry.TABLE_NAME + " ("
                + ArticleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ArticleEntry.COLUMN_ARTICLE_URL + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_HEADING + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_CONTENT + " INTEGER NOT NULL, "
                + ArticleEntry.COLUMN_IS_FAVOURITE + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_ARTICLES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
