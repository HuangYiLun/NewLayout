package com.example.venson.soho;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpTask extends AsyncTask<String, Integer, String> {
    private final static String TAG = "HttpTask";
    private String url, outStr;

    public HttpTask(String url, String outStr) {
        this.url = url;
        this.outStr = outStr;
    }

    @Override
    protected String doInBackground(String... params) {
        String remoteData = getRemoteData();
        return remoteData;
    }

//    @Override
//    protected void onPostExecute (String str) {
//        if (str == "503") {
//            Toast.makeText(context, "error occurs", Toast.LENGTH_LONG).show();
//        }
//    }


    private String getRemoteData() {
        HttpURLConnection connection = null;
        StringBuilder inStr = new StringBuilder();
        int responseCode = -1;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(1 * 500);//1s timeout
            connection.setReadTimeout(1 * 500);
            connection.setDoInput(true); // allow inputs
            connection.setDoOutput(true); // allow outputs
            // 不知道請求內容大小時可以呼叫此方法將請求內容分段傳輸，設定0代表使用預設大小
            // 參考HttpURLConnection API的Posting Content部分
            connection.setChunkedStreamingMode(0);
            connection.setUseCaches(false); // do not use a cached copy
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "UTF-8");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            bw.write(outStr);
            Log.d(TAG, "output: " + outStr);
            bw.close();
            responseCode = connection.getResponseCode();
            Log.e(TAG, "response code: " + responseCode);
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    inStr.append(line);
                }
            } else {
                Log.d(TAG, "response code: " + responseCode);
            }
        } catch (SocketTimeoutException e) {
            if (connection != null) {
                connection.disconnect();
            }
            Log.e(TAG, "response code: " + responseCode);
            return "504";
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        Log.d(TAG, "input: " + inStr);
        return inStr.toString();
    }
}