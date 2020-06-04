package com.e.maintabactivity.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.e.maintabactivity.ui.*;

public class MainActivityViewPager extends FragmentPagerAdapter {

    private final Context context;
    HomeFragment homeFragment = new HomeFragment();
    OrganizerListFragment organizerListFragment = new OrganizerListFragment();
    BookingsFragment bookingsFragment = new BookingsFragment();
    ChatFragment chatFragment = new ChatFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    public MainActivityViewPager(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return homeFragment;
        }else if(position == 1){
            return organizerListFragment;
        }else if(position == 2){
            return bookingsFragment;
        }else if(position == 3){
            return chatFragment;
        }else if(position == 4){
            return profileFragment;
        }else if(position == 5){
            return notificationFragment;
        }
        return homeFragment;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
