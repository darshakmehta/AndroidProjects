package mad.com.multifragmentdemo;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{

    ArrayList<String> mData;
    static Context mContext;

    public IngredientsAdapter(ArrayList<String> mData,Context context) {
        this.mData = mData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_activity, parent, false);
        ViewHolder viewHolder =  new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s = mData.get(position);
        holder.editText.setText(mData.get(position));
        holder.editText.setEnabled(false);
        holder.imageView.setImageResource(R.drawable.add);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        EditText editText;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.addImage);
            editText = (EditText) itemView.findViewById(R.id.word_box);

        }
    }
}