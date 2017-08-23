package com.example.android.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {
    private ListView newsListView;
    private TextView emptyTextView;
    private NewsAdapter myAdapter;
    private LoaderManager loaderManager;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsListView = (ListView) findViewById(R.id.list_view);
        emptyTextView = (TextView) findViewById(R.id.empty_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        progressBar.setVisibility(View.GONE);
        ArrayList<Article> newsList = new ArrayList<>();
        myAdapter = new NewsAdapter(this, 0, newsList);
        newsListView.setAdapter(myAdapter);
        loaderManager = getLoaderManager();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            progressBar.setVisibility(View.VISIBLE);
            loaderManager.initLoader(0, null, this);
        } else {
            emptyTextView.setText(getString(R.string.no_connection));
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaderManager.restartLoader(0, null, MainActivity.this);
            }
        });
    }


    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> data) {
        myAdapter.clear();
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty())
            myAdapter.addAll(data);
        else
            emptyTextView.setText(getString(R.string.no_data));
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {
        myAdapter.clear();
    }
}
