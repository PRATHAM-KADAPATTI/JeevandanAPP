package com.example.jeevandanapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class RecipientDetailsActivity extends AppCompatActivity {

    private TextView textRecName, textRecOrganReq, textRecBloodGrp, textRecGender, textRecPhNo, textRecEmail, textUsername, textReport;
    private Button btnAllocOrgan, btnReport, btnSUS;
    private PDFView pdfView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_details);

        // Initialize views
        textRecName = findViewById(R.id.text_recName);
        textRecOrganReq = findViewById(R.id.text_recOrganReq);
        textRecBloodGrp = findViewById(R.id.text_recBloodGrp);
        textRecGender = findViewById(R.id.text_recGender);
        textRecPhNo = findViewById(R.id.text_recPhNo);
        textRecEmail = findViewById(R.id.text_recEmail);
        textUsername = findViewById(R.id.text_username);



        btnAllocOrgan = findViewById(R.id.btn_allocOrgan);
        btnReport = findViewById(R.id.btn_report);
        btnSUS = findViewById(R.id.btn_SUS);

        // Get the recipient's information from intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String recipientInfo = intent.getStringExtra("recipient_info");
            if (recipientInfo != null) {
                // Parse the recipient's information
                String[] recipientData = recipientInfo.split(", ");
                String name = recipientData[0].replace("Name: ", "");

                // Fetch recipient's details from the database
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                Cursor cursor = dbHelper.getRecipientDetails(name);

                if (cursor != null && cursor.moveToFirst()) {
                    // Verify the column indices
                    int genderIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_GENDER);
                    int organRequiredIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_ORGAN_REQUIRED);
                    int bloodGroupIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_BLOOD_GROUP);
                    int ageIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_AGE);
                    int emailIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_EMAIL);
                    int phoneNumberIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_PHONE_NUMBER);
                    int usernameIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_USERNAME);
                    int reportIndex = cursor.getColumnIndex(DatabaseHelper.RECIPIENT_COLUMN_FILE_PATH);

                    if (genderIndex >= 0 && organRequiredIndex >= 0 && bloodGroupIndex >= 0 &&
                            ageIndex >= 0 && emailIndex >= 0 && phoneNumberIndex >= 0) {
                        // Extract recipient's details from the cursor
                        String gender = cursor.getString(genderIndex);
                        String organRequired = cursor.getString(organRequiredIndex);
                        String bloodGroup = cursor.getString(bloodGroupIndex);
                        int age = cursor.getInt(ageIndex);
                        String email = cursor.getString(emailIndex);
                        String phoneNumber = cursor.getString(phoneNumberIndex);
                        String username = cursor.getString(usernameIndex);
                        String report = cursor.getString(reportIndex);

                        // Set the data to the TextViews
                        textRecName.setText(name);
                        textRecOrganReq.setText(organRequired);
                        textRecBloodGrp.setText(bloodGroup);
                        textRecGender.setText(gender);
                        textRecPhNo.setText(phoneNumber);
                        textRecEmail.setText(email);
                        textUsername.setText(username);

                        // Load and display the PDF


                        btnAllocOrgan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Get the organ required and blood group for the recipient
                                Pair<String, String> organAndBloodGroup = dbHelper.getOrganAndBloodGroupForRecipient(name);
                                String organRequired = organAndBloodGroup.first;
                                String bloodGroup = organAndBloodGroup.second;

                                // Perform the check with the donor database tables
                                boolean compatibleDonorFound = dbHelper.checkCompatibleDonor(organRequired, bloodGroup);

                                // Update the allocation status in the database
                                if (compatibleDonorFound) {
                                    dbHelper.updateAllocationStatus(name, "yes");
                                    Toast.makeText(RecipientDetailsActivity.this, "Organ Allocated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RecipientDetailsActivity.this, "No compatible donor found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });




                        btnSUS.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Update the allocation status in the database
                                dbHelper.updateSuperUrgentStatus(name);

                                // Show a toast message or perform any other action
                                Toast.makeText(RecipientDetailsActivity.this, "Moved to Super Urgent status", Toast.LENGTH_SHORT).show();
                            }
                        });

                        btnReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(RecipientDetailsActivity.this, PDFActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }

                // Close the cursor
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }


}