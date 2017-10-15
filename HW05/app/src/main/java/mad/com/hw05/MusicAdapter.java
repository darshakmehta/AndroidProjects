package mad.com.hw05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {
    Context myContext;
    int myResource;
    List<Music> myMusicList;
    public MusicAdapter( Context context,  int resource,  List<Music> objects) {
        super(context, resource, objects);
        this.myContext = context;
        this.myResource = resource;
        this.myMusicList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textViewName);
            holder.artist = (TextView) convertView.findViewById(R.id.textViewArtist);
            holder.icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            holder.star = (ImageView) convertView.findViewById(R.id.imageViewStar);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        Music music = myMusicList.get(position);

        ImageView icon = holder.icon;
        TextView name = holder.name;
        TextView artist = holder.artist;
        ImageView star = holder.star;

        name.setText(myMusicList.get(position).name);
        artist.setText(myMusicList.get(position).artist);
        star.setImageResource(Integer.parseInt(music.getStar_img()));
        Picasso.with(myContext).load(myMusicList.get(position).large_img_url).into(icon);

        return convertView;
    }

    public static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView artist;
        ImageView star;
        int flag;
    }
}
