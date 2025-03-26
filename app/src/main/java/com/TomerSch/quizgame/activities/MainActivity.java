package com.TomerSch.quizgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.TomerSch.quizgame.R;
import com.TomerSch.quizgame.utils.LocalScoreDataSource;
import com.TomerSch.quizgame.utils.QuizResult;
import com.TomerSch.quizgame.utils.ScoreRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonStartGame;
    private Button buttonViewScores;
    private Button buttonAdminLogin;
    private TextView textViewLastScore;
    private ScoreRepository scoreRepository; // Use the interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartGame = findViewById(R.id.buttonStartGame);
        buttonViewScores = findViewById(R.id.buttonViewScores);
        buttonAdminLogin = findViewById(R.id.buttonAdminLogin);
        textViewLastScore = findViewById(R.id.textViewLastScore);

        // Initialize with LocalScoreDataSource to view local scores
        scoreRepository = new LocalScoreDataSource(this);

        displayLastSavedScore();

        buttonStartGame.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        buttonViewScores.setOnClickListener(v -> {
            displayLocalScores();
        });

        buttonAdminLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });
    }

    private void displayLastSavedScore() {
        List<QuizResult> scores = scoreRepository.getScores();
        if (!scores.isEmpty()) {
            QuizResult lastResult = scores.get(scores.size() - 1);
            textViewLastScore.setText("Last Score: " + lastResult.getScore() + " / " + lastResult.getTotalQuestions());
        } else {
            textViewLastScore.setText("No local score saved yet.");
        }
    }

    private void displayLocalScores() {
        List<QuizResult> scores = scoreRepository.getScores();
        StringBuilder sb = new StringBuilder("Local Scores:\n");
        if (scores.isEmpty()) {
            sb.append("No scores saved.");
        } else {
            for (QuizResult result : scores) {
                sb.append(result.toString()).append("\n");
            }
        }
        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
    }
}