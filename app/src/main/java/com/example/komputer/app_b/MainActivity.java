package com.example.komputer.app_b;

<<<<<<< Updated upstream
=======
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
>>>>>>> Stashed changes
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //String url = "http://www.radionetplus.ru/uploads/posts/2013-05/1369460621_panda-26.jpg";
    //ImageView imgv;

    ImageView img;
    String url = "http://www.radionetplus.ru/uploads/posts/2013-05/1369460621_panda-26.jpg";
    int switchVar = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
        //imgv = findViewById(R.id.image);
        //ImageHTTPRequest imgh = new ImageHTTPRequest(imgv);
        //imgh.execute(url);
        //ImageHTTPDownload imgs = new ImageHTTPDownload();
        //imgs.downloadImg(url, this);

=======

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

        //internet
        if(!internetEnabled()){
//статус 3 для силки в БД і повідомлення про відсутність інтернету і повернення до А
        }

        //чи наявна sd і запис з читанням на неї + дістати шлях для збереження файлів
        if(false){

        }
        HTTPReqService httpReqService = new HTTPReqService(img, switchVar);
        httpReqService.execute(url);
    }

    private boolean internetEnabled(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
>>>>>>> Stashed changes
    }
}
