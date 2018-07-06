package com.example.komputer.app_b;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button buttonShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.links_menu_activity);
        buttonShow = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PhotoActivity.class);
                intent.putExtra("HTTP",editText.getText().toString());
                startActivity(intent);
            }
        });
    }
}