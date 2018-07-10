package com.example.komputer.app_b;

import android.Manifest;
import android.annotation.SuppressLint;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_GET = 1;
    private static final String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE};

    private ImageView img;
    final static private String [] EXTENSIONS = {".png", ".jpg"};
    private String url;
    private int status;

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
        this.status = getIntent().getIntExtra("STATUS", 0);
        DBAccessHelper dbAccessHelper = new DBAccessHelper(this);


        if(url == null){
            img.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.VISIBLE);
            textView3.setText(R.string.string_for_independent_launch);
            CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long l) {
                    textView4.setText((int)(l/1000) + "sec");
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onFinish() {
                    textView4.setText("0sec");
                    try {
                        finishAffinity();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            countDownTimer.start();
        }else{
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            if(internetEnabled()){
                HTTPReqService httpReqService = new HTTPReqService(img, status, this);
                httpReqService.execute(url);
            }else {
                if (status == 4)
                    dbAccessHelper.insertImageData(url, 3);
                else
                    dbAccessHelper.updateStatus(url, 3);
                Toast.makeText(this, "Internet is disabled", Toast.LENGTH_LONG).show();
            }
        }


    }

    @SuppressWarnings("ConstantConditions")
    private boolean internetEnabled(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
        }
    }
}
