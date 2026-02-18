package com.example.blood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText name, mobile, email, password, city, address;
    Spinner bloodgroup;
    Button btnSignup;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Connect Views
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        bloodgroup = findViewById(R.id.bloodgroup);
        btnSignup = findViewById(R.id.btnSignup);

        // Setup Spinner Data
        String[] groups = {"Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                groups
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroup.setAdapter(adapter);

        // Firebase Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Button Click
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString().trim();
                String m = mobile.getText().toString().trim();
                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();
                String c = city.getText().toString().trim();
                String a = address.getText().toString().trim();

                Object selectedItem = bloodgroup.getSelectedItem();

                if (selectedItem == null) {
                    Toast.makeText(Signup.this, "Please select blood group", Toast.LENGTH_SHORT).show();
                    return;
                }

                String b = selectedItem.toString();

                if (n.isEmpty() || m.isEmpty() || e.isEmpty() || p.isEmpty()
                        || c.isEmpty() || a.isEmpty()
                        || b.equals("Select Blood Group")) {

                    Toast.makeText(Signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                } else {

                    String userId = databaseReference.push().getKey();

                    User user = new User(n, m, e, p, c, a, b);

                    databaseReference.child(userId).setValue(user);

                    Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                    // Clear fields
                    name.setText("");
                    mobile.setText("");
                    email.setText("");
                    password.setText("");
                    city.setText("");
                    address.setText("");
                    bloodgroup.setSelection(0);
                }
            }
        });
    }
}
