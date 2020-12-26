package com.example.loginapitest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;

public class CUDNetworkTask extends AsyncTask<Integer, String, Void> {


    final static String TAG = "CUDNetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;


    public CUDNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        Log.v(TAG, "Start : " + mAddr);
    }


    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Create/Update/Delete");
        progressDialog.setMessage("Working....");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground");

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(100000);
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.v(TAG,"onProgressUpdate()");

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.v(TAG,"onPostExecute()");
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }


    @Override
    protected void onCancelled() {
        Log.v(TAG, "onCancelled()");
        super.onCancelled();
    }

}//=====