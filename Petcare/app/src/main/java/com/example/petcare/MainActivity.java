package com.example.petcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log; // Import Log class for logging
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        usernameEditText = findViewById(R.id.Username);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.Login);
        signinButton = findViewById(R.id.Signin);

        // Set click listeners for buttons
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login button click
                String enteredUsername = usernameEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                // Retrieve the saved username and password from SharedPreferences
                SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String savedUsername = prefs.getString("username", null);
                String savedPassword = prefs.getString("password", null);

                // Log entered and saved values for debugging
                Log.d("MainActivity", "Entered username: " + enteredUsername);
                Log.d("MainActivity", "Entered password: " + enteredPassword);
                Log.d("MainActivity", "Saved username: " + savedUsername);
                Log.d("MainActivity", "Saved password: " + savedPassword);

                if (savedUsername != null && savedPassword != null && enteredUsername.equals(savedUsername) && enteredPassword.equals(savedPassword)) {
                    // Successful login, redirect to DetailsDisplayActivity
                    Intent intent = new Intent(MainActivity.this, DetailsDisplayActivity.class);
                    intent.putExtra("USERNAME", enteredUsername);
                    intent.putExtra("PASSWORD", enteredPassword);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    // Display an error message or handle unsuccessful login
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
