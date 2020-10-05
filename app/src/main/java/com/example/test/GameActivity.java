package com.example.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("SetTextI18n")
public class GameActivity extends AppCompatActivity {

    static private int MONEY_COUNT = 0;
    static private int MONEY = 100;

    Button getFirstMoney;
    TextView money;
    Button updateMoney2;
    Button updateMoney1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getFirstMoney = (Button)findViewById(R.id.getFirstMoney);
        money = (TextView)findViewById(R.id.money);
        updateMoney1 =(Button) findViewById(R.id.update_money_1);
        updateMoney2 =(Button) findViewById(R.id.update_money_2);

        getMoney();
        updateMoney1();
        updateMoney2();

    }

    void getMoney(){
        getFirstMoney.setOnClickListener(view -> {
            MONEY_COUNT += MONEY;
            money.setText(MONEY_COUNT + "");
        });

    }

    void updateMoney1(){
        updateMoney1.setOnClickListener(view -> {
            if(MONEY_COUNT >= 1000){
                MONEY += 200;
                MONEY_COUNT -= 1000;
                money.setText(MONEY_COUNT + "");
            }
        });
    }

    void updateMoney2(){
        updateMoney2.setOnClickListener(view -> {
            if(MONEY_COUNT >=10000){
                MONEY +=1000;
                MONEY_COUNT -=10000;
                money.setText(MONEY_COUNT + "");
            }
        });
    }

}