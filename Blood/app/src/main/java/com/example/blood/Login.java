package com.example.blood;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login, signup;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        login.setOnClickListener(view -> {

            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();

            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
                Toast.makeText(Login.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            checkUser(userEmail, userPassword);
        });

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
        });
    }

    private void checkUser(String userEmail, String userPassword) {

        databaseReference.orderByChild("email").equalTo(userEmail)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()) {
                            Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {

                            String dbPassword = userSnapshot.child("password").getValue(String.class);
                            String userName = userSnapshot.child("name").getValue(String.class);
                            String userId = userSnapshot.getKey();

                            if (dbPassword != null && dbPassword.equals(userPassword)) {

                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Login.this, Dashboard.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("userName", userName);
                                startActivity(intent);
                                finish();
                                return; // 🔥 stop loop after success
                            }
                        }

                        // If password didn't match any record
                        Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this, "Database Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
