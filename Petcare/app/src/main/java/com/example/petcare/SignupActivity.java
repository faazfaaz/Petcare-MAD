package com.example.petcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText signUpUsernameEditText;
    private EditText signUpPasswordEditText;
    private EditText signUpEmailEditText;
    private EditText signUpContactEditText;
    private Button signUpButton;

    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI components
        signUpUsernameEditText = findViewById(R.id.SignUpUsername);
        signUpPasswordEditText = findViewById(R.id.SignUpPassword);
        signUpEmailEditText = findViewById(R.id.SignUpEmail); // Initialize email field
        signUpContactEditText = findViewById(R.id.SignUpContact); // Initialize contact field
        signUpButton = findViewById(R.id.SignUpButton);

        // Set click listener for signup button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle signup button click
                String signUpUsername = signUpUsernameEditText.getText().toString();
                String signUpPassword = signUpPasswordEditText.getText().toString();
                String signUpEmail = signUpEmailEditText.getText().toString(); // Retrieve email
                String signUpContact = signUpContactEditText.getText().toString(); // Retrieve contact

                // Save user information to SharedPreferences
                saveUserInformation(signUpUsername, signUpPassword, signUpEmail, signUpContact);

                // For example, redirect to the main activity after signup
                Intent intent = new Intent(SignupActivity.this, TabContentActivity.class);

                // Pass the username, password, email, and contact to TabContentActivity
                intent.putExtra("USERNAME", signUpUsername);
                intent.putExtra("PASSWORD", signUpPassword);
                intent.putExtra("EMAIL", signUpEmail);
                intent.putExtra("CONTACT", signUpContact);

                startActivity(intent);
                finish(); // Finish the signup activity to prevent going back to it
            }
        });
    }

    private void saveUserInformation(String username, String password, String email, String contact) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("email", email); // Save email
        editor.putString("contact", contact); // Save contact
        editor.apply();
    }
}
