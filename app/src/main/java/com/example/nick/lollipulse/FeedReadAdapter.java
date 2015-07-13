package com.example.nick.lollipulse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;

/**
 * Apply multiple feeds and their articles to a listview
 *
 * Created by nick on 5/8/15.
 */
public class FeedReadAdapter extends ArrayAdapter<Feed> implements AdapterView.OnItemClickListener {

        private final Context context;
        private final List<Feed> feeds;
        public static final String VIEW = "parentView";
        private Hashtable<Integer, View> feedViews;

        public FeedReadAdapter(Context context, int resource, List<Feed> items){
            super(context, resource, items);

            this.feeds = items;
            this.context = context;
            this.feedViews = new Hashtable<Integer, View>();

            for(Feed f : items) {
                launchFeed(f);
            }

        }

    private void launchFeed(Feed f) {
        Intent intent = new Intent(context, RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        intent.putExtra(RssService.RSS_LINK, f.address);
        intent.putExtra(RssService.POSITION, f.id);
        context.startService(intent);
    }
    /**
     * For each feed to have items displayed, create a row using the "feed" layout,
     * and launch the RSS Fragment (Rename this) class
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent){

        //try to grab the row from the array of views
        try {
            View existingRow = this.feedViews.get(position);
            if (existingRow != null) return existingRow;
        } catch (IndexOutOfBoundsException e) {

        }



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //load the row, specify parent, attach to parent?
        View rowView = inflater.inflate(R.layout.feed, parent, false);

        //fetch item and set text for an item from the row
        TextView viewName = (TextView) rowView.findViewById(R.id.feedTitle);
        viewName.setText(this.feeds.get(position).name);
        Log.w(Constants.TAG, "Setting up feed view." + position + " and view id: "+this.feeds.get(position).id);
        HListView listView = (HListView) rowView.findViewById(R.id.hListView1); //new
        listView.setOnItemClickListener(this);  //fix this

        this.feedViews.put(this.feeds.get(position).id, rowView);
        return rowView;
    }

    /**
     * Update the feed that's been loaded
     */
    class UpdateUI implements Runnable {
        private List<RssItem> items;
        private int position;
        //constructor
        public UpdateUI(List<RssItem> items, int position) {
            this.position = position;
            this.items = items;
        }

        /**
         * grab the existing listview and update it.
         */
        @Override
        public void run() {
            Log.d(Constants.TAG, "updating feed at position"+ position);
            RssAdapter adapter = new RssAdapter(context, items);
            Log.d(Constants.TAG, "items:");
            Log.d(Constants.TAG, items.toString());
            View wholeFeed = (View)feedViews.get(feeds.get(position).id);
            HListView feed = (HListView)wholeFeed.findViewById(R.id.hListView1);
            feed.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
            notifyDataSetChanged();
            Log.d(Constants.TAG, "finished updating the ui presumably" + position);
        }
    }

    /**
     * Once the {@link RssService} finishes its task, the result is sent to this
     * ResultReceiver.
     */
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            //progressBar.setVisibility(View.GONE);
            Log.d(Constants.TAG, "hide progress bar notification");
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
            ListView listView = (ListView) resultData.getSerializable(FeedReadAdapter.VIEW);
            int position = (int) resultData.getInt(RssService.POSITION);
            if (items != null) {
                ((Activity)context).runOnUiThread(new UpdateUI(items, position));

            } else {
                Toast.makeText(context, "An error occurred while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
        }
    };
    /*
	@Override
	void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(Constants.TAG,"clicked a story");
		RssAdapter adapter = (RssAdapter) parent.getAdapter();
		RssItem item = (RssItem) adapter.getItem(position);
		Uri uri = Uri.parse(item.getLink());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}*/

    @Override
    public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent, View view, int position, long id) {
        Log.i(Constants.TAG,"clicked a story");
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);

        String link = item.getLink();
        Log.d(Constants.TAG,"Start Activity");
        Intent i = new Intent(this.context, ItemWebViewFragmentActivity.class);
        i.putExtra("link", link);
        context.startActivity(i);

    }
}
