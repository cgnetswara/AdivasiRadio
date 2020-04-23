package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import data.DatabaseContract.Question_Download_QUEUE;
import data.DatabaseContract.ArticleEntry;
import data.DatabaseContract.Translation_Upload_QUEUE;
public class ArticleDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "ArticlesData.db";
    public final String LOG_TAG = this.getClass().getSimpleName();
    public static final int DATABASE_VERSION = 5;

    private static final String SQL_CREATE_DOWNLOAD_TABLE = "CREATE TABLE " + Question_Download_QUEUE.TABLE_NAME + " ("
            + Question_Download_QUEUE._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Question_Download_QUEUE.QUESTION_ID + " INTEGER NOT NULL, "
            + Question_Download_QUEUE.QUESTION_TEXT + " TEXT NOT NULL );";

    private static final String SQL_CREATE_UPLOAD_TABLE = "CREATE TABLE " + Translation_Upload_QUEUE.TABLE_NAME + " ("
            + Translation_Upload_QUEUE._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Translation_Upload_QUEUE.QUESTION_ID + " INTEGER NOT NULL, "
            + Translation_Upload_QUEUE.TRANSLATION + " TEXT NOT NULL);";

    public ArticleDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ARTICLES_TABLE =  "CREATE TABLE " + DatabaseContract.ArticleEntry.TABLE_NAME + " ("
                + ArticleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ArticleEntry.COLUMN_ARTICLE_URL + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_HEADING + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_CONTENT + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_AUDIO_RES_URL + " TEXT UNIQUE NOT NULL, "
                + ArticleEntry.COLUMN_IS_FAVOURITE + " INTEGER  DEFAULT 0);";

        db.execSQL(SQL_CREATE_ARTICLES_TABLE);
        db.execSQL(SQL_CREATE_UPLOAD_TABLE);
        db.execSQL(SQL_CREATE_DOWNLOAD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion <= 4) {
            Log.d(LOG_TAG, "DATABASE UPDATE DONE");
//            db.execSQL("DROP TABLE " + Question_Download_QUEUE.TABLE_NAME);
//            db.execSQL("DROP TABLE " + Translation_Upload_QUEUE.TABLE_NAME);
            db.execSQL(SQL_CREATE_UPLOAD_TABLE);
            db.execSQL(SQL_CREATE_DOWNLOAD_TABLE);
        }
    }
}
