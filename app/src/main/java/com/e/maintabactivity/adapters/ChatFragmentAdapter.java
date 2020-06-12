package com.e.maintabactivity.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.ChatActivity;
import com.e.maintabactivity.R;
import com.e.maintabactivity.models.ContactModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.staticModels.StaticOrganizerModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatFragmentAdapter extends RecyclerView.Adapter<ChatFragmentAdapter.ChatFragmentAdapterViewHolder> {

    private static final String TAG = "ChatFragmentAdapter";

    private List<ContactModel> mContacts;
    private Context mContext;


    public ChatFragmentAdapter(Context context, List<ContactModel> mContacts){
        this.mContext = context ;
        this.mContacts = mContacts;
    }

    public void setContacts(List<ContactModel> mContacts){
        this.mContacts = mContacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ChatFragmentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_chat_contact, parent, false);
        return new ChatFragmentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatFragmentAdapterViewHolder holder, int position) {



        final ContactModel contact = mContacts.get(position);

        PersonModel organizer = StaticOrganizerModel.getOrganizer(contact.getId());
        holder.message.setText(contact.getMessage().getMessage());
        holder.personName.setText(contact.getName());
        holder.date.setText(contact.getMessage().getDate());
        if(organizer != null){
            Picasso.get().load(organizer.getImage()).into(holder.personImage);
        }else{
            Picasso.get().load(R.drawable.icon_sample_profile).into(holder.personImage);

        }

        Log.d(TAG, "onBindViewHolder: " + holder.cardView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " +" Chat ");
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("userId",contact.getId());
                Log.d(TAG, "onClick: " + contact.getId());
                intent.putExtra("userName",contact.getName());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ChatFragmentAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView personName;
        TextView message;
        ImageView personImage;
        TextView date;
        CardView cardView;

        public ChatFragmentAdapterViewHolder(View itemView){

            super(itemView);
            personImage = itemView.findViewById(R.id.layout_chat_contact_image);
            personName = itemView.findViewById(R.id.layout_chat_contact_name);
            message = itemView.findViewById(R.id.layout_chat_contact_message);
            date  = itemView.findViewById(R.id.layout_chat_contact_date_time);
            cardView = itemView.findViewById(R.id.card);
        }


    }

}
