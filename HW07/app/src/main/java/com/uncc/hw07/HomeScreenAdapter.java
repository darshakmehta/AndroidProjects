package com.uncc.hw07;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by gaurav on 11/16/2017.
 */

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.ViewHolder>{
    private ArrayList<Post> mDataset;
    static Context context;

    public HomeScreenAdapter(ArrayList<Post> myDataset,Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homescreen_recycler, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.post = (mDataset.get(position));
        holder.textViewUserName.setText(mDataset.get(position).getUser());
        holder.textViewPost.setText(mDataset.get(position).getPost());
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
        holder.textViewUserName.setTag(mDataset.get(position).getUserId());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Post post;
        TextView textViewPost, textViewUserName, textViewTime;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewPost = (TextView) itemView.findViewById(R.id.textViewPost);
            textViewUserName = (TextView) itemView.findViewById(R.id.textViewUserName);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }

}

