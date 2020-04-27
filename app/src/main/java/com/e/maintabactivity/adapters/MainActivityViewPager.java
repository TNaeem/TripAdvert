package com.e.maintabactivity.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.e.maintabactivity.ui.*;

public class MainActivityViewPager extends FragmentPagerAdapter {

    private final Context context;
    public MainActivityViewPager(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new HomeFragment();
        }else if(position == 1){
            return new OrganizerListFragment();
        }else if(position == 2){
            return new BookingsFragment();
        }else if(position == 3){
            return new ChatFragment();
        }else if(position == 4){
            return new ProfileFragment();
        }else if(position == 5){
            return new NotificationFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 6;
    }
}
