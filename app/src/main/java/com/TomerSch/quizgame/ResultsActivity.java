package com.TomerSch.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager; // Deprecated, but often used
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ResultsActivity extends AppCompatActivity {

    private int score;
    private int totalQuestions;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        Button saveButton = findViewById(R.id.saveButton);
        Button sendToServerButton = findViewById(R.id.sendToServerButton);

        // Get the score and total questions from the intent
        score = getIntent().getIntExtra("score", 0);
        totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        scoreTextView.setText("Your Score: " + score + " / " + totalQuestions);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ResultsActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("last_score", score);
                editor.putInt("last_total_questions", totalQuestions);
                editor.apply();

                Toast.makeText(ResultsActivity.this, "Result saved locally!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        sendToServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("YOUR_PYTHON_SERVER_URL_HERE"); // Replace with your server URL
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            connection.setDoOutput(true);

                            // Create the JSON data to send
                            String jsonData = "{\"score\": " + score + ", \"total_questions\": " + totalQuestions + "}";

                            // Send the JSON data
                            try (OutputStream os = connection.getOutputStream()) {
                                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                                os.write(input, 0, input.length);
                            }

                            int responseCode = connection.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                runOnUiThread(() -> Toast.makeText(ResultsActivity.this, "Result sent to server!", Toast.LENGTH_SHORT).show());
                            } else {
                                runOnUiThread(() -> Toast.makeText(ResultsActivity.this, "Error sending result to server: " + responseCode, Toast.LENGTH_SHORT).show());
                            }

                            connection.disconnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(() -> Toast.makeText(ResultsActivity.this, "Error sending result to server: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    }
                }).start();
            }
        });
    }
}