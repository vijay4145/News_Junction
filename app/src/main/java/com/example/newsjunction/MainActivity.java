package com.example.newsjunction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NewsTopicAdapter.CategoryClickInterface{
    public static String API_KEY = "YOUR_API_KEY";
    public ArrayList<Articles> newsModals = new ArrayList<>();
    NewsColumnAdapter newsColumnAdapter;
    ArrayList<String> topicNames;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("API_KEY",MODE_PRIVATE);
        API_KEY = sharedPreferences.getString("apiKey", "YOUR_API_KEY");
        if (API_KEY.equals("YOUR_API_KEY")) {
            Toast.makeText(this, "ADD YOUR API KEY\n", Toast.LENGTH_LONG);
            Intent intent = new Intent(this, addApiKey.class);
            startActivity(intent);
            return ;
        }
        RecyclerView recyclerViewTopic = findViewById(R.id.topic);
        RecyclerView recyclerViewNews = findViewById(R.id.news);
        recyclerViewTopic.setHasFixedSize(true);
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewTopic.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));

        topicNames = new ArrayList<>(Arrays.asList("INDIA", "WORLD", "TECHNOLOGY", "STARTUPS", "POLITICS"));
        ArrayList<Integer> topicIcons = new ArrayList<>(Arrays.asList(R.drawable.india, R.drawable.world, R.drawable.technology, R.drawable.startups, R.drawable.politics));

        newsColumnAdapter = new NewsColumnAdapter(this, newsModals);
        NewsTopicAdapter newsTopicAdapter = new NewsTopicAdapter(this, topicIcons, topicNames, this);
        recyclerViewTopic.setAdapter(newsTopicAdapter);
        recyclerViewNews.setAdapter(newsColumnAdapter);
        Date dated = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        date = formatter.format(dated);
        getNews("all");

    }

    public void getNews(String category) {
        newsModals.clear();
        final String baseUrl = "https://newsapi.org/";
        String url = "v2/everything?q=" + category +"&from=" + date + "&to=" + date + "&sortBy=popularity&apiKey=" + API_KEY;
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
    }
}