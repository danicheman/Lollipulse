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
        intent.putExtra(RssService.RSS_LINK, "http://www.phonearena.com/feed/news");
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
            Log.d(Constants.TAG, "hide progress bar notification");
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
        Log.i(Constants.TAG,"clicked a story");
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);

        String link = item.getLink();
        Log.d(Constants.TAG,"Start Activity");
        Intent i = new Intent(getActivity(), ItemWebViewFragmentActivity.class);
        i.putExtra("link", link);
        startActivity(i);

    }
}
