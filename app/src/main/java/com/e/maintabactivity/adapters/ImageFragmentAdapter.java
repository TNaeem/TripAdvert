package com.e.tripadvet.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.e.maintabactivity.R;
import com.e.tripadvet.ui.ImageFragment;


public class ImageFragmentAdapter extends FragmentStatePagerAdapter {
    @NonNull


    private int pos;
    int[] images = {R.drawable.person_image, R.drawable.logo,R.drawable.icon_profile};

    public ImageFragmentAdapter(@NonNull FragmentManager fm, int position) {
        super(fm);
        this.pos = position;
    }
    @Override
    public Fragment getItem(int position) {
        position = pos;
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Source", images[position]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return images.length;
    }
}
