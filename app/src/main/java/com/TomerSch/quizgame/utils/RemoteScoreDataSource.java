package com.TomerSch.quizgame.utils;

import android.util.Log;

import com.TomerSch.quizgame.utils.ApiService;
import com.TomerSch.quizgame.utils.QuizResult;
import com.TomerSch.quizgame.utils.ScoreRepository;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteScoreDataSource implements ScoreRepository {

    private static final String TAG = "RemoteScoreDataSource";
    private static final String BASE_URL = "http://10.0.2.2:5000/"; // Replace with your server's IP address

    private ApiService apiService;

    public RemoteScoreDataSource() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void saveScore(QuizResult result) {


        Call<ResponseBody> call = apiService.submitResult(result);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Score sent successfully: " + response.body().getMessage());
                } else {
                    Log.e(TAG, "Error sending score: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Network error sending score: " + t.getMessage());
            }
        });
    }



    @Override
    public List<QuizResult> getScores() {
        // Implement logic to retrieve scores from the server here
        Log.d(TAG, "Getting scores remotely (not implemented yet).");
        return new ArrayList<>(); // Placeholder
    }
}