package com.example.android.news;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.news.FetchUtils.performHttpRequest;

/**
 * Created by lama on 8/21/2017 AD.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<Article>> {

    private ArrayList<Article> newsList = new ArrayList<>();

    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Article> loadInBackground() {
        URL url = FetchUtils.createUrl();
        try {
            String jsonResponse = performHttpRequest(url);
            newsList = FetchUtils.parseJSON(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
