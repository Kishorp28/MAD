package com.example.blood;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class BloodBank extends Bottom {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloodbank);
        setupBottomNavigation(R.id.nav_database);

        Intent intent=getIntent();
    }
}
