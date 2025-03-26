package com.TomerSch.quizgame;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private QuizManager quizManager;
    private EasterEggManager easterEggManager;
    private Map<String,String> users;
    private static final String TAG = "GameManager";
    private static GameManager instance;
    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    private GameManager()
    {
        quizManager = new QuizManager();
        easterEggManager = new EasterEggManager();
        users=new HashMap<>();
        users.put("admin","admin");


        quizManager.addQuestion(new Question(R.drawable.flag_france, "france"));
        quizManager.addQuestion(new Question(R.drawable.flag_germany, "germany"));
        quizManager.addQuestion(new Question(R.drawable.flag_italy, "italy"));
        quizManager.addQuestion(new Question(R.drawable.flag_japan, "japan"));
        quizManager.addQuestion(new Question(R.drawable.flag_russia, "russia"));
        quizManager.addQuestion(new Question(R.drawable.flag_spain, "spain"));
        quizManager.addQuestion(new Question(R.drawable.flag_uk, "uk"));
        quizManager.addQuestion(new Question(R.drawable.flag_usa, "usa"));
        // Add Easter Eggs
        easterEggManager.addEasterEgg("cheat", new EasterEggManager.ToastAction("Cheater!"));
        easterEggManager.addEasterEgg("surprise", new EasterEggManager.PlaySoundAction(R.raw.surprise));
        easterEggManager.addEasterEgg("admin", new EasterEggManager.ActivityAction(AdminLoginActivity.class));
        quizManager.shuffleQuestions();


    }

    public boolean checkAnswer(String userAnswer, Context context) {
        String user_clean_answer = userAnswer.trim().toLowerCase();
        if (easterEggManager.checkEasterEgg(user_clean_answer, context)) {
            return false;
        }
        return quizManager.checkAnswer(user_clean_answer);
    }

    public void nextQuestion() {
        quizManager.nextQuestion();
    }

    public boolean isQuizFinished() {
        return quizManager.isQuizFinished();
    }

    public int getScore() {
        return quizManager.getScore();
    }

    public int getTotalQuestions() {
        return quizManager.getTotalQuestions();
    }

    public Question getCurrentQuestion() {
        return quizManager.getCurrentQuestion();
    }

    public Map<String, String> getAdminCredentials() {
        return users;
    }
}