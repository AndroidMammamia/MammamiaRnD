package com.example.loginapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainPage extends AppCompatActivity {

    final static String TAG = "로그인 이후 페이지";
    private String userinfoId;
    private String userinfoPw;
    private String userinfoAddr;
    private String userinfoTel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Intent intent = getIntent();




    }
}