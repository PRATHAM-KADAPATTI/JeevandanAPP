package com.example.jeevandanapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Switch passwordToggleSwitch;
    private Button loginButton;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        databaseHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.edit_text);
        passwordEditText = findViewById(R.id.edit_text2);
        passwordToggleSwitch = findViewById(R.id.password_toggle);
        loginButton = findViewById(R.id.submit_button);
        registerButton = findViewById(R.id.reg_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidCredentials(username, password)) {
                    if (databaseHelper.doctorExists(username, password)) {

                        Intent profileIntent = new Intent(DoctorLoginActivity.this, DoctorProfileActivity.class);
                        profileIntent.putExtra("username", username);
                        startActivity(profileIntent);

                        Intent dashboardIntent = new Intent(DoctorLoginActivity.this, DashboardActivity.class);
                        startActivity(dashboardIntent);
                        Toast.makeText(DoctorLoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DoctorLoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(DoctorLoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });




        // Set a click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorLoginActivity.this, DoctorRegisterActivity.class);
                startActivity(intent);
            }
        });

        // Set a listener for the password toggle switch
        passwordToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toggle the password visibility based on the switch state
                if (isChecked) {
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                // Move the cursor to the end of the text
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {
        // Replace with your own logic to validate the username and password
        return !username.isEmpty() && !password.isEmpty();
    }
}
