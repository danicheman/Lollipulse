package com.example.nick.lollipulse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nicks on 12/1/2014.
 *
 * Adapt a single feed's information to a single feed row view
 */
public class FeedListAdapter extends ArrayAdapter<Feed> {

    private final Context context;
    private final List<Feed> feeds;

    public FeedListAdapter(Context context, int resource, List<Feed> items) {
        super(context, resource, items);

        this.feeds = items;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //load the row, specify parent, attach to parent?
        View rowView = inflater.inflate(R.layout.feed_list_item, parent, false);

        //fetch item and set text for an item from the row
        TextView viewName = (TextView) rowView.findViewById(R.id.feed_name);
        viewName.setText(this.feeds.get(position).name);

        //fetch item and set text for an item from the row
        TextView viewDescription = (TextView) rowView.findViewById(R.id.feed_description);
        viewDescription.setText(this.feeds.get(position).address);

        return rowView;

        //return super.getView(position, convertView, parent);
    }
}
