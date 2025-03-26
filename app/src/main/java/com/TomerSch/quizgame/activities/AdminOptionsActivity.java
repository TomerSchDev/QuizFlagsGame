package com.TomerSch.quizgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TomerSch.quizgame.gameLogic.GameManager;
import com.TomerSch.quizgame.gameLogic.QuizManager;
import com.TomerSch.quizgame.R;

public class AdminOptionsActivity extends AppCompatActivity {

    private TextView textViewQuizStatus;
    private Button buttonViewLocalResults;
    private Button buttonViewServerResults;
    private Button buttonAddQuestion;
    private Button buttonEditQuestion;
    private Button buttonDeleteQuestion;
    private EditText editTextSetScore;
    private EditText editTextChangeQuestion;
    private Button buttonApplyChanges;

    private GameManager gameManager; // Get an instance of GameManager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        gameManager = GameManager.getInstance(); // Initialize GameManager

        textViewQuizStatus = findViewById(R.id.textViewQuizStatus);
        buttonViewLocalResults = findViewById(R.id.buttonViewLocalResults);
        buttonViewServerResults = findViewById(R.id.buttonViewServerResults);
        buttonAddQuestion = findViewById(R.id.buttonAddQuestion);
        buttonEditQuestion = findViewById(R.id.buttonEditQuestion);
        buttonDeleteQuestion = findViewById(R.id.buttonDeleteQuestion);
        editTextSetScore = findViewById(R.id.editTextSetScore);
        editTextChangeQuestion = findViewById(R.id.editTextChangeQuestion);
        buttonApplyChanges = findViewById(R.id.buttonApplyChanges);
        updateQuizStatus(); // Initial update of the quiz status

        buttonViewLocalResults.setOnClickListener(v ->
                Toast.makeText(AdminOptionsActivity.this, "Viewing local results...", Toast.LENGTH_SHORT).show());

        buttonViewServerResults.setOnClickListener(v ->
                Toast.makeText(AdminOptionsActivity.this, "Viewing server results...", Toast.LENGTH_SHORT).show());

        buttonAddQuestion.setOnClickListener(v ->
                Toast.makeText(AdminOptionsActivity.this, "Adding new question...", Toast.LENGTH_SHORT).show());

        buttonEditQuestion.setOnClickListener(v ->
                Toast.makeText(AdminOptionsActivity.this, "Editing question...", Toast.LENGTH_SHORT).show());

        buttonDeleteQuestion.setOnClickListener(v ->
                Toast.makeText(AdminOptionsActivity.this, "Deleting question...", Toast.LENGTH_SHORT).show());

        buttonApplyChanges.setOnClickListener(v -> applyManualChanges());


        findViewById(R.id.buttonReturnGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminOptionsActivity.this, QuizActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        });
    }

    private void updateQuizStatus() {
        // Assuming you have access to the QuizManager instance somewhere
        // For example, if GameManager holds the QuizManager
        QuizManager quizManager = gameManager.getQuizManager(); // You might need to add a getter in GameManager

        if (quizManager != null) {
            int currentQuestion = quizManager.getCurrentQuestionIndex() + 1; // Display 1-based index
            int totalQuestions = quizManager.getTotalQuestions();
            int score = quizManager.getScore();
            textViewQuizStatus.setText("Question: " + currentQuestion + " / " + totalQuestions + ", Score: " + score);
        } else {
            textViewQuizStatus.setText("Quiz not active.");
        }
    }

    private void applyManualChanges() {
        String scoreStr = editTextSetScore.getText().toString();
        String questionNumStr = editTextChangeQuestion.getText().toString();

        QuizManager quizManager = gameManager.getQuizManager(); // Get QuizManager

        if (quizManager != null) {
            if (!scoreStr.isEmpty()) {
                try {
                    int newScore = Integer.parseInt(scoreStr);
                    quizManager.setScore(newScore); // You'll need to add a setScore() method to QuizManager
                    Toast.makeText(this, "Score set to " + newScore, Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid score format", Toast.LENGTH_SHORT).show();
                }
            }

            if (!questionNumStr.isEmpty()) {
                try {
                    int newQuestionIndex = Integer.parseInt(questionNumStr) - 1; // Convert to 0-based index
                    if (newQuestionIndex >= 0 && newQuestionIndex < quizManager.getTotalQuestions()) {
                        quizManager.setCurrentQuestionIndex(newQuestionIndex); // Add a setCurrentQuestionIndex() to QuizManager
                        loadQuestionFromQuizActivity(); // Update the UI in QuizActivity if it's active
                        Toast.makeText(this, "Changed to question " + (newQuestionIndex + 1), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Invalid question number", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid question number format", Toast.LENGTH_SHORT).show();
                }
            }
            updateQuizStatus(); // Update the status after applying changes
        } else {
            Toast.makeText(this, "Quiz not active.", Toast.LENGTH_SHORT).show();
        }

    }


    // Placeholder for updating UI in QuizActivity (you'll need a way to communicate)
    private void loadQuestionFromQuizActivity() {
        // This is a simplified example. You might need to use a BroadcastReceiver,
        // an interface, or some other mechanism to communicate with QuizActivity
        Toast.makeText(this, "Quiz UI needs to be updated", Toast.LENGTH_SHORT).show();
    }
}
