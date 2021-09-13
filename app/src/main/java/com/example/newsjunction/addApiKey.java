package com.example.newsjunction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class addApiKey extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_api_key);

        TextView steps = findViewById(R.id.addApi);
        String step = "STEPS TO ADD API KEY:\n" +
                      "1.Log in to https://newsapi.org \n" +
                      "2.Create Your Account\n" +
                      "3.Copy your api key\n" +
                      "4.Paste your api key in editTextView";
        steps.setText(step);

        Button btn = findViewById(R.id.addApiKey);
        EditText editText = findViewById(R.id.apiKey);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("API_KEY",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("apiKey", editText.getText().toString());
                editor.apply();
                startActivity(intent);
            }
        });
    }
}