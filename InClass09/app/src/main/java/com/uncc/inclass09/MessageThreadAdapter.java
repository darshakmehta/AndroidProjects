package com.uncc.inclass09;

/**
 * Created by gaurav on 11/6/2017.
 */


        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;


public class MessageThreadAdapter extends RecyclerView.Adapter<MessageThreadAdapter.ViewHolder>{
    private ArrayList<MessageThreadResponse> mDataset;
    static Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageThreadAdapter(ArrayList<MessageThreadResponse> myDataset,Context context) {
        mDataset = myDataset;
        this.context = context;
    }


    public ArrayList<MessageThreadResponse> getList() {
        return mDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thread_row_activity, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.messageThreadResponse = (mDataset.get(position));
        holder.MessageThreadName.setText(mDataset.get(position).getTitle());
        holder.MessageThreadName.setTag(position);
        holder.imageView.setVisibility(Integer.parseInt(mDataset.get(position).getImage()));
        holder.imageView.setTag(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView MessageThreadName;
        ImageView imageView;
        MessageThreadResponse messageThreadResponse;
        public ViewHolder(View itemView) {
            super(itemView);
            MessageThreadName = (TextView) itemView.findViewById(R.id.textViewThreadName);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewRemove);
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

