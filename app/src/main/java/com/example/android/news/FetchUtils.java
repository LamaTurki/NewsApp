package com.example.android.news;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by lama on 8/21/2017 AD.
 */

public final class FetchUtils {
    private final static String stringUrl = "https://content.guardianapis.com/search?tag=technology/technology&api-key=test&order-by=newest&page-size=30&show-tags=contributor";

    private FetchUtils() {
        throw new AssertionError("Cannot be Instantiated");
    }

    public static URL createUrl() {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String performHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        String json = "";
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                json = readInputStream(inputStream);
            } else
                Log.e(FetchUtils.class.getName(), "Error, Response code: " + urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return json;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringResponse = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String lineRead = bufferedReader.readLine();
            while (lineRead != null) {
                stringResponse.append(lineRead);
                lineRead = bufferedReader.readLine();
            }
        }
        return stringResponse.toString();
    }

    public static ArrayList<Article> parseJSON(String jsonString) {
        ArrayList<Article> newsList = new ArrayList<>();
        try {
            JSONObject jsonRoot = new JSONObject(jsonString);
            JSONObject response = jsonRoot.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject currentArticle = results.getJSONObject(i);
                String title = currentArticle.getString("webTitle");
                String date = "";
                if (currentArticle.has("webPublicationDate")) {
                    date = currentArticle.getString("webPublicationDate");
                }
                String section = currentArticle.getString("sectionName");
                String url = currentArticle.getString("webUrl");
                JSONArray tagsArray = currentArticle.getJSONArray("tags");
                String author = "";
                if (tagsArray.length() > 0) {
                    author = tagsArray.getJSONObject(0).getString("webTitle");
                    for (int index = 1; index < tagsArray.length(); index++) {
                        author += " and " + tagsArray.getJSONObject(index).getString("webTitle");
                    }
                }
                newsList.add(new Article(title, author, date, url, section));
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return newsList;
    }
}



