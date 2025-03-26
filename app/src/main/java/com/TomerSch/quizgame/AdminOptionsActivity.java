package com.TomerSch.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminOptionsActivity extends AppCompatActivity {

    private Button buttonViewLocalResults;
    private Button buttonViewServerResults;
    private Button buttonAddQuestion;
    private Button buttonEditQuestion;
    private Button buttonDeleteQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        buttonViewLocalResults = findViewById(R.id.buttonViewLocalResults);
        buttonViewServerResults = findViewById(R.id.buttonViewServerResults);
        buttonAddQuestion = findViewById(R.id.buttonAddQuestion);
        buttonEditQuestion = findViewById(R.id.buttonEditQuestion);
        buttonDeleteQuestion = findViewById(R.id.buttonDeleteQuestion);

        buttonViewLocalResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to view locally saved results
                Toast.makeText(AdminOptionsActivity.this, "Viewing local results...", Toast.LENGTH_SHORT).show();
                // You might start a new Activity or Fragment to display the data
            }
        });

        buttonViewServerResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to view server results
                Toast.makeText(AdminOptionsActivity.this, "Viewing server results...", Toast.LENGTH_SHORT).show();
                // This would likely involve making network requests
            }
        });

        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to add a new question
                Toast.makeText(AdminOptionsActivity.this, "Adding new question...", Toast.LENGTH_SHORT).show();
                // You might start a new Activity or show a dialog
            }
        });

        buttonEditQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to edit an existing question
                Toast.makeText(AdminOptionsActivity.this, "Editing question...", Toast.LENGTH_SHORT).show();
                // You might start a new Activity or show a dialog with a list of questions
            }
        });

        buttonDeleteQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to delete a question
                Toast.makeText(AdminOptionsActivity.this, "Deleting question...", Toast.LENGTH_SHORT).show();
                // You might show a dialog with a list of questions to select from
            }
        });
    }
}