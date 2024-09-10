package com.example.petcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private EditText contactEditText;
    private Button editButton;

    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        usernameEditText = findViewById(R.id.userDetailsUsername);
        passwordEditText = findViewById(R.id.userDetailsPassword);
        emailEditText = findViewById(R.id.userDetailsEmail);
        contactEditText = findViewById(R.id.userDetailsContact);
        editButton = findViewById(R.id.editDetailsButton);


        retrieveUserDetails();


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
                // For example, redirect to the EditDetailsActivity
                Intent intent = new Intent(UserDetailsActivity.this, EditDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void retrieveUserDetails() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedUsername = prefs.getString("username", "");
        String savedPassword = prefs.getString("password", "");
        String savedEmail = prefs.getString("email", "");
        String savedContact = prefs.getString("contact", "");

        // Display user details in EditText fields
        usernameEditText.setText(savedUsername);
        passwordEditText.setText(savedPassword);
        emailEditText.setText(savedEmail);
        contactEditText.setText(savedContact);
    }
}
