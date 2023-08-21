package com.example.jeevandanapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipientProfileActivity extends AppCompatActivity {

    private TextView recipientNameTextView;
    private TextView usernameTextView;
    private TextView genderTextView;
    private TextView organRequiredTextView;
    private TextView bloodGroupTextView;
    private TextView emailTextView;
    private TextView ageTextView;
    private TextView phoneNumberTextView;
    private TextView allocStatus;

    private Button logoutButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_profile);

        // Initialize TextViews
        recipientNameTextView = findViewById(R.id.text_docName);
        usernameTextView = findViewById(R.id.text_usrname);
        genderTextView = findViewById(R.id.text_gender);
        organRequiredTextView = findViewById(R.id.text_organ);
        bloodGroupTextView = findViewById(R.id.text_bloodGroup);
        emailTextView = findViewById(R.id.text_email);
        ageTextView = findViewById(R.id.text_age);
        phoneNumberTextView = findViewById(R.id.text_phone);
        allocStatus = findViewById(R.id.text_Alloc);

        // Initialize Logout Button
        logoutButton = findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout action
                logout();
            }
        });



        // Inside the onCreate() method of DoctorProfileActivity
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        // Inside the onCreate() method of DoctorProfileActivity, after initializing TextView widgets
        String username = getIntent().getStringExtra("username");
        Cursor cursor = databaseHelper.getRecipientProfileDetails(username);

        // Inside the onCreate() method of DoctorProfileActivity, after retrieving the cursor
        if (cursor.moveToFirst()) {
            int fullNameIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_FULL_NAME);
            int genderIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_GENDER);
            int organIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_ORGAN_REQUIRED);
            int bloodIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_BLOOD_GROUP);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_EMAIL);
            int phoneIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_PHONE_NUMBER);
            int ageIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_AGE);
            int allocIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_ALLOCATION_STATUS);

            // Check if the column indexes are valid
            if (fullNameIndex != -1 && genderIndex != -1 && organIndex != -1 && bloodIndex != -1 &&
                    emailIndex != -1 && phoneIndex != -1 && ageIndex != -1) {

                // Retrieve the values from the cursor
                String fullName = cursor.getString(fullNameIndex);
                String gender = cursor.getString(genderIndex);
                String organ = cursor.getString(organIndex);
                String blood = cursor.getString(bloodIndex);
                String email = cursor.getString(emailIndex);
                String phone = cursor.getString(phoneIndex);
                String age = cursor.getString(ageIndex);
                String alloc = cursor.getString(allocIndex);

                // Update the TextViews with the recipient's information
                recipientNameTextView.setText(fullName);
                usernameTextView.setText(username);

                genderTextView.setText(gender);
                genderTextView.setTextColor(Color.BLACK);
                genderTextView.setTypeface(null, Typeface.BOLD);

                organRequiredTextView.setText(organ);
                organRequiredTextView.setTextColor(Color.BLACK);

                bloodGroupTextView.setText(blood);
                bloodGroupTextView.setTextColor(Color.BLACK);

                emailTextView.setText(email);
                emailTextView.setTextColor(Color.BLACK);

                ageTextView.setText(age);
                ageTextView.setTextColor(Color.BLACK);

                phoneNumberTextView.setText(phone);
                phoneNumberTextView.setTextColor(Color.BLACK);

                if(alloc.equals("yes")){
                    allocStatus.setText("Organ Allocated");
                    allocStatus.setTextColor(Color.GREEN);
                }
                else {
                    allocStatus.setText("Organ yet to be Allocated");
                    allocStatus.setTextColor(Color.BLUE);
                }
            } else {
                // Handle the case where the column indexes are invalid
                Toast.makeText(RecipientProfileActivity.this, "Error retrieving recipient information", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where the cursor is empty
            Toast.makeText(RecipientProfileActivity.this, "No recipient found", Toast.LENGTH_SHORT).show();
        }




    }

    private void logout() {
        // Perform logout actions and navigate back to the appropriate screen
        Intent intent = new Intent(RecipientProfileActivity.this, RecipientLoginActivity.class);
        startActivity(intent);
        finish(); // Optional: finish the current activity if necessary
    }
}
