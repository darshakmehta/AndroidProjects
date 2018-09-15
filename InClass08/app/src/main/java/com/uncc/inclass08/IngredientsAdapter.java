package com.uncc.inclass08;

/**
 * Created by gaurav on 10/30/2017.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{
     ArrayList<String> mData;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public IngredientsAdapter(ArrayList<String> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = mData.get(position);
        holder.row = data;
        holder.ingredients.setText(data);
        holder.addButton.setImageResource(R.drawable.remove);
        holder.addButton.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText ingredients;
        ImageView addButton;
        String row;
        public ViewHolder(View itemView) {
            super(itemView);
            ingredients = (EditText) itemView.findViewById(R.id.word_box);
            addButton = (ImageView) itemView.findViewById(R.id.addImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(row);
                    notifyDataSetChanged();
                }
            });

        }
    }
}
