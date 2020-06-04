package com.e.maintabactivity.organizer.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.e.maintabactivity.models.OrganizerModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.organizer.fragments.OrganizerProfileAboutFragment;
import com.e.maintabactivity.organizer.fragments.OrganizerProfileGalleryFragment;
import com.e.maintabactivity.organizer.fragments.OrganizerProfileTripsFragment;
import com.google.gson.Gson;

public class OrganizerProfileViewPagerAdapter extends FragmentPagerAdapter {

    private final Context context;
    private PersonModel mPersonModel;
    public OrganizerProfileViewPagerAdapter(Context context, FragmentManager fragmentManager, PersonModel personModel){

        super(fragmentManager);
        this.context = context;
        mPersonModel = personModel;
    }
    @NonNull
    @Override
    public Fragment getItem(int position){


        if(position == 0){
            return new OrganizerProfileAboutFragment(mPersonModel);
        }else if(position == 1){
            return new OrganizerProfileTripsFragment(mPersonModel.getOrganizer().getId());
        }else if(position == 2){
            return new OrganizerProfileGalleryFragment(mPersonModel.getId());
        }
        return new OrganizerProfileAboutFragment(mPersonModel);

    }

    @Override
    public int getCount() {
        return 3;
    }
}
