package com.example.nick.lollipulse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicks on 11/30/2014.
 */
public class FeedDAO {

    private SQLiteDatabase database;
    private RssFeedDataHelper dbHelper;
    private String[] allColumns = { "id", RssFeedDataHelper.COLUMN_FEED_NAME,
            RssFeedDataHelper.COLUMN_FEED_ADDRESS};
    
    public FeedDAO(Context context) {
        Log.e("FeedDAO", "Creating database");
        dbHelper = new RssFeedDataHelper(context);

    }

    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
        dbHelper.populate(database);
    }


    public List<Feed> getAllFeeds() {
        List<Feed> feeds = new ArrayList<Feed>();

        Cursor cursor = database.query(RssFeedDataHelper.RSS_FEED_TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Feed f = cursorToFeed(cursor);
            feeds.add(f);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return feeds;
    }

    public void deleteComment(Feed feed) {
        int id = feed.id;
        System.out.println("Comment deleted with id: " + id);
        database.delete(RssFeedDataHelper.RSS_FEED_TABLE_NAME, RssFeedDataHelper.COLUMN_FEED_ID
                + " = " + id, null);
    }

    private Feed cursorToFeed(Cursor cursor) {
        Feed feed = new Feed();
        feed.id = cursor.getInt(0);
        feed.name = cursor.getString(1);
        feed.address = cursor.getString(2);
        return feed;
    }
}
