package com.example.nick.lollipulse;

/**
 * A representation of an rss item from the list.
 * 
 * @author Veaceslav Grec
 * 
 */
public class RssItem {
    private final String title;
    private final String link;
    private final String url;

    public RssItem(String title, String link, String url) {
        this.title = title;
        this.link = link;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }
    public String getUrl() {
        return url;
    }
}
