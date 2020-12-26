package com.example.loginapitest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "NetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<UserInfoDto> userInfos;

    public NetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.userInfos = new ArrayList<UserInfoDto>();
    }

    //다이알로그 불러오는 작업
    @Override
    protected void onPreExecute() {
        Log.v(TAG, "progressDialogue()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("dialogue");
        progressDialog.setMessage("down....");
        progressDialog.show();
        super.onPreExecute();
    }

    // 불러오는 작업
    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackGround");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();

        try {

            URL url = new URL(mAddr);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);


            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {//여기 안들어가지네...

                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);


                while (true) {
                    String strline = bufferedReader.readLine();

                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");


                }

                parser(stringBuffer.toString());
                // Json 분리와 어레이 리스트에 넣기
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStream != null) inputStream.close();
                if (inputStreamReader != null) inputStreamReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return userInfos;
    }


    @Override
    protected void onProgressUpdate(String... values) {

        Log.v(TAG, "onProgressUpdate()");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute()");

        super.onPostExecute(o);
        progressDialog.dismiss();
    }


    @Override
    protected void onCancelled() {

        Log.v(TAG, "onCanceled()");
        super.onCancelled();
    }

    private void parser(String s) {
       Log.v(TAG, "parser");


        try {
            JSONObject jsonObject = new JSONObject(s); //JSON 파일을 s로 가져옴
            JSONArray jsonArray = new JSONArray(jsonObject.getString("userinfo"));

            userInfos.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String userinfoId = jsonObject1.getString("userinfoId");
                String userinfoPw = jsonObject1.getString("userinfoPw");
                String userinfoAddr = jsonObject1.getString("userinfoAddr");
                String userinfoTel = jsonObject1.getString("userinfoTel");


                UserInfoDto member = new UserInfoDto(userinfoId, userinfoPw, userinfoAddr, userinfoTel);

                userInfos.add(member);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}





