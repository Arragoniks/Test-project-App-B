package com.example.komputer.app_b.Photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komputer.app_b.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PhotoTask extends AsyncTask<String,Void,Bitmap> {
    ImageView taskImage;
    TextView textError;
    Context context;
    public PhotoTask(ImageView taskImage,TextView textError, Context context){
        this.taskImage = taskImage;
        this.context =context;
        this.textError = textError;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        String httpTask = strings[0];
        Bitmap myPhoto = null;
        try{
            InputStream in = new java.net.URL(httpTask).openStream();
            myPhoto = BitmapFactory.decodeStream(in);
        }catch (Exception e){}
        return myPhoto;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        taskImage.setImageBitmap(bitmap);
       if (!hasImage(taskImage)){
            textError.setText("Невернная ссылка!!!");
        }
        else {
           new DownloadingTask(context).execute(bitmap);
       }
    }

    private boolean hasImage(ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }
        return hasImage;
    }
}