package com.TomerSch.quizgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuizManager {
    private final List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    public QuizManager() {
        // Initialize the list of questions with flag image resource IDs
        questions = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;
    }
    public void addQuestion(Question q)
    {
        questions.add(q);
    }

    public Question getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null; // Quiz is finished
    }

    public boolean checkAnswer(String userAnswer) {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            if (userAnswer.trim().equalsIgnoreCase(currentQuestion.getCorrectAnswer().trim()))
            {
                score++;
                return true;
            }
            return false;
        }
        return false;
    }

    public void nextQuestion() {
        currentQuestionIndex++;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public boolean isQuizFinished() {
        return currentQuestionIndex >= questions.size();
    }

    public void shuffleQuestions() {
        Collections.shuffle(questions);
    }
}