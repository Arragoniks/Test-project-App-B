package com.example.komputer.app_b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ImageReader;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "CanBeFinal"})
public class HTTPReqService extends AsyncTask<String, Bitmap, Void> {

    @SuppressLint("StaticFieldLeak")
    private ImageView image;
    static final private String FOLDER_TO_SAVE = pathForSaving();
    private String url;
    private int status;
    private DBAccessHelper dbAccessHelper;
    private boolean isAPic = false;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    @SuppressWarnings("WeakerAccess")
    public HTTPReqService(ImageView image, int status, Context context){
        this.image = image;
        this.status = status;
        dbAccessHelper = new DBAccessHelper(context);
        this.context = context;
    }


    @Override
    protected Void doInBackground(String... strings) {
        this.url = strings[0];
        InputStream in = null;
        Bitmap bitImage = null;

            try {
                in = new URL(url).openStream();
                bitImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                /*if (openHist) {
                    dbAccessHelper.updateStatus(url, 2);
                }else {
                    dbAccessHelper.insertImageData(url, 2);
                }*/
            }
        finally {
            try {
                if(in != null)
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isAPic = bitImage != null;

        publishProgress(bitImage);
        if(status == 1 && isAPic) {
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
        if(isAPic){
            if (status == 1) {
                dbAccessHelper.deleteImageData(url);
                Toast.makeText(context, "The link was deleted from your history", Toast.LENGTH_LONG).show();
            }else if(status == 2 || status == 3){
                dbAccessHelper.updateStatus(url, 1);
            }else if(status == 4)
                dbAccessHelper.insertImageData(url, 1);
        }else {
            if(status == 1 || status == 3){
                dbAccessHelper.updateStatus(url, 2);
            }else if(status == 4){
                dbAccessHelper.insertImageData(url, 2);
            }
            Toast.makeText(context, "It is not a picture", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        super.onProgressUpdate(values);
        if(isAPic){
            image.setImageBitmap(values[0]);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("SdCardPath")
    static private String pathForSaving(){
        File file = new File("/sdcard/BIGDIG/test/B");
        file.mkdirs();
        return file.getAbsolutePath();
    }
}
