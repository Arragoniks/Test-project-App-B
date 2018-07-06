package com.example.komputer.app_b.Photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DownloadingTask extends AsyncTask<Bitmap,Void,Void> {
    Context mContext;
    public DownloadingTask(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Bitmap... bitmaps) {
        //задержка 15 сек
        try {
            Log.e("CHEKING TIME: ","Start "+(new GregorianCalendar().get(Calendar.SECOND)));
            Thread.sleep(15000);
        } catch (InterruptedException e) {}

        String folderToSafe = Environment.getExternalStorageDirectory().toString()+"/BIGDIG/test/B";
        SavePicture(bitmaps[0],folderToSafe);
        Log.e("CHEKING TIME: ","Finish "+(new GregorianCalendar().get(Calendar.SECOND)));
        return null;
    }

    private void SavePicture(Bitmap bitmap, String folderToSave)
    {
        String TAG_TASK="Save Picture: ";
        FileOutputStream fOut = null;
        GregorianCalendar gs = new GregorianCalendar();
        try {
            File file = new File(folderToSave,""+gs.get(Calendar.YEAR)+"."+(gs.get(Calendar.MONTH)+1)+"."+
                    gs.get(Calendar.DATE)+"."+gs.get(Calendar.HOUR)+"."+gs.get(Calendar.MINUTE)+"."+gs.get(Calendar.SECOND)+".jpg");
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
           // MediaStore.Images.Media.insertImage(mContext.getContentResolver(), file.getAbsolutePath(), file.getName(),  file.getName()); // регистрация в фотоальбоме
        }
        catch (Exception e) { Log.e(TAG_TASK,e.toString());}
    }
}
