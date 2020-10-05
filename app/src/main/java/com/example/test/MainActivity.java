package com.example.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MessageCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    boolean flag;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferencesManager.init(getApplicationContext());

        if (SharedPreferencesManager.contains(SharedPreferencesManager.FLAG)){
            this.flag = SharedPreferencesManager.read(SharedPreferencesManager.FLAG, false);
            chooseActivity(this.flag);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.switch_flag :
                MessageManager.getInstance().switchFlag(!this.flag,this);
                break;
            case R.id.get_flag:
                MessageManager.getInstance().getCurrentMessage(this);
                break;
            case R.id.clear_preferences:
                SharedPreferencesManager.clear();
                break;
        }
        return  false;
    }

    @Override
    public void onMessageResponse(boolean flag) {
        this.flag = flag;
        SharedPreferencesManager.write(SharedPreferencesManager.FLAG, flag);
        chooseActivity(this.flag);
     }

    @Override
    public void onMessageResponseError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFlagSwitched() {
        this.flag = !this.flag;
        SharedPreferencesManager.write(SharedPreferencesManager.FLAG, this.flag);
        Log.d(TAG, "onFlagSwitched: "  + flag);
    }

    private void chooseActivity(boolean flag){
        if(flag){
            Intent intent = new Intent(this, WebActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
    }

}