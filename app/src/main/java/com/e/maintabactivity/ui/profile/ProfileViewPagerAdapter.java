package com.e.maintabactivity.ui.profile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProfileViewPagerAdapter extends FragmentPagerAdapter {
    Context mContext;
    public ProfileViewPagerAdapter(Context context, @NonNull FragmentManager fm) {

        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new ProfileDetailsFragment();
        }else if(position == 1){
            return new ProfileMyTripsFragment();
        }
        return new ProfileDetailsFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
