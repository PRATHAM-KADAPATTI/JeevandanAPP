package com.example.jeevandanapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    private Button button;
    private ListView listView;

    public SecondFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // Find the ListView
        listView = view.findViewById(R.id.donorListView);

        // Find the button and set click listener
        button = view.findViewById(R.id.donorButton);
        // ...

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), DonorRegisterActivity.class);
                startActivity(intent);
            }
        });
        // Retrieve donor list from the database
        List<String> donorList = getDonorList();

        // Set the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, donorList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the title
        requireActivity().setTitle("Donor Information");
    }
    private List<String> getDonorList() {
        List<String> donorList = new ArrayList<>();

        // Get a cursor with all the donors from the database
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        Cursor cursor = databaseHelper.getAllDonors();

        // Check if the cursor has data
        if (cursor != null && cursor.moveToFirst()) {
            int fullNameIndex = cursor.getColumnIndex(DatabaseHelper.DONOR_COLUMN_FULL_NAME);
            int genderIndex = cursor.getColumnIndex(DatabaseHelper.DONOR_COLUMN_GENDER);
            int organDonatingIndex = cursor.getColumnIndex(DatabaseHelper.DONOR_COLUMN_ORGAN_DONATING);
            int bloodGroupIndex = cursor.getColumnIndex(DatabaseHelper.DONOR_COLUMN_BLOOD_GROUP);
            int ageIndex = cursor.getColumnIndex(DatabaseHelper.DONOR_COLUMN_AGE);

            // Iterate over the cursor and extract donor information
            do {
                // Check if the column exists in the cursor
                if (fullNameIndex != -1 && genderIndex != -1 && organDonatingIndex != -1 && bloodGroupIndex != -1 && ageIndex != -1) {
                    String fullName = cursor.getString(fullNameIndex);
                    String gender = cursor.getString(genderIndex);
                    String organDonating = cursor.getString(organDonatingIndex);
                    String bloodGroup = cursor.getString(bloodGroupIndex);
                    String age = cursor.getString(ageIndex);

                    // Create a formatted string with donor information
                    String donorInfo = "Name: " + fullName + "\n" +
                            "Organ Donating: " + organDonating + "\n" ;

                    // Add the donor information to the list
                    donorList.add(donorInfo);
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor and database connection
        cursor.close();
        databaseHelper.close();

        return donorList;
    }

}
