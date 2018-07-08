package com.example.komputer.app_b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    String [] extensions = {".png", ".jpg"};
    String url = "http://fans-android.com/wp-content/uploads/2016/04/android-1.jpg";
    //String url = "";
    int mode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.image);

        //this.url = getIntent().getStringExtra("URL");
        //this.mode = getIntent().getIntExtra("MODE", 0);

        if(url != null && isAPicture() && internetEnabled()){
                HTTPReqService httpReqService = new HTTPReqService(img, mode);
                httpReqService.execute(url);
        }else if(url == null){
            //сповіщення про самостійне відкриття
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finishAffinity();
        }else if(isAPicture()){
            //сповіщення про не картинку
        }else{
            //статус 3 для силки в БД і повідомлення про відсутність інтернету і повернення до А
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
