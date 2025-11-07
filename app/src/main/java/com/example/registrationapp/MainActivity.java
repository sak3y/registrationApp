package com.example.registrationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView user = findViewById(R.id.user_in);
        TextView pass = findViewById(R.id.pass_in);
        TextView err = findViewById(R.id.err);
        TextView reg = findViewById(R.id.register);
        Button login = findViewById(R.id.loginBtn);


        // Sends to registration
        reg.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
        });


        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        login.setOnClickListener(v ->{
            String savedUser = sp.getString("username", "");
            String savedPass = sp.getString("password", "");

            if (user.getText().toString().equals(savedUser) &&
                    pass.getText().toString().equals(savedPass)) {
                Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(i);

                finish();
            }
            else if (user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                err.setText("Fill in the fields");
            }
            else {
                err.setText("Incorrect details");
            }
        });
    }
}
