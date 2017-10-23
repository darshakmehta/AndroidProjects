package mad.com.midterm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Favorite_Movies extends AppCompatActivity {

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite__movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Favorite_Movies.this);
        editor = sharedPrefs.edit();
        Intent intent = getIntent();
        ListView listView = (ListView) findViewById(R.id.movieFavList);
        if(sharedPrefs.contains(MainActivity.FAVORITES)){
            String json = sharedPrefs.getString(MainActivity.FAVORITES, null);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
            MainActivity.favMovieList = gson.fromJson(json, type);
            MovieAdapter adapter = new MovieAdapter(this, R.layout.row_item_layout, MainActivity.favMovieList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie = MainActivity.favMovieList.get(position);
                    Intent intent = new Intent(Favorite_Movies.this, Movie_Details.class);
                    intent.putExtra("movieList", movie);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this,"No Favourite Movies",Toast.LENGTH_LONG).show();
        }
    }

    public void StarClick(View v) {
        ImageView img = (ImageView) v;
        View parentRow = (View) v.getParent();
        LinearLayout linearLayout = (LinearLayout) parentRow.getParent();
        ListView listView = (ListView) linearLayout.getParent();
        int position = listView.getPositionForView(parentRow);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = MainActivity.favMovieList.get(position);
                Intent intent = new Intent(Favorite_Movies.this, Movie_Details.class);
                intent.putExtra("movieList", movie);
                startActivity(intent);
            }
        });

        Movie movie = MainActivity.favMovieList.get(position);
        if (movie.getFlag() == 0) {
            img.setImageResource(android.R.drawable.btn_star_big_on);
            movie.setSetStar(android.R.drawable.btn_star_big_on + "");
            movie.setFlag(1);
            MainActivity.favMovieList.add(movie);
            Gson gson = new Gson();
            String json = gson.toJson(MainActivity.favMovieList);
            editor.putString(MainActivity.FAVORITES, json);
            editor.commit();
            Toast.makeText(this, "You have favourited a movie", Toast.LENGTH_SHORT).show();
        } else if (movie.getFlag() == 1) {
            img.setImageResource(android.R.drawable.btn_star_big_off);
            movie.setSetStar(android.R.drawable.btn_star_big_off + "");
            movie.setFlag(0);
            MainActivity.favMovieList.remove(position);
            Gson gson = new Gson();
            String json = gson.toJson(MainActivity.favMovieList);
            editor.putString(MainActivity.FAVORITES, json);
            editor.commit();
            MovieAdapter adapter = new MovieAdapter(this, R.layout.row_item_layout, MainActivity.favMovieList);
            listView.setAdapter(adapter);
            Toast.makeText(this, "You have un-favourited a movie", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if(id == R.id.itemQuit){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MainActivity.SHOULD_FINISH, true);
            startActivity(intent);
        } else if(id == R.id.itemRating) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Collections.sort(MainActivity.favMovieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return o1.getRating().compareTo(o2.getRating());
                }
            });
            intent.putExtra("movieList",MainActivity.favMovieList);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
