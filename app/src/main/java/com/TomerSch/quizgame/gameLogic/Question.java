package com.TomerSch.quizgame.gameLogic;

public class Question {
    private final int flagImageResourceId;
    private final String correctAnswer;

    public Question(int flagImageResourceId, String correctAnswer) {
        this.flagImageResourceId = flagImageResourceId;
        this.correctAnswer = correctAnswer;
    }

    public int getFlagImageResourceId() {
        return flagImageResourceId;
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }
}