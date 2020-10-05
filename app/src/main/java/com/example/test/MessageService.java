package com.example.test;


import io.reactivex.Completable;
import io.reactivex.Observable;
import models.FlagResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessageService {
    @GET("/")
    Observable<FlagResponse> getMessageResponse();

    @GET("/setting?")
    Completable switchFlag(@Query("flag") boolean flag);
}
