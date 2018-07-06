package com.example.komputer.app_b.Photo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.komputer.app_b.R;

import java.io.InputStream;

public class PhotoActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        imageView = (ImageView)findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        final String http = extras.getString("HTTP");
        new PhotoTask(imageView,getApplicationContext()).execute(http);
    }
}


