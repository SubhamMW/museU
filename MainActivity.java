package com.example.subham.museu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bn1,bn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bn1=(Button)findViewById(R.id.bn1);
        bn2=(Button)findViewById(R.id.bn2);
        bn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent list=new Intent(MainActivity.this,List.class);
                startActivity(list);
            }
        });
        bn2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent list1=new Intent(MainActivity.this,Video.class);
                startActivity(list1);
            }
        }));
    }
}

