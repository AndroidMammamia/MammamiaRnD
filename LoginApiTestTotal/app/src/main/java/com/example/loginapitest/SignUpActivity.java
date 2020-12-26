package com.example.loginapitest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    final static String TAG = "회원가입";
    EditText userinfoId, userinfoPw, userinfoPwCheck,  userinfoAddrDetail, userinfoTel;

    TextView pwCheckAlert, userinfoAddr;
    ArrayList<UserInfoDto> userInfoDtos;
    private String urlAddr;
    private String macIp;
    private String userIdCheck;
    private int totalCheck = 0;
    CheckBox agreementCheckBox;

    Button buttonIdCheck, buttonSignUp;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //button
        buttonSignUp = findViewById(R.id.signUpButton_signup);
        buttonIdCheck = findViewById(R.id.btnIdCheck_signup);
        //textview
        pwCheckAlert = findViewById(R.id.pwCheckAlert);
        //editText
        userinfoAddr = findViewById(R.id.et_address);
        userinfoAddrDetail = findViewById(R.id.et_address_detail);
        userinfoTel = findViewById(R.id.textTelNumber);
        userinfoId = findViewById(R.id.textId);
        userinfoPw = findViewById(R.id.textPassword);
        userinfoPwCheck = findViewById(R.id.textPasswordCheck);
        //checkbox
        agreementCheckBox = findViewById(R.id.Agreement);


//---------------------------------------------------주소API--------------------------------------------//
        userinfoAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, AddressWebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        userinfoAddr.setText(data);
                    }
                }
                break;
        }


//------------------------------------------------약관동의--------------------------------------------//

        //buttonListener
        buttonSignUp.setOnClickListener(mOnclickListener);
        buttonIdCheck.setOnClickListener(mOnclickListener);


        buttonSignUp.setEnabled(false); //회원가입 버튼 비활성화.
        //체크박스 눌렀을때 활성화
        agreementCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (agreementCheckBox.isChecked()) {
                    buttonSignUp.setEnabled(true);
                }
            }
        });


