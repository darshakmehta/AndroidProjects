package mad.com.inclass07;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darsh on 10/23/2017.
 */

public class AppAdapter extends ArrayAdapter<App> {
    Context myContext;
    int myResource;
    List<App> myAppList;
    public AppAdapter( Context context,  int resource,  List<App> objects) {
        super(context, resource, objects);
        this.myContext = context;
        this.myResource = resource;
        this.myAppList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textViewName);
            holder.price = (TextView) convertView.findViewById(R.id.textViewPrice);
            holder.icon = (ImageView) convertView.findViewById(R.id.imageViewSmall);
            holder.dollar = (ImageView) convertView.findViewById(R.id.imageViewDollar);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        App app = myAppList.get(position);

        ImageView icon = holder.icon;
        TextView name = holder.name;
        TextView price = holder.price;
        ImageView dollar = holder.dollar;

        name.setText(myAppList.get(position).name);
        price.setText("Price : USD " + myAppList.get(position).price + "");
        Picasso.with(myContext).load(myAppList.get(position).large_thumb_url).into(icon);
        Double value = Double.parseDouble(app.getPrice());
        if(value < 2 && value >= 0 )
            dollar.setImageResource(R.drawable.price_low);
        else if(value >= 2 && value < 6)
            dollar.setImageResource(R.drawable.price_medium);
        else
            dollar.setImageResource(R.drawable.price_high);
        return convertView;
    }

    public static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView price;
        ImageView dollar;
    }
}
