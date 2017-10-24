package mad.com.inclass07;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by darsh on 10/23/2017.
 */

public class AppRecyclerView extends RecyclerView.Adapter<AppRecyclerView.ViewHolder> {
    ArrayList<App> mData;
    Context context;
    DBDataManager databaseManager;
    public AppRecyclerView(ArrayList<App> mData, Context context, DBDataManager databaseManager) {
        this.mData = mData;
        this.context = context;
        this.databaseManager = databaseManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filteredapp, parent, false);
        ViewHolder viewHolder =  new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        App app = mData.get(position);
        holder.appName.setText(app.getName());
        holder.priceValue.setText("Price : USD " + app.getPrice()+"");
        holder.delete.setImageResource(R.drawable.delete_icon);
        Picasso.with(context).load(app.getLarge_thumb_url()).into(holder.imageView);
        Double value = Double.parseDouble(app.getPrice());
        if(value < 2 && value >= 0 )
            holder.priceSymbol.setImageResource(R.drawable.price_low);
        else if(value >= 2 && value < 6)
            holder.priceSymbol.setImageResource(R.drawable.price_medium);
        else
            holder.priceSymbol.setImageResource(R.drawable.price_high);
        holder.app = app;
       holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.appList.add(mData.get(position));
                if(MainActivity.aSwitch.isChecked()){
                    Collections.sort(MainActivity.appList,new PriceComparatorAsc());
                }else {
                    Collections.sort(MainActivity.appList,new PriceComparator());
                }
                MainActivity.adapter.notifyDataSetChanged();
                databaseManager.deleteApp(mData.get(position));
                mData.remove(position);
                if(MainActivity.filterAppList.isEmpty() || MainActivity.filterAppList==null){
                    MainActivity.textViewFilter.setVisibility(View.VISIBLE);
                }else {
                    MainActivity.textViewFilter.setVisibility(View.INVISIBLE);
                }

                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appName, priceValue;
        ImageView imageView, priceSymbol, delete;
        App app;

        public ViewHolder(View itemView) {
            super(itemView);
            this.app = app;
            appName = (TextView) itemView.findViewById(R.id.appName);
            priceValue = (TextView) itemView.findViewById(R.id.priceValue);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            priceSymbol = (ImageView) itemView.findViewById(R.id.priceSymbol);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }


}
