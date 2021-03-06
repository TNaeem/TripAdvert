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

import com.e.maintabactivity.R;
import com.e.maintabactivity.TripDetailsActivity;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.NotificationModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.staticModels.StaticUserModel;
import com.google.android.material.textview.MaterialTextView;

import java.net.UnknownServiceException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationTabFragmentAdapterViewHolder> {

    private List<NotificationModel> notifications;
    private Context context;

    public NotificationAdapter(Context context, List<NotificationModel> notifications){
        this.context = context ;
        this.notifications = notifications;
    }
    @NonNull
    @Override

    public NotificationTabFragmentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_notification, parent, false);
        return new NotificationTabFragmentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationTabFragmentAdapterViewHolder holder, int position) {
        NotificationModel notification = notifications.get(position);
       // holder.message.setText(notificatio);
        //PersonModel user = StaticUserModel.getUser()
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: " + "CardViewClicked");
                context.startActivity(new Intent(v.getContext(), TripDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationTabFragmentAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView personName;
        TextView message;
        ImageView personImage;
        TextView date;
        CardView cardView;
        public NotificationTabFragmentAdapterViewHolder(View itemView){

            super(itemView);
            personImage = itemView.findViewById(R.id.layout_notification_image);
            personName = itemView.findViewById(R.id.layout_notification_name);
            message = itemView.findViewById(R.id.layout_notification_message);
            date  = itemView.findViewById(R.id.layout_notification_date_time);
            cardView = itemView.findViewById(R.id.notification_card_view);
        }

    }


}

