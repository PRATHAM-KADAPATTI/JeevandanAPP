package com.example.jeevandanapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private ListView listViewRecipients;
    private DatabaseHelper dbHelper;
    private List<String> recipientsList;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(requireContext());

        // Find the ListView and set the adapter
        listViewRecipients = view.findViewById(R.id.listView_recipients);
        recipientsList = dbHelper.getAllRecipients();

        // Create an ArrayAdapter to display the recipient data in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, recipientsList);
        listViewRecipients.setAdapter(adapter);

        // Set item click listener
        listViewRecipients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the recipient's information
                String recipientInfo = recipientsList.get(position);

                // Open RecipientDetailsActivity and pass the recipient's information
                Intent intent = new Intent(requireContext(), RecipientDetailsActivity.class);
                intent.putExtra("recipient_info", recipientInfo);
                startActivity(intent);
            }
        });
        return view;
    }
}