package com.example.nick.lollipulse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by nick on 3/6/15.
 */
public class ItemWebViewFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewItemFragment vif = new ViewItemFragment();

        Intent i = this.getIntent();
        String link = i.getExtras().getString("link");

        Log.d("SwA", "URL ["+link+"]");
        vif.init(link);
        //outer container to add to, inner containeradd(R.id.fragment_container, vif).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, vif).commit();

    }

}
