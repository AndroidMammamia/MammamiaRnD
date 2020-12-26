package com.example.loginapitest;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this,"b8f1e7f5e6bb8f2b5ba642c75ff2d201"); //네이티브 앱키를 넣을것

    }
}
