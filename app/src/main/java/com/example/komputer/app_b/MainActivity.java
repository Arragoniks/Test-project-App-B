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
    int switchVar = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.image);

        //запущено самостійно
        //чи прийшли данні з інтентом

            //this.url = getIntent().getStringExtra("URL");
            //this.switchVar = getIntent().getIntExtra("SWITCHVAR", 0);

        if(url == null){
            //сповіщення про самостійне відкриття
            finishAffinity();
        }else if(switchVar == 0){
            //сповіщення про не правильні параметри при запускові
            //повернення до А
            finishAffinity();
        }
        //чи картинка
        if(isAPicture()){
            if(internetEnabled()){
                HTTPReqService httpReqService = new HTTPReqService(img, switchVar);
                httpReqService.execute(url);
            }else{
                //статус 3 для силки в БД і повідомлення про відсутність інтернету і повернення до А
            }
        }else{
            //сповіщення про не картинку
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
