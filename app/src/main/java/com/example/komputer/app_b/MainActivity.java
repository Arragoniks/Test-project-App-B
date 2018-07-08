package com.example.komputer.app_b;


import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.komputer.app_b.Photo.DefaultFragment;
import com.example.komputer.app_b.Photo.PhotoFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    String http;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.links_menu_activity);

        Intent intentFromA = getIntent();
        String action = intentFromA.getAction();
        ft = getSupportFragmentManager().beginTransaction();
        if(action.equals("goto_App_B")){
            Log.e("LOG","-____-");
            http = intentFromA.getStringExtra("LINK");
            ft.add(R.id.layoutForFragment,PhotoFragment.setDataFragment(http));
            ft.commit();
        }else{
            Log.e("LOG","-------");
            ft.add(R.id.layoutForFragment,new DefaultFragment());
            ft.commit();
        }
    }
}