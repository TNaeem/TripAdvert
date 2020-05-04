package com.e.maintabactivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.e.maintabactivity.adapters.ImageFragmentAdapter;


public class Slider extends AppCompatActivity {

    int position;
    ViewPager viewPager;
    ImageFragmentAdapter imageFragmentAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra("POSITION", 0);
        setContentView(R.layout.activity_image_slider);
        
        viewPager = findViewById(R.id.slider_viewPager);
        imageFragmentAdapter = new ImageFragmentAdapter(getSupportFragmentManager(), position);
        viewPager.setAdapter(imageFragmentAdapter);
    }
}
