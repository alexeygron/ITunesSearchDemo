package dev.tunse.demo.itunessearch.net;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.io.IOException;

/**
 * Loading tracks data from network.
 */
public class TrackLoader extends AsyncTaskLoader<String> {

    private String query;
    private static final String TAG = "_TrackLoader";

    public TrackLoader(Context context, Bundle args){
        super(context);
        this.query = args.getString("query");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        try {
            return RestClient.getTracks(query);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
