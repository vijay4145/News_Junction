package com.example.newsjunction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NewsTopicAdapter.CategoryClickInterface{
    public ArrayList<Articles> newsModals = new ArrayList<>();
    NewsColumnAdapter newsColumnAdapter;
    ArrayList<String> topicNames;

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

        topicNames = new ArrayList<>(Arrays.asList("INDIA", "WORLD", "TECHNOLOGY", "STARTUPS", "POLITICS"));
        ArrayList<Integer> topicIcons = new ArrayList<>(Arrays.asList(R.drawable.india, R.drawable.world, R.drawable.technology, R.drawable.startups, R.drawable.politics));

        newsColumnAdapter = new NewsColumnAdapter(this, newsModals);
        NewsTopicAdapter newsTopicAdapter = new NewsTopicAdapter(this, topicIcons, topicNames, newsColumnAdapter);
        recyclerViewTopic.setAdapter(newsTopicAdapter);
        recyclerViewNews.setAdapter(newsColumnAdapter);
        getNews("all");

        recyclerViewTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = v.findViewById(R.id.heading);
                Log.d("errors", "clicked the " + textView.getText().toString());
            }
        });


    }

    public void getNews(String category) {
        newsModals.clear();
        final String baseUrl = "https://newsapi.org/";
        String url = "v2/everything?q=" + category +"&from=2021-09-09&to=2021-09-09&sortBy=popularity&apiKey=c086c0774b3440b59881690a6988c924";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<NewsModal> call = api.getNewsByCategory(url);
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                for (int i=0; i<newsModal.getArticles().size(); i++) {
                    newsModals.add(newsModal.getArticles().get(i));
                }
                newsColumnAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Log.d("errors", "something wrong with newsColumnAdapter");
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        getNews(topicNames.get(position));
        Log.d("errors", "clicked" + topicNames.get(position));
    }
}