package com.example.test;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import models.FlagResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageManager {
    private static MessageManager instance;
    public static final  String URL = "http://194.5.157.136:8081";

    private MessageService messageService;
    private CompositeDisposable disposable = new CompositeDisposable();

    static MessageManager getInstance(){
        if(instance == null)
            instance = new MessageManager();
        return instance;
    }

    private MessageManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageService = retrofit.create(MessageService.class);
    }

    void getCurrentMessage(MessageCallback callback){
        disposable.add(messageService.getMessageResponse()
                .map(new Function<FlagResponse, Boolean>() {
                    @Override
                    public Boolean apply(FlagResponse messageResponse) throws Exception {
                        return messageResponse.getFlag();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onMessageResponse, error ->{
                    callback.onMessageResponseError(error.getMessage());
                }));
    }

    void switchFlag(boolean flag, MessageCallback callback){
        disposable.add(messageService.switchFlag(flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onFlagSwitched,
                        error->callback.onMessageResponseError(error.getMessage())));
    }

    void dispose(){
        disposable.clear();
    }
}
