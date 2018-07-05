package com.example.komputer.app_b;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

public class ImageHTTPDownload {

    public void downloadImg(String url, Activity activity){
        DownloadManager mgr = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setTitle("Saving")
                .setDescription("Downloading picture")
                .setDestinationInExternalPublicDir("/AnhsirkDasarp", "fileName.jpg");

        mgr.enqueue(request);
    }
}
