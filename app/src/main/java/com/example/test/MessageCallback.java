package com.example.test;

public interface MessageCallback {

    void onMessageResponse(boolean flag);
    void onMessageResponseError(String message);
    void onFlagSwitched();

}
