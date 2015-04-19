package com.example.nick.lollipulse;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by nick on 12/30/14.
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    private boolean retry = true;

    public ImageDownloader(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mIcon = null;
        Log.i(Constants.TAG, "loading image: "+ url);
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {

            if(retry) {
               retry = false;
               Log.e(Constants.TAG,"Retry image" + url);
               return doInBackground(url);
            }

            Log.e(Constants.TAG, "Error loading url" + url+ " try to return default.");
            Log.e(Constants.TAG, "exception here", e);
            String uri = "@drawable/no_image.jpg";

            mIcon = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.no_image);


        }
        return mIcon;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}

