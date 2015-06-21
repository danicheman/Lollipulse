package com.example.nick.lollipulse;

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
			addRssFragment(rootView);
		}
	}

	private void addRssFragment(ListView rootView) {

        ArrayList<Feed> feeds = new ArrayList<>();
        feeds.add(new Feed(
                1,
                "Phone Arena",
                "http://www.phonearena.com/feed/news"
        ));
        feeds.add(new Feed(
                2,
                "GSM Arena",
                "http://www.gsmarena.com/rss-news-reviews.php3"
        ));

        Log.w(Constants.TAG, "In function addRssFragment");
        FeedReadAdapter feedAdapter = new FeedReadAdapter(this, R.layout.read_feeds_list, feeds);
        rootView.setAdapter(feedAdapter);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("fragment_added", true);
	}
}