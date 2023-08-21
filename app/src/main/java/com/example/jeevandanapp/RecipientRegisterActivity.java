package com.example.jeevandanapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RecipientRegisterActivity extends AppCompatActivity {

    private Button registerButton, loginButton, uploadButton;
    private DatabaseHelper databaseHelper;
    private TextInputEditText fullNameEditText, usernameEditText, emailEditText, ageEditText,
            phoneNumberEditText, passwordEditText;
    private Spinner genderSpinner, organRequiredSpinner, bloodGroupSpinner;
    private ArrayAdapter<CharSequence> genderAdapter;
    private ArrayAdapter<CharSequence> bgAdapter;
    private ArrayAdapter<CharSequence> orgReqAdapter;
    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private String selectedFilePath;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_register);

        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.login_button);
        uploadButton = findViewById(R.id.upload_button);
        databaseHelper = new DatabaseHelper(this);

        fullNameEditText = findViewById(R.id.full_name_edittext);
        genderSpinner = findViewById(R.id.gender_spinner);
        organRequiredSpinner = findViewById(R.id.orgReq_spinner);
        bloodGroupSpinner = findViewById(R.id.bloodGroup_spinner);
        usernameEditText = findViewById(R.id.username_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        ageEditText = findViewById(R.id.age_edittext);
        phoneNumberEditText = findViewById(R.id.phno_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

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
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String age = ageEditText.getText().toString().trim();
                String phoneNumber = phoneNumberEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Check if any field is empty
                if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || age.isEmpty() ||
                        phoneNumber.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RecipientRegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert recipient into the database
                long recipientId = databaseHelper.insertRecipient(fullName, gender, organRequired, bloodGroup,
                        username, email, age, phoneNumber, password, selectedFilePath);

                if (recipientId != -1) {
                    // Registration successful, navigate to RecipientProfileActivity
                    Toast.makeText(RecipientRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecipientRegisterActivity.this, RecipientProfileActivity.class);
                    intent.putExtra("recipient_id", recipientId);
                    startActivity(intent);
                    finish(); // Optional: Close this activity
                } else {
                    Toast.makeText(RecipientRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open file picker dialog
                openFilePicker();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipientRegisterActivity.this, RecipientLoginActivity.class);
                startActivity(intent);
                finish(); // Optional: Close this activity
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), FILE_PICKER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the selected file path
            Uri uri = data.getData();
            selectedFilePath = uri.getPath();

            // TODO: Store the file path or perform any other actions with it
        }
    }
}
