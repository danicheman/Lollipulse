package com.example.nick.lollipulse;

import java.util.ArrayList;

/**
 * Created by Nicks on 11/30/2014.
 */
public class Feed {
    public Feed() {
    }
    public Feed(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
    public int id;
    public String name;
    public String address;
    public ArrayList<RssItem> rssItems;

    public String toString() {
        return this.name;
    }

}
