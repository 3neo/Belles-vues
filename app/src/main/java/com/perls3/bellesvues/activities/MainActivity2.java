package com.perls3.bellesvues.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.perls3.bellesvues.R;
import com.perls3.bellesvues.services.NotificationsService;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView imageView =(ImageView)findViewById(R.id.Imageresolved);
        Glide.with(this).load(NotificationsService.imagesolvedURL).into(imageView);
    }
}