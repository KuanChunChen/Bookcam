package com.lhh.apst.Camera;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class AsyncTaskForPostFile extends AsyncTask<String, Void, Void> {
    private String TAG = "AsyncTaskForPostFile";
    private Context mContext;
    private final String sp = "7dd19a1a5a0d2c";
    private final String end = "\r\n";
    private final String twoHyphens = "--";
    private final String boundary = "---------------------------";
    private final String post_url = "http://140.115.80.61/postfile.php";
    private final String input_name = "myfile";

    public AsyncTaskForPostFile(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(String... params) {

        int countsize = params.length;
        for (int i = 0; i < countsize; i++){
            String file_path = params[i];

            File MediaFile = new File(file_path);
            // File MediaFile2 = new File(file_path1);
            if (!MediaFile.exists()) {
                return null;
            }

            try {
                URL url = new URL(post_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setChunkedStreamingMode(0);    //針對大檔傳輸的設定
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary + sp);

                int bufferSize = 1024*1024*60;
                FileInputStream fileInputStream = new FileInputStream(file_path);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(boundary + twoHyphens + sp + end);

                //
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + input_name + "\"; filename=\"" + MediaFile.getName() + "\"" + end);
                //  dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + input_name + "\"; filename=\"" + MediaFile2.getName() + "\"" + end);
                //傳送
                //
                dataOutputStream.writeBytes(end);
                byte[] buffer = new byte[bufferSize];
                int count = 0;
                while ((count = bufferedInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, count);
                    dataOutputStream.writeBytes(end);
                }
                bufferedInputStream.close();
                fileInputStream.close();
                dataOutputStream.write((boundary + twoHyphens + sp + twoHyphens + end).getBytes());
                dataOutputStream.flush();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                dataOutputStream.close();

            } catch (Exception ex) {
                Log.d(TAG, ex.getMessage());
            }//++
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Intent intent = new Intent("上傳已完成");
        this.mContext.sendBroadcast(intent);
        super.onPostExecute(result);
    }
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();

        // 背景工作被"取消"時作的事，此時不作 onPostExecute(String result)
    }
}