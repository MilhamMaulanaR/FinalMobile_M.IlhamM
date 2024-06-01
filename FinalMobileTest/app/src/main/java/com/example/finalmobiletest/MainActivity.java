package com.example.finalmobiletest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ApiService apiService;
    private BottomNavigationView bottomNavigationView;
    private MainFragment mainFragment;
    private ProfileFragment profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);

        mainFragment = new MainFragment();
        profile = new ProfileFragment();

        SharedPreferences preferencesLogin = getSharedPreferences("cekLoginFromLogin", MODE_PRIVATE);
        boolean checkLogin = preferencesLogin.getBoolean("login", false);

        SharedPreferences preferencesWelcome = getSharedPreferences("cekLoginFromWelcome", MODE_PRIVATE);
        boolean checkWelcome = preferencesWelcome.getBoolean("welcome", false);

        if (!checkLogin){
            Intent toLogin = new Intent(MainActivity.this, Login.class);
            startActivity(toLogin);
        }

        if (!checkWelcome){
            Intent toWelcome = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(toWelcome);
        }

        setFragment(mainFragment);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        apiService = RetrofitClient.getQuotesClient().create(ApiService.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    setFragment(new MainFragment());
                } else if (itemId == R.id.nav_profile) {
                    setFragment(new ProfileFragment());
                }
                return true;
            }
        });
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
    }
}
