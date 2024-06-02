package com.example.finalmobiletest.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalmobiletest.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button btn_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        btn_welcome = findViewById(R.id.btn_welcome);
        btn_welcome.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = WelcomeActivity.this.getSharedPreferences("cekLoginFromWelcome", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("welcome", true);
            editor.apply();

            Intent intent = new Intent(this, SignActivity.class);
            startActivity(intent);
        });
    }
}