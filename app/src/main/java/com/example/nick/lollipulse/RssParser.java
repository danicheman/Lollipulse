package com.example.nick.lollipulse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class RssParser {

	// We don't use namespaces
	private final String ns = null;

	public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
		try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        }catch (XmlPullParserException e) {
            Log.e(null, "Pull Parser Exception in Parse");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.e(null, "IO Exception in Parse");
            e.printStackTrace();
            return null;
		} finally {
			inputStream.close();
		}
	}

	private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		try {
            parser.require(XmlPullParser.START_TAG, null, "rss");
            String title = null;
            String link = null;
            String thumb = null;

            List<RssItem> items = new ArrayList<RssItem>();
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("title")) {
                    title = readTitle(parser);
                } else if (name.equals("link")) {
                    link = readLink(parser);
                } else if (name.equals("media:thumbnail")) {
                    thumb = parser.getAttributeValue(null, "url");
                } if(name.equals("description")) {
                    parser.next();
                }
                if (title != null && link != null && thumb != null) {
                    RssItem item = new RssItem(title, link, thumb);
                    items.add(item);
                    title = null;
                    link = null;
                    thumb = null;
                }
            }
            return items;
        } catch (XmlPullParserException e) {
            Log.e(Constants.TAG, "XML Pull exception in readfeed");
        } catch (IOException e) {
            Log.e(Constants.TAG, "IO exception in readfeed");
        }
        return null;
	}

	private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "link");
		String link = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "link");
		return link;
	}

	private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "title");
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "title");
		return title;
	}

	// For the tags title and link, extract their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}
}
