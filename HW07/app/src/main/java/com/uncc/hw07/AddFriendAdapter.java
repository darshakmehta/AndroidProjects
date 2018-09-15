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

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by gaurav on 11/17/2017.
 */

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.ViewHolder>{
    private ArrayList<User> mDataset;
    static Context context;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserFriendRef = mRootRef.child("userFriend");
    private FirebaseAuth auth;

    public AddFriendAdapter(ArrayList<User> myDataset,Context context) {
        this.mDataset = myDataset;
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addfriend_recycler, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.user = (mDataset.get(position));
            String name = mDataset.get(position).getFirstName() + " " + mDataset.get(position).getLastName();
            holder.friendName.setText(name);
            holder.imageViewAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    writeAddUserFriend(auth.getUid(),mDataset.get(position).getUserId(),"RS",mDataset.get(position).getFirstName()+" "+mDataset.get(position).getLastName());
                    writeAddUserFriend(mDataset.get(position).getUserId(),auth.getUid(),"RR",auth.getCurrentUser().getDisplayName());
                    mDataset.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        User user;
        TextView friendName;
        ImageView imageViewAddFriend;
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewAddFriend = (ImageView)itemView.findViewById(R.id.imageViewDelete);
            friendName = (TextView) itemView.findViewById(R.id.friendName);
        }
    }

    private void writeAddUserFriend(String userId,String userFriendId, String status,String userName) {
        AddFriend addFriend = new AddFriend(userId,userFriendId, status,userName);
        mUserFriendRef.child(userId).child(userFriendId).setValue(addFriend);
    }
}
