package com.example.komputer.app_b;

import android.annotation.SuppressLint;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;

import java.util.Date;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
@SuppressLint("Recycle")
public class DBAccessHelper {
    private static final Uri IMAGES_CONTENT_PROVIDER_URI = Uri.parse("content://com.example.komputer.app_a.imagescontentprovier/images");

    @SuppressWarnings("CanBeFinal")
    private Context context;

    @SuppressWarnings("WeakerAccess")
    public DBAccessHelper(Context context) {
        this.context = context;
    }

    public void deleteImageData(String src) {
        ContentProviderClient contentProviderClient = context.getContentResolver().acquireContentProviderClient(IMAGES_CONTENT_PROVIDER_URI);
        try {
            contentProviderClient.delete(IMAGES_CONTENT_PROVIDER_URI, "SRC = ?", new String[]{src});
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void updateStatus(String src, int newStatus) {
        ContentProviderClient contentProviderClient = context.getContentResolver().acquireContentProviderClient(IMAGES_CONTENT_PROVIDER_URI);
        ContentValues contentValues = new ContentValues();
        contentValues.put("STATUS", newStatus);
        try {
            contentProviderClient.update(IMAGES_CONTENT_PROVIDER_URI, contentValues, "SRC = ?", new String[]{src});
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void insertImageData(String src, int status) {
        ContentProviderClient contentProviderClient = context.getContentResolver().acquireContentProviderClient(IMAGES_CONTENT_PROVIDER_URI);
        ContentValues contentValues = new ContentValues();
        contentValues.put("SRC", src);
        contentValues.put("STATUS", status);
        contentValues.put("TIME", new Date().getTime());
        try {
            contentProviderClient.insert(IMAGES_CONTENT_PROVIDER_URI, contentValues);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
