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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsColumnAdapter extends RecyclerView.Adapter<NewsColumnAdapter.ViewHolder> {
    private Context context;

    public NewsColumnAdapter(Context context) {
        this.context = context;
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
        final String baseUrl = "https://newsapi.org/";
        String url = "v2/everything?q=" + MainActivity.categorySelected +"&from=2021-09-09&to=2021-09-09&sortBy=popularity&apiKey=c086c0774b3440b59881690a6988c924";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<NewsModal> call = api.getNewsByCategory(url);
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                holder.newsHeading.setText(newsModal.getArticles().get(position).getTitle());
                Glide.with(context).load(newsModal.getArticles().get(position).getUrlToImage()).into(holder.newsImage);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, NewsDetailPage.class);
                        intent.putExtra("ImageUrl", newsModal.getArticles().get(position).getUrlToImage());
                        intent.putExtra("Title", newsModal.getArticles().get(position).getTitle());
                        intent.putExtra("Author", newsModal.getArticles().get(position).getAuthor());
                        intent.putExtra("Description", newsModal.getArticles().get(position).getDescription());
                        intent.putExtra("NewsUrl", newsModal.getArticles().get(position).getUrl());
                        intent.putExtra("PublishedDate", newsModal.getArticles().get(position).getPublishedAt());
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Log.d("errors", "something wrong with newsColumnAdapter");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
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
