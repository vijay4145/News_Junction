package com.example.newsjunction;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewsTopicAdapter extends RecyclerView.Adapter<NewsTopicAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Integer> icons;
    private ArrayList<String> topics;
    private NewsColumnAdapter newsColumnAdapter;

    public NewsTopicAdapter(Context context, ArrayList<Integer> icons, ArrayList<String> topics, NewsColumnAdapter newsColumnAdapter) {
        this.context = context;
        this.icons = icons;
        this.topics = topics;
        this.newsColumnAdapter = newsColumnAdapter;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public NewsTopicAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull NewsTopicAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(icons.get(position));
        holder.heading.setText(topics.get(position));
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView heading;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.topicImage);
            heading = itemView.findViewById(R.id.heading);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MainActivity.categorySelected = heading.getText().toString();
            newsColumnAdapter.notifyDataSetChanged();
        }
    }
}
