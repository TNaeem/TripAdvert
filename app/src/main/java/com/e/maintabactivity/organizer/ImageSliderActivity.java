package com.e.maintabactivity.organizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.e.maintabactivity.R;
import com.e.maintabactivity.models.ImageModel;
import com.e.maintabactivity.organizer.adapters.ImageFragmentAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class ImageSliderActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private static final String TAG = "ImageSliderActivity";
    private ImageFragmentAdapter mImageFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider2);

        List<String> images = getIntent().getStringArrayListExtra("images");

        Log.d(TAG, "onCreate: " + images);
        int currentImage = getIntent().getExtras().getInt("current");
        mViewPager = findViewById(R.id.activity_image_slider_viewPager);
        mImageFragmentAdapter = new ImageFragmentAdapter(getSupportFragmentManager(), images);

        mViewPager.setAdapter(mImageFragmentAdapter);

        mViewPager.setCurrentItem(currentImage);



    }
}
