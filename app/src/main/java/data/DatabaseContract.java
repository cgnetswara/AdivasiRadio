package data;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static final class ArticleEntry implements BaseColumns {

        public static final String TABLE_NAME = "Articles";

        public static final String COLUMN_ARTICLE_URL = "Article_url";
        public static final String COLUMN_ARTICLE_HEADING = "Heading";
        public static final String COLUMN_ARTICLE_CONTENT = "Content";
        public static final String COLUMN_AUDIO_RES_URL = "Audio_url";
        public static final String COLUMN_IS_FAVOURITE = "Favorite";

    }

}
