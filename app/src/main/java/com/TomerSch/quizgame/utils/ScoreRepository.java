package com.TomerSch.quizgame.utils;

import java.util.List;

public interface ScoreRepository {
    void saveScore(QuizResult result);
    List<QuizResult> getScores();
}
