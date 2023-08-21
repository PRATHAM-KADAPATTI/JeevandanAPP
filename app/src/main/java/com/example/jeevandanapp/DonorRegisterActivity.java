package com.example.jeevandanapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class DonorRegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private DatabaseHelper databaseHelper;
    private TextInputEditText fullNameEditText , ageEditText;
    private Spinner genderSpinner, organRequiredSpinner, bloodGroupSpinner;
    private ArrayAdapter<CharSequence> genderAdapter;
    private ArrayAdapter<CharSequence> bgAdapter;
    private ArrayAdapter<CharSequence> orgReqAdapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);

        registerButton = findViewById(R.id.register_button);
        databaseHelper = new DatabaseHelper(this);

        fullNameEditText = findViewById(R.id.full_name_edittext);
        genderSpinner = findViewById(R.id.gender_spinner);
        organRequiredSpinner = findViewById(R.id.dnrOrg_spinner);
        bloodGroupSpinner = findViewById(R.id.bloodGroup_spinner);
        ageEditText = findViewById(R.id.age_edittext);


        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_data, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        orgReqAdapter = ArrayAdapter.createFromResource(this, R.array.organ_data, android.R.layout.simple_spinner_item);
        orgReqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        organRequiredSpinner.setAdapter(orgReqAdapter);

        bgAdapter = ArrayAdapter.createFromResource(this, R.array.blood_group_data, android.R.layout.simple_spinner_item);
        bgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupSpinner.setAdapter(bgAdapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String fullName = fullNameEditText.getText().toString().trim();
                String gender = genderSpinner.getSelectedItem().toString();
                String organRequired = organRequiredSpinner.getSelectedItem().toString();
                String bloodGroup = bloodGroupSpinner.getSelectedItem().toString();
                String age = ageEditText.getText().toString().trim();


                // Check if any field is empty
                if (fullName.isEmpty() || age.isEmpty()) {
                    Toast.makeText(DonorRegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert recipient into the database
                long donorID = databaseHelper.insertDonor(fullName, gender, organRequired, bloodGroup, age);

                if (donorID != -1) {
                    // Registration successful, navigate to RecipientProfileActivity
                    Toast.makeText(DonorRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DonorRegisterActivity.this, SecondFragment.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DonorRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
