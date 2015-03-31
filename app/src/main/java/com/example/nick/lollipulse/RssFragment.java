package com.example.nick.lollipulse;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.ProgressBar;
import android.widget.Toast;
import it.sephiroth.android.library.widget.HListView;

public class RssFragment extends Fragment implements OnItemClickListener {

	private ProgressBar progressBar;
	private HListView listView;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_layout, container, false);
			//progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            //progressBar.setVisibility(View.VISIBLE);
            
			listView = (HListView) view.findViewById(R.id.hListView1); //new
			listView.setOnItemClickListener(this);  //fix this
			startService();
		} else {
			// If we are returning from a configuration change:
			// "view" is still attached to the previous view hierarchy
			// so we need to remove it and re-attach it to the current one
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
		return view;
	}

	private void startService() {
		Intent intent = new Intent(getActivity(), RssService.class);
		intent.putExtra(RssService.RECEIVER, resultReceiver);
		getActivity().startService(intent);
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
            Log.e(Constants.TAG, "hide progress bar notification");
			List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
			if (items != null) {
				RssAdapter adapter = new RssAdapter(getActivity(), items);
				listView.setAdapter(adapter);
			} else {
				Toast.makeText(getActivity(), "An error occurred while downloading the rss feed.",
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
        Log.e(Constants.TAG,"clicked a story");
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);

        String link = item.getLink();

        //Uri uri = Uri.parse(item.getLink());
        //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
        //Intent webViewIntent = new Intent(getActivity(), ItemWebViewFragmentActivity.class);

        //Intent webViewIntent = (WebViewFragment) getFragment

        //Bundle webViewBundle = new Bundle();
        //webViewBundle.putString("link", item.getLink());

        //getActivity().startActivity(webViewIntent,webViewBundle);

        if (getActivity().findViewById(R.id.hListView1) != null) {
            ViewItemFragment vif = (ViewItemFragment) getFragmentManager().findFragmentById(R.id.webview);

                Log.i(Constants.TAG,"Add fragment, going to: "+link);
                Bundle linkBundle = new Bundle();
                linkBundle.putString("link", link);

                vif = new ViewItemFragment();
                vif.setArguments(linkBundle);

                // We are in dual fragment (Tablet and so on)
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();



                //wvf.updateUrl(link);
                ft.replace(R.id.fragment_container, vif);
                ft.commit();
        }
        else {
            Log.i(Constants.TAG,"Start Activity");
            //Intent i = new Intent(this, WebViewActivity.class);
            //i.putExtra("link", link);
            //startActivity(i);
        }

    }
}
