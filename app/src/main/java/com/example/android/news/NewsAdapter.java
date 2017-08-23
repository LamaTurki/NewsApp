package com.example.android.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lama on 8/18/2017 AD.
 */

public class NewsAdapter extends ArrayAdapter<Article> {
    private Context context;

    public NewsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Article> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.title);
            holder.authorTextView = (TextView) convertView.findViewById(R.id.author);
            holder.sectionTextView = (TextView) convertView.findViewById(R.id.section);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.publish_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Article article = getItem(position);
        holder.titleTextView.setText(article.getmTitle());
        holder.authorTextView.setText(article.getmAuthor());
        holder.sectionTextView.setText(article.getmSection());
        holder.dateTextView.setText(article.getmDate());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(article.getmUrl()));
                context.startActivity(webIntent);
            }
        });
        return convertView;

    }

    static class ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        TextView sectionTextView;
        TextView dateTextView;
    }
}
