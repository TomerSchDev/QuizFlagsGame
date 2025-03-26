package com.TomerSch.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private GameManager gm;
    private ImageView flagImageView;
    private EditText answerEditText;
    private static final String TAG = "QuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        gm = GameManager.getInstance();

        flagImageView = findViewById(R.id.flagImageView);
        answerEditText = findViewById(R.id.answerEditText);
        Button submitButton = findViewById(R.id.submitButton);

        loadQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = answerEditText.getText().toString();

                if (!userAnswer.isEmpty()) {
                    boolean isCorrect = gm.checkAnswer(userAnswer, QuizActivity.this);

                    if (isCorrect) {
                        Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(QuizActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    }
                    gm.nextQuestion();
                    if (gm.isQuizFinished()) {
                        // Go to the results activity
                        Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
                        intent.putExtra("score", gm.getScore());
                        intent.putExtra("totalQuestions", gm.getTotalQuestions());

                        startActivity(intent);
                        finish(); // Close this activity
                    } else {
                        loadQuestion();
                        answerEditText.setText(""); // Clear the EditText for the next question
                    }
                } else {
                    Toast.makeText(QuizActivity.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadQuestion() {
        Question currentQuestion = gm.getCurrentQuestion();
        if (currentQuestion != null) {
            flagImageView.setImageResource(currentQuestion.getFlagImageResourceId());
        }
    }
}