package com.example.blood;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends Bottom {

    TextView user;
    GridLayout bloodContainer;
    LinearLayout donorContainer;
    Button donate;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        setupBottomNavigation(R.id.nav_home);


        // Initialize Views
        donate = findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Donate.class);
                startActivity(intent);
            }
        });
        user = findViewById(R.id.user);
        bloodContainer = findViewById(R.id.bloodContainer);
        donorContainer = findViewById(R.id.donorContainer);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        // Get Name From Login
        String nameFromLogin = getIntent().getStringExtra("userName");
        if (nameFromLogin != null) {
            user.setText(nameFromLogin);
        }

        loadBloodAvailability();
        loadNearbyDonors();
    }

    // ===============================
    // BLOOD AVAILABILITY SECTION
    // ===============================
    private void loadBloodAvailability() {

        databaseReference.child("BloodAvailability")
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        bloodContainer.removeAllViews();

                        for (DataSnapshot bloodSnap : snapshot.getChildren()) {

                            String group = bloodSnap.getKey();
                            String status = bloodSnap.getValue(String.class);

                            // Create Card
                            CardView card = new CardView(Dashboard.this);
                            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                            params.width = 0;
                            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                            params.setMargins(20, 20, 20, 20);
                            card.setLayoutParams(params);
                            card.setRadius(30);
                            card.setCardElevation(8);

                            // Inner Layout
                            LinearLayout layout = new LinearLayout(Dashboard.this);
                            layout.setOrientation(LinearLayout.VERTICAL);
                            layout.setPadding(50, 50, 50, 50);

                            // Blood Group Text
                            TextView tvGroup = new TextView(Dashboard.this);
                            tvGroup.setText(group);
                            tvGroup.setTextSize(22);
                            tvGroup.setTypeface(null, Typeface.BOLD);
                            tvGroup.setTextColor(Color.BLACK);

                            // Status Text
                            TextView tvStatus = new TextView(Dashboard.this);
                            tvStatus.setText(status);
                            tvStatus.setTextSize(14);
                            tvStatus.setPadding(0, 10, 0, 0);

                            // Status Color Logic
                            if (status != null) {
                                if (status.equalsIgnoreCase("Available")) {
                                    tvStatus.setTextColor(Color.parseColor("#2E7D32")); // Green
                                } else if (status.equalsIgnoreCase("Low")) {
                                    tvStatus.setTextColor(Color.parseColor("#F9A825")); // Orange
                                } else {
                                    tvStatus.setTextColor(Color.parseColor("#C62828")); // Red
                                }
                            }

                            layout.addView(tvGroup);
                            layout.addView(tvStatus);

                            card.addView(layout);
                            bloodContainer.addView(card);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    // ===============================
    // NEARBY DONORS SECTION
    // ===============================
    private void loadNearbyDonors() {

        databaseReference.child("Donors")
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        donorContainer.removeAllViews();

                        for (DataSnapshot donorSnap : snapshot.getChildren()) {

                            String donorName = donorSnap.child("name").getValue(String.class);
                            String bloodGroup = donorSnap.child("bloodGroup").getValue(String.class);
                            String distance = donorSnap.child("distance").getValue(String.class);

                            // Create Card
                            CardView card = new CardView(Dashboard.this);
                            LinearLayout.LayoutParams params =
                                    new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0, 0, 0, 30);
                            card.setLayoutParams(params);
                            card.setRadius(30);
                            card.setCardElevation(8);

                            // Inner Layout
                            LinearLayout layout = new LinearLayout(Dashboard.this);
                            layout.setOrientation(LinearLayout.VERTICAL);
                            layout.setPadding(50, 50, 50, 50);

                            // Donor Name
                            TextView tvName = new TextView(Dashboard.this);
                            tvName.setText(donorName);
                            tvName.setTextSize(18);
                            tvName.setTypeface(null, Typeface.BOLD);
                            tvName.setTextColor(Color.BLACK);

                            // Details
                            TextView tvDetails = new TextView(Dashboard.this);
                            tvDetails.setText(bloodGroup + " • " + distance);
                            tvDetails.setTextSize(14);
                            tvDetails.setTextColor(Color.DKGRAY);
                            tvDetails.setPadding(0, 10, 0, 0);

                            layout.addView(tvName);
                            layout.addView(tvDetails);

                            card.addView(layout);
                            donorContainer.addView(card);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}
