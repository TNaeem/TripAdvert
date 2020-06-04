package com.e.maintabactivity.organizer.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.e.maintabactivity.organizer.ImageFragment;

import java.util.List;

public class ImageFragmentAdapter extends FragmentStatePagerAdapter {

    List<String> mImages;

    public ImageFragmentAdapter(@NonNull FragmentManager fm, List<String> images) {
        super(fm);
        mImages = images;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("image", mImages.get(position));
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }
}
