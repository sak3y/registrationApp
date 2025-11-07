package com.example.registrationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

class User {
    String user;
    String fullName;
    String pass;
    String gender;
    String bYear;
}

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = findViewById(R.id.username);
        EditText fullName = findViewById(R.id.fullName);
        EditText pass = findViewById(R.id.pass);
        EditText rePass = findViewById(R.id.rePass);

        RadioButton m_rb = findViewById(R.id.m_rb);
        RadioButton f_rb = findViewById(R.id.f_rb);

        TextView loginLink = findViewById(R.id.loginLink);

        // Spinner setup
        Spinner bySpinner = findViewById(R.id.bySpinner);
        bySpinner.setPrompt("Select birth year");
        ArrayList<String> years = new ArrayList<>();
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = currYear; y >= 1910; y--) {
            years.add(String.valueOf(y));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bySpinner.setAdapter(adapter);

        // Register button
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(v -> {
            User u = new User();
            u.user = username.getText().toString().trim();
            u.fullName = fullName.getText().toString().trim();
            u.pass = pass.getText().toString().trim();
            String confirm = rePass.getText().toString().trim();
            u.gender = m_rb.isChecked() ? "Male" : f_rb.isChecked() ? "Female" : "";
            u.bYear = bySpinner.getSelectedItem() != null ? bySpinner.getSelectedItem().toString() : "";


            SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
            String existingUser = sp.getString("username", "");

            // Error handling
            if (existingUser.equals(u.user)) {
                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                return; // stop registration
            }
            if (u.user.isEmpty() || u.fullName.isEmpty() || u.pass.isEmpty() || confirm.isEmpty() || u.gender.isEmpty() || u.bYear.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!u.pass.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save to SharedPreferences
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", u.user);
            editor.putString("fullname", u.fullName);
            editor.putString("password", u.pass);
            editor.putString("gender", u.gender);
            editor.putString("birthyear", u.bYear);
            editor.apply();

            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

            // Go to Login
            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        // Login send
        loginLink.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }
}
