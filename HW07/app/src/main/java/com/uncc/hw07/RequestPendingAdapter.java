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

public class RequestPendingAdapter extends RecyclerView.Adapter<RequestPendingAdapter.ViewHolder>{
    private ArrayList<AddFriend> mDataset;
    static Context context;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserFriendRef = mRootRef.child("userFriend");
    private FirebaseAuth auth;

    public RequestPendingAdapter(ArrayList<AddFriend> myDataset,Context context) {
        this.mDataset = myDataset;
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requestpending_recycler, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.user = (mDataset.get(position));
        holder.friendName.setText(mDataset.get(position).getFriendName());

        if("RS".equalsIgnoreCase(mDataset.get(position).getStatus())){
            holder.imageViewAcceptFriend.setVisibility(View.GONE);
            holder.imageViewDeclineFriend.setVisibility(View.GONE);

        }else{
            if("RR".equalsIgnoreCase(mDataset.get(position).getStatus())) {
                holder.imageViewDelete.setVisibility(View.GONE);
            }
        }

            holder.imageViewAcceptFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you really want to accept?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        writeAddUserFriend(auth.getUid(),mDataset.get(position).getUserFriendId(),"FF",mDataset.get(position).getFriendName());
                                        writeAddUserFriend(mDataset.get(position).getUserFriendId(),auth.getUid(),"FF",auth.getCurrentUser().getDisplayName());
                                        mDataset.remove(position);
                                        notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        holder.imageViewDeclineFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you really want to decline?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //make deletion
                                        mUserFriendRef.child(auth.getUid()).child(mDataset.get(position).getUserFriendId()).setValue(null);
                                        mUserFriendRef.child(mDataset.get(position).getUserFriendId()).child(auth.getUid()).setValue(null);
                                        mDataset.remove(position);
                                        notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you really want to delete?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //make deletion
                                        mUserFriendRef.child(auth.getUid()).child(mDataset.get(position).getUserFriendId()).setValue(null);
                                        mUserFriendRef.child(mDataset.get(position).getUserFriendId()).child(auth.getUid()).setValue(null);
                                        mDataset.remove(position);
                                        notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
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
        ImageView imageViewAcceptFriend,imageViewDeclineFriend,imageViewDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewAcceptFriend = (ImageView)itemView.findViewById(R.id.imageViewAccept);
            imageViewDeclineFriend = (ImageView)itemView.findViewById(R.id.imageViewDecline);
            imageViewDelete = (ImageView)itemView.findViewById(R.id.imageViewDelete);
            friendName = (TextView) itemView.findViewById(R.id.friendName);
        }
    }

    private void writeAddUserFriend(String userId,String userFriendId, String status,String friendName) {
        AddFriend addFriend = new AddFriend(userId,userFriendId, status,friendName);
        mUserFriendRef.child(userId).child(userFriendId).setValue(addFriend);
    }
}
