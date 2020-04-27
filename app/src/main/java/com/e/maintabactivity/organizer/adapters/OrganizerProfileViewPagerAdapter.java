package com.e.maintabactivity.organizer.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.e.tripadvet.Organizer.fragments.OrganizerProfileAboutFragment;
import com.e.tripadvet.Organizer.fragments.OrganizerProfileGalleryFragment;
import com.e.maintabactivity.organizer.fragments.OrganizerProfileTripsFragment;

public class OrganizerProfileViewPagerAdapter extends FragmentPagerAdapter {

    private final Context context;
    public OrganizerProfileViewPagerAdapter(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        this.context = context;
    }
    @NonNull
    @Override
    public Fragment getItem(int position){

        if(position == 0){
            return new OrganizerProfileAboutFragment();
        }else if(position == 1){
            return new OrganizerProfileTripsFragment();
        }else if(position == 2){
            return new OrganizerProfileGalleryFragment();
        }
        return new OrganizerProfileAboutFragment();

    }

    @Override
    public int getCount() {
        return 3;
    }
}
