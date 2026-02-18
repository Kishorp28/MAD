package com.example.blood;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class Bottom extends AppCompatActivity {

    protected void setupBottomNavigation(int selectedItemId) {

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                if (!(this instanceof Dashboard)) {
                    startActivity(new Intent(this, Dashboard.class));
                    finish();
                }

            } else if (id == R.id.nav_add) {

                if (!(this instanceof Request)) {
                    startActivity(new Intent(this, Request.class));
                    finish();
                }

            } else if (id == R.id.nav_database) {

                if (!(this instanceof BloodBank)) {
                    startActivity(new Intent(this, BloodBank.class));
                    finish();
                }

            } else if (id == R.id.nav_calendar) {

                if (!(this instanceof Donate)) {
                    startActivity(new Intent(this, Donate.class));
                    finish();
                }

            } else if (id == R.id.nav_profile) {

                if (!(this instanceof Profile)) {
                    startActivity(new Intent(this, Profile.class));
                    finish();
                }

            }

            return true;
        });

    }
}
