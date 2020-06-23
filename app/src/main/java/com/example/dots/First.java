package com.example.dots;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.IntegerRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class First extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dfirst_screen);
//        ImageView i=findViewById(R.id.im);
//        i.setClipToOutline(true);
        CardView User=findViewById(R.id.usercard);
        CardView Admin=findViewById(R.id.admincard);
        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(First.this,Scroll.class);
                startActivity(intent);
            }
        });
        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(First.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
