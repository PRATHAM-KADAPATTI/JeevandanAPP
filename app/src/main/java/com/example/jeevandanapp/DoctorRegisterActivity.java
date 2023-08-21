package com.example.jeevandanapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorRegisterActivity extends AppCompatActivity {

    private Button registerButton, loginButton;
    private EditText fullNameEditText, usernameEditText, passwordEditText;
    private Spinner hospitalSpinner, specializationSpinner, genderSpinner;
    private DatabaseHelper databaseHelper;

    private ArrayAdapter<CharSequence> hospitalAdapter;
    private ArrayAdapter<CharSequence> specializationAdapter;
    private ArrayAdapter<CharSequence> genderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        fullNameEditText = findViewById(R.id.full_name_edittext);
        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        hospitalSpinner = findViewById(R.id.hospital_spinner);
        specializationSpinner = findViewById(R.id.specialization_spinner);
        genderSpinner = findViewById(R.id.gender_spinner);

        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.login_button);

        // Create ArrayAdapter for hospital spinner
        hospitalAdapter = ArrayAdapter.createFromResource(this, R.array.hospital_data, android.R.layout.simple_spinner_item);
        hospitalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitalSpinner.setAdapter(hospitalAdapter);

        // Create ArrayAdapter for specialization spinner
        specializationAdapter = ArrayAdapter.createFromResource(this, R.array.specialization_data, android.R.layout.simple_spinner_item);
        specializationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specializationSpinner.setAdapter(specializationAdapter);

        // Create ArrayAdapter for gender spinner
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_data, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the values entered by the user
                String fullName = fullNameEditText.getText().toString().trim();
                String hospitalName = hospitalSpinner.getSelectedItem().toString().trim();
                String specialization = specializationSpinner.getSelectedItem().toString().trim();
                String gender = genderSpinner.getSelectedItem().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Insert the data into the doctor table
                long result = databaseHelper.insertDoctor(fullName, hospitalName, specialization, gender, username, password);
                if (result != -1) {
                    Toast.makeText(DoctorRegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DoctorRegisterActivity.this, DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DoctorRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorRegisterActivity.this, DoctorLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
