package com.example.petcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditDetailsActivity extends AppCompatActivity {

    private EditText editEmailEditText;
    private EditText editContactEditText;
    private Button saveChangesButton;

    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        // Initialize UI components
        editEmailEditText = findViewById(R.id.editEmailEditText);
        editContactEditText = findViewById(R.id.editContactEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);


        retrieveUserDetails();


        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editedEmail = editEmailEditText.getText().toString();
                String editedContact = editContactEditText.getText().toString();


                if (!TextUtils.isEmpty(editedEmail) && !TextUtils.isEmpty(editedContact)) {

                    saveChangesToSharedPreferences(editedEmail, editedContact);


                    Toast.makeText(EditDetailsActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(EditDetailsActivity.this, UserDetailsActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(EditDetailsActivity.this, "Email and Contact cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void retrieveUserDetails() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedEmail = prefs.getString("email", "");
        String savedContact = prefs.getString("contact", "");

        // Display existing details in EditText fields
        editEmailEditText.setText(savedEmail);
        editContactEditText.setText(savedContact);
    }

    private void saveChangesToSharedPreferences(String editedEmail, String editedContact) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("email", editedEmail);
        editor.putString("contact", editedContact);
        editor.apply();
    }
}
