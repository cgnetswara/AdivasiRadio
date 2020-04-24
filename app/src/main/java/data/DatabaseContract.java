package data;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String GET_ROW = "get";

    private DatabaseContract() {};
    public static final String CONTENT_AUTHORITY = "com.shuklaAnurag0006.AdivasiRadio";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_DOWNLOAD_QUESTIONS = "download";
    public static final String PATH_UPLOAD_QUESTIONS= "upload";



    public static final class ArticleEntry implements BaseColumns {

        public static final String TABLE_NAME = "Articles";

        public static final String COLUMN_ARTICLE_URL = "Article_url";
        public static final String COLUMN_ARTICLE_HEADING = "Heading";
        public static final String COLUMN_ARTICLE_CONTENT = "Content";
        public static final String COLUMN_AUDIO_RES_URL = "Audio_url";
        public static final String COLUMN_IS_FAVOURITE = "Favorite";

    }

    public static final class Question_Download_QUEUE implements BaseColumns {

        public  static final String TABLE_NAME = "Question_Download_QUEUE";
        public static final int QUEUE_LIMIT = 100;

        public static final String QUESTION_ID = "Question_id";
        public static final String QUESTION_TEXT= "Question_text";

    }

    public static final class Translation_Upload_QUEUE implements BaseColumns {

        public  static  final String TABLE_NAME = "Translation_Upload_QUEUE";

        public static final String QUESTION_ID = "Question_id";
        public static final String TRANSLATION= "Translation";

    }

}
