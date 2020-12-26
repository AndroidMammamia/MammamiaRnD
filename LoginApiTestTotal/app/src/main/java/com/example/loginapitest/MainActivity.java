package com.example.loginapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    private View loginButton, kakaoLoginButton, signUpButton, findButton;
    private EditText userId, userPw;
    private String urlAddr = null;
    ArrayList<UserInfoDto> userInfoDtos;
    UserAdapter adapter;
    String macIp;
    String userinfoId, userinfoPw;
    String userIdCheck, userPwCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        kakaoLoginButton = findViewById(R.id.kakaologin);
        loginButton = findViewById(R.id.login);
        findButton =findViewById(R.id.btn_find);
        signUpButton = findViewById(R.id.btnsignUp);

        userId = findViewById(R.id.userid);
        userPw = findViewById(R.id.userpw);


        Intent intent = getIntent();
        userId.setText(intent.getStringExtra("USERID"));



        signUpButton.setOnClickListener(mOnclickListener);
        findButton.setOnClickListener(mOnclickListener);
        kakaoLoginButton.setOnClickListener(mOnclickListener);
        loginButton.setOnClickListener(mOnclickListener);


    }//oncreate


    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.kakaologin:
                    if (LoginClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)) { //기기를 통한 카카오톡 로그인 가능한지
                        LoginClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
                    } else {
                        LoginClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);//아니면 카카오톡 온라인

                    }
                    updateKakaoLoginUi();
                    break;

                case R.id.login:
                    userinfoId = userId.getText().toString().trim();
                    userinfoPw = userPw.getText().toString().trim();
                    if (userinfoId.length()!=0 && userinfoPw.length() !=0) {//빈칸이 아닐경우
                        connectloginCheck();//로그인 메소드 발동
                    } else {//빈칸이 있을경우
                        Toast.makeText(MainActivity.this, "빈칸을 채워주셈요 ", Toast.LENGTH_SHORT).show();

                    }
                    break;

                case R.id.btnsignUp://가입하기로 넘어가기
                    Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_find://아이디 비번 찾기로 넘어가기
                    Intent intent1 = new Intent(MainActivity.this,FindIdPwActivity.class);
                    startActivity(intent1);
                    break;
            }

        }
    };


    //-------------------------------일반 로그인 ----------------------------------------------//


    private void connectloginCheck() {
        Log.v(TAG, "connectGetData()");
        try {

            macIp = "192.168.35.147";
            urlAddr = "http:/" + macIp + ":8080/test/MammamiaUserinfoSelect.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
            urlAddr = urlAddr + "userinfoId=" + userinfoId;//jsp에 ID값 Request할 수 있게 페이지 설정.
            Log.v(TAG,urlAddr);
            NetworkTask networkTask = new NetworkTask(MainActivity.this, urlAddr);
            Object obj = networkTask.execute().get(); //obj를 받아들여서
            userInfoDtos = (ArrayList<UserInfoDto>) obj; //userInfoDtos 다시 풀기

            userIdCheck = userInfoDtos.get(0).getUserinfoId();//dto에서 0번째로 낚아 채기 (어짜피 한개 밖에 없음.
            userPwCheck = userInfoDtos.get(0).getUserinfoPw();
//            Log.d(TAG,userIdCheck);
//            Log.d(TAG,userPwCheck);

            if (userPwCheck.length()!=0) {//받아오는 암호값이 있으면 고고
                if (userPwCheck.equals(userinfoPw)) {//암호가 같으면
                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    intent.putExtra("USERID", userinfoId);
                    Toast.makeText(this, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {// 암호가 다르면 암호가 틀렸다고 메세지 띄우기
                    Toast.makeText(MainActivity.this, "암호가 틀립니다.", Toast.LENGTH_SHORT).show();
                }


            } else {//아이디가 없는값이라면 없는 아이디라 메세지 띄우고
                Toast.makeText(this, "없는 아이디입니다.", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//-----------------------------------일반 로그인 끝 ---------------------------------------------------------//


//-----------------------------------카카오 로그인 ---------------------------------------------------------//

    Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            if (oAuthToken != null) {
                //TBD
            }
            if (throwable != null) {
                //TBD
            }
            updateKakaoLoginUi();
            return null;
        }
    };


    private void updateKakaoLoginUi() {//로그인 유무 확인
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {//로그인 상황일때
                    Log.d(TAG, "invoke: email = " + user.getKakaoAccount().getEmail());

//                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());//닉네임 가져와서 올리기
//                    Glide.with(profileImage).load(user.getKakaoAccount().getProfile().getProfileImageUrl()).circleCrop().into(profileImage);
                    //카카오 프로필 사진 가져오기
                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    intent.putExtra("userId", user.getKakaoAccount().getEmail());//유저 이메일 주소 넘기기


                } else {//로그아웃 상황일때.


                }


                return null;
            }
        });
    }

    //-----------------------------------카카오 로그인   ---------------------------------------------------------//
}