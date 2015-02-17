package com.example.nick.lollipulse;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nicks on 11/30/2014.
 */
class RssFeedDataHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rssfeeds.db";
    public static final String RSS_FEED_TABLE_NAME = "feeds";
    public static final String COLUMN_FEED_ID = "id";
    public static final String COLUMN_FEED_NAME = "name";
    public static final String COLUMN_FEED_ADDRESS = "address";
    private static final String COLUMN_NAME_NULLABLE = null;

    private static final String RSS_FEED_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            RSS_FEED_TABLE_NAME +
            "(id integer primary key autoincrement, "+
            COLUMN_FEED_NAME+ " text unique not null, " +
            COLUMN_FEED_ADDRESS + " text not null);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RSS_FEED_TABLE_NAME;

    public RssFeedDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(RSS_FEED_TABLE_CREATE);
        } catch (SQLiteException e) {
            Log.e(DATABASE_NAME, e.toString());
        }

    }

    public boolean populate(SQLiteDatabase db) {
        Log.e("DataHelper", "populating");
        ContentValues[] data = new ContentValues[3];
        data[0] = new ContentValues();
        data[0].put(COLUMN_FEED_NAME, "Ars Technica");
        data[0].put(COLUMN_FEED_ADDRESS, "http://feeds.arstechnica.com/arstechnica/index");

        data[1] = new ContentValues();
        data[1].put(COLUMN_FEED_NAME, "AnandTech");
        data[1].put(COLUMN_FEED_ADDRESS, "http://www.anandtech.com/rss/");

        data[2] = new ContentValues();
        data[2].put(COLUMN_FEED_NAME, "The Atlantic");
        data[2].put(COLUMN_FEED_ADDRESS, "http://feeds.feedburner.com/TheAtlantic?format=xml");
        try {
            for(int i=0; i < data.length;i++) {
                db.insert(RSS_FEED_TABLE_NAME,COLUMN_NAME_NULLABLE, data[i]);
            }
        } catch(SQLiteException e) {
            Log.e(DATABASE_NAME, e.toString());
            return false;
        }
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
