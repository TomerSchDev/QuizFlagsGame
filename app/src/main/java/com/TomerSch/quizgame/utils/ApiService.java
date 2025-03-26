package com.TomerSch.quizgame.utils;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/submit_result")
    Call<ResponseBody> submitResult(@Body QuizResult payload);
}