package com.example.android.news;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lama on 8/18/2017 AD.
 */

public class Article {
    private String mTitle;
    private String mAuthor;
    private String mDate;
    private String mUrl;
    private String mSection;

    public Article(String mTitle, String mAuthor, String mDate, String mUrl, String mSection) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mDate = reformatDate(mDate);
        this.mUrl = mUrl;
        this.mSection = mSection;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmSection() {
        return mSection;
    }

    private String reformatDate(String inputSting) {
        String outputSting = inputSting;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy' 'HH:mm 'UTC'");
        try {
            Date date = inputFormat.parse(inputSting);
            outputSting = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputSting;
    }
}
