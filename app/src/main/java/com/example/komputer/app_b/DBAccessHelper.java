package com.example.komputer.app_b;

import android.annotation.SuppressLint;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;

import java.util.GregorianCalendar;
import java.util.TimeZone;

@SuppressLint("Recycle")
public class DBAccessHelper {
    private static final Uri IMAGES_CONTENT_PROVIDER_URI = Uri.parse("content://com.example.komputer.app_a/images");

    private Context context;

    public DBAccessHelper(Context context) {
        this.context = context;
    }

    public void deleteImageData(String src) {
        try(ContentProviderClient contentProviderClient = context.getContentResolver().acquireContentProviderClient(IMAGES_CONTENT_PROVIDER_URI)) {
            try {
                contentProviderClient.delete(IMAGES_CONTENT_PROVIDER_URI, "SRC = ?", new String[]{src});
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateStatus(String src, int newStatus) {
        try(ContentProviderClient contentProviderClient = context.getContentResolver().acquireContentProviderClient(IMAGES_CONTENT_PROVIDER_URI)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("STATUS", newStatus);
            try {
                contentProviderClient.update(IMAGES_CONTENT_PROVIDER_URI, contentValues, "SRC = ?", new String[]{src});
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertImageData(String src, int status) {
        try(ContentProviderClient contentProviderClient = context.getContentResolver().acquireContentProviderClient(IMAGES_CONTENT_PROVIDER_URI)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("SRC", src);
            contentValues.put("STATUS", status);
            contentValues.put("TIME", new GregorianCalendar(TimeZone.getDefault()).getTimeInMillis());
            try {
                contentProviderClient.insert(IMAGES_CONTENT_PROVIDER_URI, contentValues);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
