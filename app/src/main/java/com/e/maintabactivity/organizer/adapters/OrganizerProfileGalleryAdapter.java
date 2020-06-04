package com.e.maintabactivity.organizer.adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class OrganizerProfileGalleryAdapter extends BaseAdapter {

    List<String> mImagesIds;
    Context mContext;

    public OrganizerProfileGalleryAdapter(List<String> ids, Context context){
        mImagesIds = ids;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImagesIds.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = (ImageView) convertView;

        if(imageView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(350, 450));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        Picasso.get().load(mImagesIds.get(position)).into(imageView);

        return imageView;
    }
}

