package com.example.komputer.app_b;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_GET = 1;
    private static final String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE};

    ImageView img;
    String [] extensions = {".png", ".jpg"};
    String url;
    int mode;
    boolean openHist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        img = findViewById(R.id.image);
        TextView textView3 = findViewById(R.id.textView3);
        final TextView textView4 = findViewById(R.id.textView4);
        textView3.setVisibility(View.INVISIBLE);
        textView4.setVisibility(View.INVISIBLE);

        this.url = getIntent().getStringExtra("URL");
        this.mode = getIntent().getIntExtra("MODE", 0);
        this.openHist = getIntent().getBooleanExtra("OPENHIST", false);
        DBAccessHelper dbAccessHelper = new DBAccessHelper(this);
        boolean isaPic = isAPicture();
        boolean inetEnable = internetEnabled();

        if(url != null && isaPic && inetEnable){
                HTTPReqService httpReqService = new HTTPReqService(img, mode, openHist, this);
                httpReqService.execute(url);
        }else if(url == null){
            img.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.VISIBLE);
            textView3.setText("App B is not independent and will close in");
            CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
                @Override
                public void onTick(long l) {
                    textView4.setText((int)(l/1000) + "sec");
                }

                @Override
                public void onFinish() {
                    finishAffinity();
                }
            };
            countDownTimer.start();
        }else if(isaPic){
            if(openHist)
                dbAccessHelper.updateStatus(url, 2);
            else
                dbAccessHelper.insertImageData(url, 2);
            Toast.makeText(this, "It is not a picture", Toast.LENGTH_LONG);
            finishAffinity();
        }else if(inetEnable){
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
    private void checkPermissions() {
        List<String> permissions = new ArrayList<>();
        for(String permission : PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissions.add(permission);
            }
        }
        if(!permissions.isEmpty()){
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), PERMISSIONS_GET);
            return;
        }
        return;
    }
}
