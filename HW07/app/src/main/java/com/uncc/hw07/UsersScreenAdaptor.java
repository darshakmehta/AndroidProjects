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
 * Created by gaurav on 11/16/2017.
 */

public class UsersScreenAdaptor extends RecyclerView.Adapter<UsersScreenAdaptor.ViewHolder>{
    private ArrayList<Post> mDataset;
    static Context context;
    private FirebaseAuth auth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserPostRef = mRootRef.child("userPost");

    public UsersScreenAdaptor(ArrayList<Post> myDataset,Context context) {
        this.mDataset = myDataset;
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userscreen_recycler, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.post = (mDataset.get(position));
        holder.textViewUserName.setText(mDataset.get(position).getUser());
        holder.textViewPost.setText(mDataset.get(position).getPost());

        if(!auth.getCurrentUser().getDisplayName().equals(mDataset.get(position).getUser())){
            holder.imageViewDelete.setVisibility(View.GONE);
        }
        PrettyTime p = new PrettyTime();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String preDate = dateFormat.format(mDataset.get(position).getPostTime());
            date = dateFormat.parse(preDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.textViewTime.setText(p.format(date));
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you really want to delete?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mUserPostRef.child(mDataset.get(position).getUserId()).child(mDataset.get(position).getPostTime()+"").setValue(null);
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
        Post post;
        TextView textViewPost, textViewUserName, textViewTime;
        ImageView imageViewDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewDelete = (ImageView)itemView.findViewById(R.id.imageViewDelete);
            textViewPost = (TextView) itemView.findViewById(R.id.textViewPost);
            textViewUserName = (TextView) itemView.findViewById(R.id.textViewUserName);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }

}

