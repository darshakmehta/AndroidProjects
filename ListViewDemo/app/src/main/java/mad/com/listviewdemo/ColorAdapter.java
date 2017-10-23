package mad.com.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ColorAdapter extends ArrayAdapter<Color> {
    List<Color> mData;
    Context mContext;
    int mResource;
    public ColorAdapter(Context context,int resource, List<Color> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.colorName = (TextView) convertView.findViewById(R.id.textViewColorName);
            holder.colorHex = (TextView) convertView.findViewById(R.id.textViewColorHex);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        Color color = mData.get(position);
        TextView colorName = holder.colorName;
        TextView colorHex = holder.colorHex;
        colorName.setText(mData.get(position).colorName);
        colorHex.setText(mData.get(position).colorHex);
        colorHex.setTextColor(android.graphics.Color.parseColor(color.colorHex));
        if(position % 2 == 0) {
            convertView.setBackgroundColor(android.graphics.Color.WHITE);
        }else {
            convertView.setBackgroundColor(android.graphics.Color.RED);
        }

        return convertView;
    }
    static class ViewHolder {
        TextView colorName;
        TextView colorHex;
    }

}
