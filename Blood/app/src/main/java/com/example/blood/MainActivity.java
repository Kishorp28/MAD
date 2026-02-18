package com.example.blood;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button getstarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        getstarted=findViewById(R.id.getstarted);

    }
    public void getstarted(View view){
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);

    }

}