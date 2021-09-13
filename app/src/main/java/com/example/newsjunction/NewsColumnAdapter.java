package com.example.newsjunction;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsColumnAdapter extends RecyclerView.Adapter<NewsColumnAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Articles> articles;

    public NewsColumnAdapter(Context context, ArrayList<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @NotNull
    @Override
    public NewsColumnAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newscolumn, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewsColumnAdapter.ViewHolder holder, int position) {
        holder.newsHeading.setText(articles.get(position).getTitle());
        Glide.with(context).load(articles.get(position).getUrlToImage()).into(holder.newsImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailPage.class);
                intent.putExtra("ImageUrl", articles.get(position).getUrlToImage());
                intent.putExtra("Title", articles.get(position).getTitle());
                intent.putExtra("Author", articles.get(position).getAuthor());
                intent.putExtra("Description", articles.get(position).getDescription());
                intent.putExtra("NewsUrl", articles.get(position).getUrl());
                intent.putExtra("PublishedDate", articles.get(position).getPublishedAt());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView newsImage;
        public TextView newsHeading;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            newsHeading = itemView.findViewById(R.id.newsHeading);
            newsImage = itemView.findViewById(R.id.newsImage);
        }

    }
}
