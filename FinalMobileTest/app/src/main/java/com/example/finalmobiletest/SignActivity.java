package com.example.finalmobiletest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignActivity extends AppCompatActivity {

    private TextInputEditText etName, etUsername, etPassword, etDesc;
    private Button btnContinue;
    private TextView tvLogin;
    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        EdgeToEdge.enable(this);

        etName = findViewById(R.id.signup_set_name);
        etUsername = findViewById(R.id.signup_username);
        etPassword = findViewById(R.id.signup_password);
        etDesc = findViewById(R.id.signup_set_Desc);
        btnContinue = findViewById(R.id.btn_signup_continue);
        tvLogin = findViewById(R.id.Signup_tv_Login);
        myDB = new DatabaseHelper(this);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }

        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogin();
            }
        });
    }

    private void handleRegister() {
        String name = etName.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String description = etDesc.getText().toString();
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "isi data yang belum terisi", Toast.LENGTH_SHORT).show();
        } else if (myDB.checkUsername(username)) {
            Toast.makeText(this, "Username sudah terdaftar dan tidak bisa digunakan lagi ", Toast.LENGTH_SHORT).show();
            return;
        }else {
            myDB.insertUser(name, username, password, description);
            SharedPreferences preferencesName = getSharedPreferences("Name", MODE_PRIVATE);
            SharedPreferences.Editor editorName = preferencesName.edit();
            editorName.putString("name", name);
            editorName.putString("description", description);
            editorName.apply();
            SharedPreferences preferencesDesc = getSharedPreferences("Desc", MODE_PRIVATE);
            SharedPreferences.Editor editorDesc = preferencesDesc.edit();
            editorDesc.putString("desc", description);
            editorDesc.apply();

            Intent intent = new Intent(SignActivity.this, Login.class);
            startActivity(intent);
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SignActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
}
