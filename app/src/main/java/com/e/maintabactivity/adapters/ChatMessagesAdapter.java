package com.e.maintabactivity.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.R;
import com.e.maintabactivity.models.MessageModel;
import com.e.maintabactivity.utility.UserSharedPreference;

import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.FragmentChatAdapterViewHolder>  {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private static final String TAG = "ChatMessagesAdapter";

    private List<MessageModel> mMessages;

    public void setmMessages(List<MessageModel> mMessages) {
        this.mMessages = mMessages;
        this.notifyDataSetChanged();
        Log.d(TAG, "setmMessages: " + mMessages.size());
    }

    Context context;
    public ChatMessagesAdapter(Context context, List<MessageModel> messages){
        this.context = context;
        this.mMessages = messages;
    }
    @NonNull
    @Override

    public ChatMessagesAdapter.FragmentChatAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == MSG_TYPE_RIGHT){
            View view = layoutInflater.inflate(R.layout.layout_message_right, parent, false);
            return new ChatMessagesAdapter.FragmentChatAdapterViewHolder(view);
        }else{
            View view = layoutInflater.inflate(R.layout.layout_message_left, parent, false);
            return new ChatMessagesAdapter.FragmentChatAdapterViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessagesAdapter.FragmentChatAdapterViewHolder holder, int position) {
        MessageModel msg = mMessages.get(position);
        holder.message.setText(msg.getMessage());

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
        
    }

    @Override
    public int getItemViewType(int position) {
        if(mMessages.get(position).getSenderUserId() == UserSharedPreference.getUser(context).getId()) {
            return MSG_TYPE_RIGHT;
        }
        return MSG_TYPE_LEFT;
    }

    public class FragmentChatAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        ImageView profile;
        public FragmentChatAdapterViewHolder(View itemView){

            super(itemView);

            message = itemView.findViewById(R.id.layout_message_text);
            profile = itemView.findViewById(R.id.layout_message_left_profile_image);
        }
    }
}
