package dev.lotr.demo.itunessearch.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.lotr.demo.itunessearch.R;
import dev.lotr.demo.itunessearch.model.Track;
import dev.lotr.demo.itunessearch.net.TrackLoader;

/**
 * Show main screen app.
 */
public class MainActivity extends AppCompatActivity implements
        MaterialSearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<String> {

    @BindView(R.id.search_view) MaterialSearchView searchView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress_bar) AVLoadingIndicatorView progressBar;
    @BindView(R.id.list_track) RecyclerView recyclerView;
    @BindView(R.id.message) TextView message;
    private ListTrackAdapter listAdapter;
    private LinearLayoutManager mLayoutManager;

    private static final int LOADER_ID = 1;
    private static final String TAG = "_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        searchView.setOnQueryTextListener(this);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        listAdapter = new ListTrackAdapter(this);
        recyclerView.setAdapter(listAdapter);
        showMessage(getString(R.string.message_start));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    public void onResume(){
        super.onResume();
        if (getLoaderManager().getLoader(LOADER_ID) != null) {
            getLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }

    private void initLoading(String query) {
        hideMessage();
        showProgress();
        listAdapter.clearData();
        Bundle args = new Bundle();
        args.putString("query", query);
        getLoaderManager().restartLoader(LOADER_ID, args, this);
    }

    /**
     * Parses responce json.
     * @param json request result
     */
    private ArrayList<Track> parseData(String json) {
        ArrayList<Track> list = new ArrayList<>();
        try {
            JSONArray resultsItem = new JSONObject(json).getJSONArray("results");
            // If query non found, show error and brake.
            if (resultsItem.length() < 1){
                showMessage(getString(R.string.error_non_found));
                return list;
            }

            // Else build list data.
            for (int i = 0; i < resultsItem.length(); i++) {
                list.add(new Track(
                        resultsItem.getJSONObject(i).getString("artistName"),
                        resultsItem.getJSONObject(i).getString("artworkUrl100"),
                        resultsItem.getJSONObject(i).getString("primaryGenreName"),
                        resultsItem.getJSONObject(i).getString("trackName"),
                        resultsItem.getJSONObject(i).getString("trackPrice")
                ));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return list;
        }
    }

    private void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }

    private void showMessage(String message){
        this.message.setVisibility(View.VISIBLE);
        this.message.setText(message);
    }

    private void hideMessage(){
        message.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        initLoading(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (LOADER_ID == id) {
            return new TrackLoader(this, args);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String responce) {
        if (!TextUtils.isEmpty(responce)) {
            listAdapter.clearData();
            listAdapter.setData(parseData(responce));
        } else {
            showMessage(getString(R.string.error_fail_loading));
        }
        hideProgress();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
