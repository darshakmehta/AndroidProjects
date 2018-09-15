package com.uncc.inclass09;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gaurav on 11/7/2017.
 */

public class ChatRoomMessageAdapter extends RecyclerView.Adapter<ChatRoomMessageAdapter.ViewHolder>{
    private ArrayList<ChatRoomThreadResponse> mDataset;
    static Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatRoomMessageAdapter(ArrayList<ChatRoomThreadResponse> myDataset,Context context) {
       this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row_activity, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.chatRoomThreadResponse = (mDataset.get(position));
        holder.textViewUserName.setText(mDataset.get(position).getUser_fname()+" "+mDataset.get(position).getUser_lname());
        Log.d("messageThreadResponse",holder.chatRoomThreadResponse.getMessage());
        holder.textViewChatMessage.setText(mDataset.get(position).getMessage());
        holder.textViewTime.setText(mDataset.get(position).getCreated_at());
        holder.imageViewDelete.setVisibility(Integer.parseInt(mDataset.get(position).getImage()));
        holder.imageViewDelete.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ChatRoomThreadResponse chatRoomThreadResponse;
        TextView textViewChatMessage, textViewUserName, textViewTime;
        ImageView imageViewDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewChatMessage = (TextView) itemView.findViewById(R.id.textViewChatMessage);
            textViewUserName = (TextView) itemView.findViewById(R.id.textViewUserName);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
            imageViewDelete = (ImageView) itemView.findViewById(R.id.imageViewDelete);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("messageThreadResponse","reached");
                    messageThreadAdapterResponse.
                    Intent i = new Intent(context, ChatroomActivity.class);
                    i.putExtra("chatRoomObject",messageThreadResponse);
                    context.startActivity(i);
                    Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT);
                }
            });*/
        }
    }

}
