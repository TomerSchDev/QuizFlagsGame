package com.TomerSch.quizgame.gameLogic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class EasterEggManager {

    private static final String TAG = "EasterEggManager";

    private final Map<String, EasterEggAction> easterEggs = new HashMap<>();

    // Interface for Easter Egg actions
    public interface EasterEggAction {
        void execute(AppCompatActivity context);
    }

    public EasterEggManager() {

    }

    /**
     * Adds a new Easter Egg to the manager.
     *
     * @param keyword The word that triggers the Easter Egg.
     * @param action  The action to be performed when the Easter Egg is triggered.
     */
    public void addEasterEgg(String keyword, EasterEggAction action) {
        easterEggs.put(keyword.toLowerCase(), action);
        Log.d(TAG, "Added Easter Egg for keyword: " + keyword);
    }

    /**
     * Checks if the given answer is an Easter Egg.
     *
     * @param answer The answer provided by the user.
     * @return True if the answer is an Easter Egg, false otherwise.
     */
    public boolean checkEasterEgg(String answer, AppCompatActivity context) {
        String lowerCaseAnswer = answer.toLowerCase();
        if (easterEggs.containsKey(lowerCaseAnswer)) {
            Log.d(TAG, "Easter Egg found for answer: " + answer);
            EasterEggAction e=easterEggs.get(lowerCaseAnswer);
            if (e!=null)
            {
                e.execute(context);
            }
            else
            {
                Log.e(TAG, "Easter Egg action is null for answer: " + answer);
            }
            return true;
        } else {
            Log.d(TAG, "No Easter Egg found for answer: " + answer);
            return false;
        }
    }

    /**
     * Example Easter Egg actions
     */
    // Example: Play a sound
    public static class PlaySoundAction implements EasterEggAction {
        private final int soundResId;

        public PlaySoundAction(int soundResId) {
            this.soundResId = soundResId;
        }

        @Override
        public void execute(AppCompatActivity context) {
            try {
                MediaPlayer mediaPlayer = MediaPlayer.create(context, soundResId);
                mediaPlayer.setOnCompletionListener(mp -> mp.release());
                mediaPlayer.start();
                Log.d(TAG, "Playing sound with resource ID: " + soundResId);
            } catch (Exception e) {
                Log.e(TAG, "Failed to play sound: " + e.getMessage());
            }
        }
    }

    // Example: Open a video
    public static class OpenVideoAction implements EasterEggAction {
        private final String videoUrl;

        public OpenVideoAction(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        @Override
        public void execute(AppCompatActivity context) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                context.startActivity(intent);
                Log.d(TAG, "Opening video with URL: " + videoUrl);
            } catch (Exception e) {
                Log.e(TAG, "Failed to open video: " + e.getMessage());
            }
        }
    }
    public static class ToastAction implements EasterEggAction {
        private final String message;

        public ToastAction(String message) {
            this.message = message;
        }

        @Override
        public void execute(AppCompatActivity context) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Showing toast message: " + message);
        }
    }
    public static class ActivityAction implements EasterEggAction
    {
        private final Class activity;

        public ActivityAction(Class activity) {
            this.activity = activity;
        }

        @Override
        public void execute(AppCompatActivity context) {

            context.startActivity(new Intent(context, activity));
            context.finish();
        }


    }
}