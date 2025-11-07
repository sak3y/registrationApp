package com.example.registrationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView user = findViewById(R.id.user);
        TextView fullName = findViewById(R.id.fullName);
        TextView gender = findViewById(R.id.gender);
        TextView age = findViewById(R.id.age);
        Button logoutBtn = findViewById(R.id.logoutBtn);


        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = sp.getString("username", "Unknown User");
        String fullNameStr = sp.getString("fullname", "Not found");
        String genderStr = sp.getString("gender", "Not found");
        String ageStr = sp.getString("birthyear", "Not found");


        user.setText("Welcome, " + username);
        fullName.setText("Full Name: " + fullNameStr);
        gender.setText("Gender: " + genderStr);
        age.setText("Birth Year: " + ageStr);

        // Logout button
        logoutBtn.setOnClickListener(v ->{
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(i);

            finish();
        });
    }
}
