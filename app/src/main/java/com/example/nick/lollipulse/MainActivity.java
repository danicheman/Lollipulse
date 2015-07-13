package com.example.nick.lollipulse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_feeds_list);

        //the list view for the feeds
        ListView rootView = (ListView)findViewById(R.id.feeds_list);
        //rootView.removeView(findViewById(R.id.feed));

		if (savedInstanceState == null) {
			addRssFeed(rootView);
		}
	}

	private void addRssFeed(ListView rootView) {

        ArrayList<Feed> feeds = new ArrayList<>();
        feeds.add(new Feed(
                1,
                "Stack Overflow",
                "http://stackoverflow.com/feeds/question/5273436"
        ));
        feeds.add(new Feed(
                2,
                "GSM Arena",
                "http://www.gsmarena.com/rss-news-reviews.php3"
        ));

        Log.w(Constants.TAG, "In function addRssFragment");
        FeedReadAdapter feedAdapter = new FeedReadAdapter(this, R.layout.read_feeds_list, feeds);
        rootView.setAdapter(feedAdapter);
        //startService(feeds.get(position).address);

        //RssFragment fragment = new RssFragment();
        //fragment.setFeed(feeds.get(position));

    }

    /*private void startService(String address) {
        Intent intent = new Intent(context, RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        intent.putExtra(RssService.RSS_LINK, address);
        //intent.putExtra()
        context.startService(intent);
    }*/



	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("fragment_added", true);
	}
}