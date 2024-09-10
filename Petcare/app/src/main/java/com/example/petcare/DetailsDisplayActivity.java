package com.example.petcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsDisplayActivity extends AppCompatActivity {

    private TextView textViewUsernameValue;
    private TextView cityNameTextView;
    private TextView detailsTextView;

    private Button buttonPetCare;
    private Button buttonContact;

    private static final int IMAGE_PICKER_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_display);

        textViewUsernameValue = findViewById(R.id.textViewUsernameValue);
        cityNameTextView = findViewById(R.id.cityNameTextView);
        detailsTextView = findViewById(R.id.detailsTextView);

        buttonPetCare = findViewById(R.id.buttonPetCare);
        buttonContact = findViewById(R.id.buttonContact);


        String savedDetails = getSavedDetailsFromSharedPreferences();
        Intent intent = getIntent();

        if (intent != null) {
            String username = intent.getStringExtra("USERNAME");
            String cityName = intent.getStringExtra("CITY_NAME");

            // Set values to TextViews
            textViewUsernameValue.setText(username);

            // Display city name in the designated TextView
            if (cityNameTextView != null) {
                cityNameTextView.setText("City: " + cityName);
            }

            // Set the value of detailsTextView
            detailsTextView.setText(savedDetails);
        } else {
            Log.w("DetailsDisplayActivity", "Intent is null");
        }

        // Set onClickListener for buttonPetCare
        buttonPetCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsDisplayActivity.this, petcare.class);
                startActivity(intent);
            }
        });

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsDisplayActivity.this, UserDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getSavedDetailsFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        return prefs.getString("savedDetails", "");
    }

    private void openImagePicker() {
        Intent imagePickerIntent = new Intent(Intent.ACTION_PICK);
        imagePickerIntent.setType("image/*");
        startActivityForResult(imagePickerIntent, IMAGE_PICKER_REQUEST_CODE);
    }
}
