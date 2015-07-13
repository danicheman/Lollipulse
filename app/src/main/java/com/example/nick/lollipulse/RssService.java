package com.example.nick.lollipulse;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class RssService extends IntentService {

	public static final String RSS_LINK = "link";
	public static final String ITEMS = "items";
	public static final String RECEIVER = "receiver";
	public static final String POSITION = "position";

	public RssService() {
		super("RssService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
        String rssLink = intent.getStringExtra(RSS_LINK);
		int position = (int)intent.getIntExtra(POSITION, 0);
		Log.d(Constants.TAG, "Attempting to load RSS feed "+rssLink);
		List<RssItem> rssItems = null;
		try {
			RssParser parser = new RssParser();
			rssItems = parser.parse(getInputStream(rssLink));
		} catch (XmlPullParserException e) {
			Log.w(Constants.TAG,e.getMessage(), e);
		} catch (IOException e) {
			Log.w(Constants.TAG,e.getMessage(), e);
		}
		Bundle bundle = new Bundle();
		bundle.putSerializable(ITEMS, (Serializable) rssItems);
		bundle.putSerializable(POSITION, position);
		ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        Log.d(Constants.TAG, "Sending bundle");
		receiver.send(0, bundle);
	}

	public InputStream getInputStream(String link) {
		try {
			URL url = new URL(link);
			return url.openConnection().getInputStream();
		} catch (IOException e) {
			Log.e(Constants.TAG, "Exception while retrieving the input stream", e);
			return null;
		}
	}
}
