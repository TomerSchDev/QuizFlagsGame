package com.TomerSch.quizgame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocalScoreDataSource implements ScoreRepository {

    private static final String TAG = "LocalScoreDataSource";
    private static final String PREF_SCORES = "quiz_scores";

    private Context context;
    private Gson gson;

    public LocalScoreDataSource(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    @Override
    public void saveScore(QuizResult result) {
        List<QuizResult> scores = getLocalScoresInternal();
        scores.add(result);
        saveScoresInternal(scores);
        Log.d(TAG, "Score saved locally: " + result);
    }

    @Override
    public List<QuizResult> getScores() {
        return getLocalScoresInternal();
    }

    private List<QuizResult> getLocalScoresInternal() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String scoresJson = preferences.getString(PREF_SCORES, null);

        if (scoresJson == null) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<List<QuizResult>>() {}.getType();
        List<QuizResult> scores = gson.fromJson(scoresJson, listType);
        return scores != null ? scores : new ArrayList<>();
    }

    private void saveScoresInternal(List<QuizResult> scores) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String scoresJson = gson.toJson(scores);
        editor.putString(PREF_SCORES, scoresJson);
        editor.apply();
    }

    // Additional local-specific methods if needed, e.g., clearLocalScores()
    public void clearLocalScores() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(PREF_SCORES);
        editor.apply();
        Log.d(TAG, "Local scores cleared");
    }
}