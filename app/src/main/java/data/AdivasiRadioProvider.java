package data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import data.DatabaseContract.Question_Download_QUEUE;
import data.DatabaseContract.Translation_Upload_QUEUE;

public class AdivasiRadioProvider extends ContentProvider {

    private SQLiteOpenHelper mDbHelper;

    private static final String LOG_TAG = AdivasiRadioProvider.class.getSimpleName();

    private static final int DOWNLOAD= 100;
    private static final int READ_TOP_DOWNLOAD = 101;
    private static final int UPLOAD = 102;
    private static final int UPLOAD_ID = 103;
    private static final int DOWNLOAD_ID = 104;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.PATH_DOWNLOAD_QUESTIONS, DOWNLOAD);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.PATH_DOWNLOAD_QUESTIONS + "/" + DatabaseContract.GET_ROW, READ_TOP_DOWNLOAD);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.PATH_DOWNLOAD_QUESTIONS + "/#", DOWNLOAD_ID);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.PATH_UPLOAD_QUESTIONS, UPLOAD);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.PATH_UPLOAD_QUESTIONS + "/#", UPLOAD_ID);
    }




    @Override
    public boolean onCreate() {
        mDbHelper = new ArticleDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case UPLOAD:
                cursor = db.query(Translation_Upload_QUEUE.TABLE_NAME, null, null, null, null, null, null);
                break;
            case DOWNLOAD:
                cursor = db.query(Question_Download_QUEUE.TABLE_NAME, null, null, null, null, null, null);
                break;
            case READ_TOP_DOWNLOAD:
                cursor = db.query(Question_Download_QUEUE.TABLE_NAME, null, null, null, null, null, null, "1");
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI: " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long rowId;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case DOWNLOAD:
                rowId = db.insert(Question_Download_QUEUE.TABLE_NAME, null, contentValues);
                break;
            case UPLOAD:
                rowId = db.insert(Translation_Upload_QUEUE.TABLE_NAME, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
        if (rowId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, rowId);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        switch (match) {
            case UPLOAD:
                return db.delete(Translation_Upload_QUEUE.TABLE_NAME, null, null);
            case UPLOAD_ID:
                selection = Translation_Upload_QUEUE.QUESTION_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.delete(Translation_Upload_QUEUE.TABLE_NAME, selection, selectionArgs);
            case DOWNLOAD_ID:
                selection = Question_Download_QUEUE.QUESTION_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.delete(Question_Download_QUEUE.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
