package com.example.nick.lollipulse;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import it.sephiroth.android.library.widget.AdapterView;

/**
 * Created by nick on 2/28/15.
 */
public class ViewItemFragment extends Fragment {
    private String currentURL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currentURL = getArguments().getString("link");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WebView wv = (WebView) inflater.inflate(R.layout.web_view,null);
        if(this.currentURL.isEmpty()) {
            this.currentURL = "http://www.google.com/";
            Log.i(Constants.TAG, "loading google, no URL found");
        } else {
            Log.i(Constants.TAG, "loading url" + this.currentURL);
        }

        wv.loadUrl(this.currentURL);
        return wv;
    }

    public void init(String url) {
        this.currentURL = url;


    }


}
