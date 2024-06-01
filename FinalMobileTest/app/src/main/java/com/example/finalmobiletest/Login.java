package com.example.finalmobiletest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    private TextView textViewSignUp;
    private Button buttonContinue;
    private TextInputEditText editTextUsername;
    private TextInputEditText editTextPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EdgeToEdge.enable(this);

        // Initialize views
        textViewSignUp = findViewById(R.id.Login_tv_SignUp);
        buttonContinue = findViewById(R.id.btn_login_continue);
        editTextUsername = findViewById(R.id.login_username);
        editTextPassword = findViewById(R.id.login_password);
        databaseHelper = new DatabaseHelper(this);

        // Set click listener for the Sign Up text view
        textViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignActivity.class);
            startActivity(intent);
        });

        // Set click listener for the Continue button
        buttonContinue.setOnClickListener(v -> {
            // Get the input from username and password fields
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Perform login logic here (e.g., authentication with server)
            // For demonstration, we'll just show a toast
            if (!username.isEmpty() && !password.isEmpty()) {
                databaseHelper.checkUsernamePassword(username, password);
                // Handle successful login
                SharedPreferences sharedPreferences = Login.this.getSharedPreferences("cekLoginFromLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("login", true);
                editor.apply();
                SharedPreferences preferencesUsername = Login.this.getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editorUser = preferencesUsername.edit();
                editorUser.putString("username", username);
                editorUser.apply();


                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                // You can start a new activity here
            } else {
                // Handle login failure
                Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
