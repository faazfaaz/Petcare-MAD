package com.example.petcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.List;

public class TabContentActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final int IMAGE_PICKER_REQUEST_CODE = 123; // You can use any unique integer
    private static final String PREFS_NAME = "MyPrefsFile";


    private RadioGroup radioGroupAnimals;
    private EditText editTextOtherAnimal;
    private EditText editName;
    private EditText editTextAge;
    private EditText editTextWeight;
    private Button submitButton;
    private Button getLocationButton;
    private TextView townCityTextView;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_content);


        radioGroupAnimals = findViewById(R.id.radioGroupAnimals);
        editTextOtherAnimal = findViewById(R.id.editTextOtherAnimal);
        editName = findViewById(R.id.editName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextWeight = findViewById(R.id.editTextWeight);
        submitButton = findViewById(R.id.submitButton);
        getLocationButton = findViewById(R.id.getLocationButton);
        townCityTextView = findViewById(R.id.townCityTextView);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        radioGroupAnimals.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                handleAnimalEditTextVisibility(checkedId);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedAnimal = getSelectedAnimal();
                String name = editName.getText().toString();
                String age = editTextAge.getText().toString();
                String weight = editTextWeight.getText().toString();

                String details = "Animal: " + selectedAnimal + "\nName: " + name + "\nAge: " + age + "\nWeight: " + weight;

                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                String savedUsername = prefs.getString("username", null);

                if (!TextUtils.isEmpty(savedUsername)) {
                    String signUpUsername = savedUsername;
                    String city = townCityTextView.getText().toString();

                    // Save details in SharedPreferences
                    saveDetailsInSharedPreferences(details);

                    // Load default pet image using Picasso


                    // Start DetailsDisplayActivity
                    Intent intent = new Intent(TabContentActivity.this, DetailsDisplayActivity.class);
                    intent.putExtra("USERNAME", signUpUsername);
                    intent.putExtra("CITY_NAME", city);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TabContentActivity.this, "Username not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermissions();
            }
        });
    }

    private void handleAnimalEditTextVisibility(int checkedId) {
        if (checkedId == R.id.radioOther) {
            editTextOtherAnimal.setVisibility(View.VISIBLE);
        } else {
            editTextOtherAnimal.setVisibility(View.GONE);
        }
    }

    private void checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation(); // Call getLocation only if permission is granted
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Handle the case where permissions are not granted.
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            getAddressFromLocation(location);
                        } else {
                            Toast.makeText(TabContentActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void getAddressFromLocation(Location location) {
        android.location.Geocoder geocoder = new android.location.Geocoder(this);

        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                String city = addresses.get(0).getLocality();
                updateCityTextView(city);
            } else {
                Toast.makeText(TabContentActivity.this, "Address not available", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(TabContentActivity.this, "Error retrieving address", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCityTextView(String city) {
        if (townCityTextView != null) {
            townCityTextView.setText(city);
        }
    }

    private String getSelectedAnimal() {
        int selectedRadioButtonId = radioGroupAnimals.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

            if (selectedRadioButton != null) {
                return selectedRadioButton.getText().toString();
            }
        }

        return "";
    }

    private void saveDetailsInSharedPreferences(String details) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("savedDetails", details);
        editor.apply();

        Toast.makeText(TabContentActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
    }

    private void openImagePicker() {
        Log.d("TabContentActivity", "Opening image picker");
        Intent imagePickerIntent = new Intent(Intent.ACTION_PICK);
        imagePickerIntent.setType("image/*");
        startActivityForResult(imagePickerIntent, IMAGE_PICKER_REQUEST_CODE);


    }

}