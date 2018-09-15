package com.uncc.hw07;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by gaurav on 11/17/2017.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{
    private ArrayList<AddFriend> mDataset;
    static Context context;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserFriendRef = mRootRef.child("userFriend");
    private FirebaseAuth auth;

    public FriendsAdapter(ArrayList<AddFriend> myDataset,Context context) {
        this.mDataset = myDataset;
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_recycler, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.user = (mDataset.get(position));
        String name = mDataset.get(position).getFriendName();
        holder.friendName.setText(name);
        holder.friendName.setTag(mDataset.get(position).getUserFriendId());
        holder.imageViewRemoveFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you really want to add?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       *//* writeAddUserFriend(auth.getUid(),mDataset.get(position).getUserId(),"RS",mDataset.get(position).getFirstName()+" "+mDataset.get(position).getLastName());
                                        writeAddUserFriend(mDataset.get(position).getUserId(),auth.getUid(),"RR",auth.getCurrentUser().getDisplayName());
                                        mDataset.remove(position);
                                        notifyDataSetChanged();*//*
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AddFriend user;
        TextView friendName;
        ImageView imageViewRemoveFriend;
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewRemoveFriend = (ImageView)itemView.findViewById(R.id.imageViewRemoveFriend);
            friendName = (TextView) itemView.findViewById(R.id.friendName);
        }
    }

    private void writeAddUserFriend(String userId,String userFriendId, String status,String userName) {
        AddFriend addFriend = new AddFriend(userId,userFriendId, status,userName);
        mUserFriendRef.child(userId).child(userFriendId).setValue(addFriend);
    }
}