//---------------------------------------------------암호체크용--------------------------------------------//
        userinfoPwCheck.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strUserinfoPw = userinfoPw.getText().toString();
                Log.d("하하", s + "," + count + "," + strUserinfoPw);
                if (s != null) {
                    if (s.toString().equals(strUserinfoPw)) {//암호 확인 체크 하기

                        pwCheckAlert.setText("암호가 일치합니다.");
                    } else {

                        pwCheckAlert.setText("암호가 일치하지 않습니다.");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//---------------------------------------------------암호체크용--------------------------------------------//
    }//onCreate

    //--------------------------------------------------버튼 클릭 리스너 ---------------------------------------//
    View.OnClickListener mOnclickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //--------------------------------------------------회원가입 버튼 눌렀을때 ---------------------------------------//
                case R.id.signUpButton_signup:
                    String strTel = userinfoTel.getText().toString().trim();
                    String strAddr = userinfoAddr.getText().toString().trim() + userinfoAddrDetail.getText().toString().trim();
                    String strPw = userinfoPw.getText().toString().trim();
                    String strPwCheck = userinfoPwCheck.getText().toString().trim();
                    if (strTel.length() != 0 && strAddr.length() != 0 && strPw.equals(strPwCheck)) {

                        Log.d("입력체크", "토탈체크 " + totalCheck);
                        buttonEnable();
                    }  else if (strAddr.length() == 0) {

                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("주소를 입력하세요")
                                .setMessage("주소를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        userinfoAddr.setFocusable(true);

                    } else if (userinfoAddrDetail.getText().toString().trim().length() == 0) {

                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("상세주소를 입력하세요")
                                .setMessage("상세주소를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        userinfoAddrDetail.setFocusable(true);

                    } else if (strTel.length() == 0) {

                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("전화번호를 입력하세요")
                                .setMessage("전화번호를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        userinfoTel.setFocusable(true);
                    }else if (strPw != strPwCheck) {
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("같은 암호를 입력하세요")
                                .setMessage("같은 암호를 입력하세요")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        userinfoPw.setFocusable(true);
                    } else if (strPw.length() == 0) {
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("암호를 입력해주세요")
                                .setMessage("암호를 입력해주세요")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        userinfoPw.setFocusable(true);

                    } else if (strPwCheck.length() == 0) {
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("암호 확인을 채워주세요")
                                .setMessage("암호요 확인을 채워주세요")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        userinfoPwCheck.setFocusable(true);

                    } else {

                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("전화번호와 주소를 입력하세요")
                                .setMessage("전화번호와 주소를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        userinfoTel.setFocusable(true);

                    }
                    break;
                //--------------------------------------------------회원가입 버튼 눌렀을때 끝 ---------------------------------------//


                //--------------------------------------------------아이디 중복 버튼 눌렀을때 ---------------------------------------//

                case R.id.btnIdCheck_signup://ID Check

                    String strId = userinfoId.getText().toString().trim();
                    Log.d(TAG, "strid : " + strId);
                    if (strId.length() != 0) {
                        connectIdCheck();//idCheck 메소드 발동.

                    } else {
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("이메일을 입력하세요!")

                                .setMessage("이메일을 입력하세요!.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                    }
                    break;

            }


        }
    };
    //--------------------------------------------------아이디 중복 버튼 눌렀을때 ---------------------------------------//



   /*
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
    */

    //-------------------------------아이디 체크 메소드 ----------------------------------------------//


    private void connectIdCheck() {
        Log.v(TAG, "connectGetData()");
        try {
            String strId = userinfoId.getText().toString();
            macIp = "192.168.35.147";
            urlAddr = "http:/" + macIp + ":8080/test/MammamiaUserinfoSelect.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것

            urlAddr = urlAddr + "userinfoId=" + strId;//jsp에 ID값 Request할 수 있게 페이지 설정.
            Log.v(TAG, urlAddr);
            NetworkTask networkTask = new NetworkTask(SignUpActivity.this, urlAddr);
            Object obj = networkTask.execute().get(); //obj를 받아들여서
            userInfoDtos = (ArrayList<UserInfoDto>) obj; //userInfoDtos 다시 풀기


            if (userInfoDtos.isEmpty()) {//아이디가 없는값이라면 없는 아이디라 메세지 띄우고 (없는 값이기 때문에 불러와지지 않으므로
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("이메일 중복확인 결과!")
                        .setMessage("사용가능한 이메일 입니다.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                totalCheck = 1;
            } else {
                userIdCheck = userInfoDtos.get(0).getUserinfoId();//dto에서 0번째로 낚아 채기 (어짜피 한개 밖에 없음.
                Log.d(TAG, userIdCheck);
//            Log.d(TAG,userPwCheck);
                totalCheck = 0;
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("이메일 중복확인 결과!")
                        .setMessage("다른 이메일을 입력하세요")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoId.setFocusable(true);
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

//-----------------------------------아이디 체크끝 ---------------------------------------------------------//

    //버튼 먹히
    private void buttonEnable() {
        if (totalCheck == 1) {
            String strId = userinfoId.getText().toString().trim();
            String strPw = userinfoPw.getText().toString().trim();
            String strTel = userinfoTel.getText().toString().trim();
            String strAddr = userinfoAddr.getText().toString().trim() + " " + userinfoAddrDetail.getText().toString().trim();

            macIp = "192.168.35.147";

            urlAddr = "http:/" + macIp + ":8080/test/MammamiaUserinfoInsert.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것

            urlAddr = urlAddr + "userinfoId=" + strId + "&userinfoPw=" + strPw + "&userinfoTel=" + strTel + "&userinfoAddr=" + strAddr;


            try {
                CUDNetworkTask insertworkTask = new CUDNetworkTask(SignUpActivity.this, urlAddr);
                insertworkTask.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("가입완료!")
                    .setMessage("로그인 페이지로 넘어갑니다!")
                    .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                    .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.putExtra("USERID", strId);
                            startActivity(intent);

                        }
                    })
                    .show();


        } else {
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("이메일 중복확인!")
                    .setMessage("이메일 중복확인을 해주세요")
                    .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                    .setPositiveButton("닫기", null)
                    .show();

        }


    }


}