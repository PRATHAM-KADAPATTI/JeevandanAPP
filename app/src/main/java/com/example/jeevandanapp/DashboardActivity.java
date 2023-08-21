package com.example.jeevandanapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class DashboardActivity extends AppCompatActivity {

    private Button AllocList, SuperUrgentList, DocProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        AllocList = (Button) findViewById(R.id.btn_alloList);
        SuperUrgentList = (Button) findViewById(R.id.btn_superUrgentList);
        DocProfile = (Button) findViewById(R.id.btn_viewProfile);

        DocProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DoctorProfileActivity.class);
                startActivity(intent);
            }
        });

        AllocList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AllocatedListActivity.class);
                startActivity(intent);

            }
        });

        SuperUrgentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SuperUrgentListActivity.class);
                startActivity(intent);

            }
        });

    }
}