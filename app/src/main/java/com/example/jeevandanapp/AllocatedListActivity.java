package com.example.jeevandanapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AllocatedListActivity extends AppCompatActivity {

    private ListView listViewAllocatedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocated_list);

        listViewAllocatedList = findViewById(R.id.listViewAllocatedList);

        // Create an ArrayAdapter without any data initially
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        // Set the adapter on the ListView
        listViewAllocatedList.setAdapter(adapter);

        // Retrieve and display the allocated recipients
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<String> allocatedRecipients = databaseHelper.getAllocatedRecipients();
        adapter.addAll(allocatedRecipients);
    }
}
