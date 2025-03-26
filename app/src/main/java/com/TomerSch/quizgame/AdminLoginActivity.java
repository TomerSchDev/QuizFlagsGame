package com.TomerSch.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText editTextAdminUsername;
    private EditText editTextAdminPassword;
    private Button buttonAdminLogin;

    // You'll need to define your admin credentials here (for simplicity in this example)



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        editTextAdminUsername = findViewById(R.id.editTextAdminUsername);
        editTextAdminPassword = findViewById(R.id.editTextAdminPassword);
        buttonAdminLogin = findViewById(R.id.buttonAdminLogin);

        buttonAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextAdminUsername.getText().toString();
                String password = editTextAdminPassword.getText().toString();
                Map<String,String>users=GameManager.getInstance().getAdminCredentials();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AdminLoginActivity.this, "Please enter both username and password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (users.containsKey(username) && Objects.equals(users.get(username), password)) {
                    // Login successful, start AdminOptionsActivity
                    Intent intent = new Intent(AdminLoginActivity.this, AdminOptionsActivity.class);
                    startActivity(intent);
                    finish(); // Close the login activity
                } else {
                    // Login failed
                    Toast.makeText(AdminLoginActivity.this, "Login failed. Invalid credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}