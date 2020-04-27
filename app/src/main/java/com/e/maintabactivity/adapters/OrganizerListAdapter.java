package com.e.maintabactivity.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.organizer.OrganizerProfileActivity;
import com.e.maintabactivity.R;

public class OrganizerListAdapter extends RecyclerView.Adapter<OrganizerListAdapter.OrganizerListAdapterViewHolder> {
    private String[] organizers;
    private Context context;
    public OrganizerListAdapter(Context context, String [] trips){
        this.context = context;
        this.organizers = trips;
    }
    @NonNull
    @Override

    public OrganizerListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_organizer_card, parent, false);
        return new OrganizerListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizerListAdapter.OrganizerListAdapterViewHolder holder, int position) {
        String title = organizers[position];
        holder.name.setText(title);
        holder.ratings.setRating(3.5f);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(v.getContext(), OrganizerProfileActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return organizers.length;
    }

    public class OrganizerListAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView companyName;
        RatingBar ratings;
        ImageView image;
        CardView cardView;
        public OrganizerListAdapterViewHolder(View itemView){

            super(itemView);
            name = itemView.findViewById(R.id.layout_organizer_card_name);
            companyName = itemView.findViewById(R.id.layout_organizer_card_company_name);
            ratings = itemView.findViewById(R.id.layout_organizer_card_ratting);
            image = itemView.findViewById(R.id.layout_organizer_card_image);
            cardView = itemView.findViewById(R.id.layout_organizer_card_view);
        }
    }
}
