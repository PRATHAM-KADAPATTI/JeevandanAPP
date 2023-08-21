package com.example.jeevandanapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorProfileActivity extends AppCompatActivity {

    private TextView docNameTextView;
    private TextView usernameTextView;
    private TextView hospNameTextView;
    private TextView specTextView;
    private TextView genderTextView;

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        // Initialize TextViews
        docNameTextView = findViewById(R.id.text_docName);
        usernameTextView = findViewById(R.id.text_usrname);
        hospNameTextView = findViewById(R.id.text_hospName);
        specTextView = findViewById(R.id.text_spec);
        genderTextView = findViewById(R.id.text_gender);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        String username = getIntent().getStringExtra("username");
        Cursor cursor = databaseHelper.getDoctorProfileDetails(username);

        if (cursor.moveToFirst()) {
            String fullName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COLUMN_FULL_NAME));
            String hospitalName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COLUMN_HOSPITAL_NAME));
            String specialization = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COLUMN_SPECIALIZATION));
            String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COLUMN_GENDER));

            // Inside the onCreate() method of DoctorProfileActivity, after retrieving the profile details
            docNameTextView.setText(fullName);
            usernameTextView.setText(username);

            hospNameTextView.setText(hospitalName);
            hospNameTextView.setTextColor(Color.BLACK);


            specTextView.setText(specialization);
            specTextView.setTextColor(Color.BLACK);

            genderTextView.setText(gender);
            genderTextView.setTextColor(Color.BLACK);


        }




        logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorProfileActivity.this, DoctorLoginActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent dashboardIntent = new Intent(DoctorProfileActivity.this, DashboardActivity.class);
        startActivity(dashboardIntent);
        finish(); // Optional: finish the current activity if necessary
    }

}



