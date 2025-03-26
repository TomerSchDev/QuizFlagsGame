package com.TomerSch.quizgame.utils;


import android.os.Build;
import android.util.Log;

import com.TomerSch.quizgame.gameLogic.GameManager;
import com.google.gson.JsonObject;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class QuizResult {
    private int score;
    private static String TAG="QuizResult";
    private int totalQuestions;
    private  String userId; // Optional: to store the user identifier
    private String timestamp; // Optional: to store when the result was saved

    public QuizResult(int score, int totalQuestions) {
        this.score = score;
        this.userId = GameManager.getInstance().getCurrentUser();
        this.totalQuestions = totalQuestions;
        long long_timestamp = System.currentTimeMillis(); // Record the current time
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OffsetDateTime dateTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(long_timestamp), ZoneId.systemDefault());
            timestamp = dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        else {
            timestamp = "N/A";
        }
        Log.d(TAG,"QuizResult created: "+this.serialise());
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public JsonObject serialise(){
        JsonObject object=new JsonObject();
        object.addProperty("score",score);
        object.addProperty("totalQuestions",totalQuestions);
        object.addProperty("userId",userId);
        object.addProperty("timestamp",timestamp);
        Log.i("QuizResult",object.toString());
        return object;
    }

    @Override
    public String toString() {
        return "Score: " + score + " / " + totalQuestions;
    }
}
