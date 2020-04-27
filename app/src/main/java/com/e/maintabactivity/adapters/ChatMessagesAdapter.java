package com.e.tripadvet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.R;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.FragmentChatAdapterViewHolder>  {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private String[] messages;
    Context context;
    public ChatMessagesAdapter(Context context, String [] messages){
        this.context = context;
        this.messages = messages;
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
        String msg = messages[position];
        holder.message.setText(msg);

    }

    @Override
    public int getItemCount() {
        return messages.length;
    }

    public class FragmentChatAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        ImageView profile;
        public FragmentChatAdapterViewHolder(View itemView){

            super(itemView);
            message = itemView.findViewById(R.id.layout_message_right_text);
            profile = itemView.findViewById(R.id.layout_message_right_profile_image);
        }
    }
}
