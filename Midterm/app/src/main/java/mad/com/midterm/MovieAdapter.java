package mad.com.midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    Context myContext;
    int myResource;
    List<Movie> movieList;
    public MovieAdapter( Context context,  int resource,  List<Movie> objects) {
        super(context, resource, objects);
        this.myContext = context;
        this.myResource = resource;
        this.movieList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myResource, parent, false);
            holder = new ViewHolder();
            holder.movie_name = (TextView) convertView.findViewById(R.id.textViewName);
            holder.release_date = (TextView) convertView.findViewById(R.id.textViewArtist);
            holder.icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            holder.star = (ImageView) convertView.findViewById(R.id.imageViewStar);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        Movie movie = movieList.get(position);

        ImageView icon = holder.icon;
        TextView movie_name = holder.movie_name;
        TextView release_date = holder.release_date;
        ImageView star = holder.star;

        movie_name.setText(movieList.get(position).movie_name);
        release_date.setText("Released in " + movieList.get(position).release_date.substring(0,4));
        star.setImageResource(Integer.parseInt(movie.getSetStar()));
        String img_url = "http://image.tmdb.org/t/p/w154/"+movie.getPoster_path();
        Picasso.with(myContext).load(img_url).into(icon);

        return convertView;
    }

    public static class ViewHolder {
        ImageView icon;
        TextView movie_name;
        TextView release_date;
        ImageView star;
        int flag;
    }
}
