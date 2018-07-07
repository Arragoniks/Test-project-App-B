package com.example.komputer.app_b;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class HTTPReqService extends AsyncTask<String, Bitmap, Void> {

    ImageView image;
    int aFlag;
    static String folderToSave = Environment.getExternalStorageDirectory().toString();
    String url;

    public HTTPReqService(ImageView image, int aFlag){
        this.image = image;
        this.aFlag = aFlag;
    }


    @Override
    protected Void doInBackground(String... strings) {
        Log.e("onPreExecute", "message");
        this.url = strings[0];
        InputStream in = null;
        Bitmap bitImage = null;
        try {
            in = new java.net.URL(url).openStream();
            bitImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.e("Publishing", "message");

        publishProgress(bitImage);
        if(aFlag == 2) {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OutputStream fOut = null;
            Time time = new Time();
            time.setToNow();
            File file = new File(folderToSave, Integer.toString(time.year)
                    + Integer.toString(time.month)
                    + Integer.toString(time.monthDay)
                    + Integer.toString(time.hour)
                    + Integer.toString(time.minute)
                    + Integer.toString(time.second) + ".jpg");
            try {
                fOut = new FileOutputStream(file);
                bitImage.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e("onPostExecute", "message");
        return null;
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        super.onProgressUpdate(values);
        image.setImageBitmap(values[0]);

    }
}
