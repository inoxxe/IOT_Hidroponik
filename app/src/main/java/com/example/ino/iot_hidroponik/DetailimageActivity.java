package com.example.ino.iot_hidroponik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailimageActivity extends AppCompatActivity {

    private ImageView imageView,icon;
    private TextView textViewdate;
    private String date,url,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailimage);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        url = intent.getStringExtra("url");
        status = intent.getStringExtra("status");


        imageView = (ImageView)findViewById(R.id.detail_image);
        icon = (ImageView)findViewById(R.id.detail_warning);
        textViewdate = (TextView) findViewById(R.id.detail_date);

        if (url.equals("http://iot.dinus.ac.id/")){
            Glide.with(DetailimageActivity.this).load(R.drawable.ic_warning)
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }else {
            Glide.with(DetailimageActivity.this).load(url)
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }

        if(status.equals("SEHAT")){
            icon.setVisibility(View.INVISIBLE);
        }else {
            icon.setVisibility(View.VISIBLE);
        }

        textViewdate.setText(date);
        Toast.makeText(this,status,Toast.LENGTH_LONG).show();


    }

}
