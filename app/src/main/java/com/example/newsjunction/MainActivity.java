package com.example.newsjunction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static String categorySelected = "all";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewTopic = findViewById(R.id.topic);
        RecyclerView recyclerViewNews = findViewById(R.id.news);
        recyclerViewTopic.setHasFixedSize(true);
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewTopic.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> topicNames = new ArrayList<>(Arrays.asList("INDIA", "WORLD", "TECHNOLOGY", "STARTUPS", "POLITICS"));
        ArrayList<Integer> topicIcons = new ArrayList<>(Arrays.asList(R.drawable.india, R.drawable.world, R.drawable.technology, R.drawable.startups, R.drawable.politics));

        NewsColumnAdapter newsColumnAdapter = new NewsColumnAdapter(this);
        NewsTopicAdapter newsTopicAdapter = new NewsTopicAdapter(this, topicIcons, topicNames, newsColumnAdapter);
        recyclerViewTopic.setAdapter(newsTopicAdapter);
        recyclerViewNews.setAdapter(newsColumnAdapter);
    }
}