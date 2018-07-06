package com.example.komputer.app_b.Photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.komputer.app_b.R;

import java.io.InputStream;

class PhotoTask extends AsyncTask<String,Void,Bitmap> {
    ImageView taskImage;

    Context context;
    public PhotoTask(ImageView taskImage, Context context){
        this.taskImage = taskImage;
        this.context =context;
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
            Toast toast =Toast.makeText(context, R.string.incorect_link,Toast.LENGTH_LONG);
            toast.show();
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
