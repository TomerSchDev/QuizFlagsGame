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
import com.TomerSch.quizgame.utils.RemoteScoreDataSource;
import com.TomerSch.quizgame.utils.ScoreRepository;

public class ResultsActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button saveButton;
    private Button sendToServerButton;
    private Button buttonReturnMain;
    private int score;
    private int totalQuestions;
    private ScoreRepository scoreRepository; // Interface for score operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        scoreTextView = findViewById(R.id.scoreTextView);
        saveButton = findViewById(R.id.saveButton);
        sendToServerButton = findViewById(R.id.sendToServerButton);
        buttonReturnMain = findViewById(R.id.buttonReturnMain);

        // Get the score and total questions from the intent
        score = getIntent().getIntExtra("score", 0);
        totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        scoreTextView.setText("Your Score: " + score + " / " + totalQuestions);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use LocalScoreDataSource to save locally
                scoreRepository = new LocalScoreDataSource(ResultsActivity.this);
                scoreRepository.saveScore(new QuizResult(score, totalQuestions));
                Toast.makeText(ResultsActivity.this, "Result saved locally!", Toast.LENGTH_SHORT).show();
            }
        });

        sendToServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use RemoteScoreDataSource to send to server
                scoreRepository = new RemoteScoreDataSource();
                scoreRepository.saveScore(new QuizResult(score, totalQuestions));
                Toast.makeText(ResultsActivity.this, "Sending to server...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonReturnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}