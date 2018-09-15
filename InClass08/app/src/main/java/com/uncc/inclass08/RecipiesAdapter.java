package com.uncc.inclass08;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gaurav on 10/24/2017.
 */

public class RecipiesAdapter extends RecyclerView.Adapter<RecipiesAdapter.ViewHolder> {
    ArrayList<Recepie> mData;
    static Context mContext;

    public RecipiesAdapter(ArrayList<Recepie> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipies_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recepie recepie = mData.get(position);
        holder.titleRecepie.setText("Tiltle:"+" "+recepie.title);
        holder.ingredValue.setText(" "+recepie.ingredients.toString());
        if(recepie.thumbnil!=null && !"".equalsIgnoreCase(recepie.thumbnil)) {
            Picasso.with(mContext).load(recepie.thumbnil).into(holder.recepieImage);
        }
        holder.urlValue.setText(" "+recepie.href.toString());
        final String href = recepie.href.toString();
        holder.urlValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(href));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleRecepie, ingredValue,urlValue;
        ImageView recepieImage;
        Recepie recepie;
        public ViewHolder(View itemView) {
            super(itemView);
            titleRecepie = (TextView) itemView.findViewById(R.id.titleRecepie);
            recepieImage = (ImageView)itemView.findViewById(R.id.recepieImage);
            ingredValue = (TextView) itemView.findViewById(R.id.ingredValue);
            urlValue = (TextView)itemView.findViewById(R.id.urlValue);

        }
    }

}