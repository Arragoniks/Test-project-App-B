package com.example.komputer.app_b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    String [] extensions = {".png", ".jpg"};
    //String url = "http://fans-android.com/wp-content/uploads/2016/04/android-1.jpg";
    String url;
    int mode;
    boolean openHist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.image);

        this.url = getIntent().getStringExtra("URL");
        this.mode = getIntent().getIntExtra("MODE", 0);
        this.openHist = getIntent().getBooleanExtra("OPENHIST", false);
        DBAccessHelper dbAccessHelper = new DBAccessHelper(this);

        if(url != null && isAPicture() && internetEnabled()){
                HTTPReqService httpReqService = new HTTPReqService(img, mode, openHist, this);
                httpReqService.execute(url);
        }else if(url == null){
            android.widget.Toast.makeText(this, "This application isn't independent", Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finishAffinity();
        }else if(isAPicture()){
            if(openHist)
                dbAccessHelper.updateStatus(url, 2);
            else
                dbAccessHelper.insertImageData(url, 2);
            Toast.makeText(this, "It is not a picture", Toast.LENGTH_LONG);
            finishAffinity();
        }else{
            if(openHist)
                dbAccessHelper.updateStatus(url, 3);
            else
                dbAccessHelper.insertImageData(url, 3);
            Toast.makeText(this, "Internet is disabled", Toast.LENGTH_LONG);
            finishAffinity();
        }


    }

    private boolean internetEnabled(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    private boolean isAPicture(){
        for(String extension : extensions){
            if(url.endsWith(extension)){
                return true;
            }
        }
        return false;
    }
}
