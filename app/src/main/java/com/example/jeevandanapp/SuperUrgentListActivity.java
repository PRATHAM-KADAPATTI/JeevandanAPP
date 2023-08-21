package com.example.jeevandanapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SuperUrgentListActivity extends AppCompatActivity {

    private ListView listViewSuperUrgentList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superurgent_list);

        listViewSuperUrgentList = findViewById(R.id.listViewSuperUrgentList);

        // Create an ArrayAdapter without any data initially
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        // Set the adapter on the ListView
        listViewSuperUrgentList.setAdapter(adapter);

        // Retrieve and display the super urgent recipients
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<String> superUrgentRecipients = databaseHelper.getSuperUrgentRecipients();
        adapter.addAll(superUrgentRecipients);
    }
}
