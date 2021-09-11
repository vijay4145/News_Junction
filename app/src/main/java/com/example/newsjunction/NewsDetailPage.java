package com.example.newsjunction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsDetailPage extends AppCompatActivity {
    String imgUrl, title, url, author, description, publishedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_page);
        Intent intent = getIntent();
        imgUrl = intent.getStringExtra("ImageUrl");
        author = intent.getStringExtra("Author");
        title = intent.getStringExtra("Title");
        description = intent.getStringExtra("Description");
        url = intent.getStringExtra("NewsUrl");
        publishedDate = intent.getStringExtra("PublishedDate");

        ImageView newsImage = findViewById(R.id.newsDetailPageImage);
        TextView newsHeading = findViewById(R.id.newsDetailPageHeading);
        TextView newsAuthor = findViewById(R.id.newsDetailPageAuthor);
        TextView newsDescription = findViewById(R.id.newsDetailPageDescription);
        Button btn = findViewById(R.id.btn);
        TextView publishedData = findViewById(R.id.publishedDate);

        newsHeading.setText(title);
        newsAuthor.setText("Author: " +author);
        newsDescription.setText(description);
        publishedData.setText("Published Date: " + (publishedDate.split("T"))[0]);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        Glide.with(this).load(imgUrl).into(newsImage);
    }
}