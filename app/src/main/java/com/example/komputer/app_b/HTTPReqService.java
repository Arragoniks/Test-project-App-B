package com.example.komputer.app_b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.format.Time;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "CanBeFinal"})
public class HTTPReqService extends AsyncTask<String, Bitmap, Void> {

    @SuppressLint("StaticFieldLeak")
    private ImageView image;
    private int aFlag;
    static final private String FOLDER_TO_SAVE = pathForSaving();
    private String url;
    private boolean openHist;
    private DBAccessHelper dbAccessHelper;
    private Context context;

    @SuppressWarnings("WeakerAccess")
    public HTTPReqService(ImageView image, int aFlag, boolean openHist, Context context){
        this.image = image;
        this.aFlag = aFlag;
        this.openHist = openHist;
        dbAccessHelper = new DBAccessHelper(context);
        this.context = context;
    }


    @Override
    protected Void doInBackground(String... strings) {
        this.url = strings[0];
        InputStream in = null;
        Bitmap bitImage = null;
        try {
            in = new java.net.URL(url).openStream();
            bitImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            if(openHist) {
                dbAccessHelper.updateStatus(url, 3);
            }else {
                dbAccessHelper.insertImageData(url, 3);
            }
            //Toast.makeText(context, "Unable to get the picture", Toast.LENGTH_LONG);
        }finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
            File file = new File(FOLDER_TO_SAVE, Integer.toString(time.year)
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
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(openHist) {
            dbAccessHelper.deleteImageData(url);
            Toast.makeText(context, "The link was deleted from your history", Toast.LENGTH_LONG).show();
        }else {
            dbAccessHelper.insertImageData(url, 1);
        }
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        super.onProgressUpdate(values);
        image.setImageBitmap(values[0]);

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("SdCardPath")
    static private String pathForSaving(){
        File file = new File("/sdcard/BIGDIG/test/B");
        file.mkdirs();
        return file.getAbsolutePath();
    }
}
