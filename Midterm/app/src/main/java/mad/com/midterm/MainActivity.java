package mad.com.midterm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.midi.MidiOutputPort;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    StringBuilder url;
    String initial_url, search_text, api_key, page;
    EditText editText;
    ListView listView;
    final static String SHOULD_FINISH = "FINISH";
    ArrayList<Movie> movieList;
    public static ArrayList<Movie> favMovieList;
    public static final String FAVORITES = "Music_Favorite";
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getBooleanExtra(MainActivity.SHOULD_FINISH, false)) {
            finish();
        }else if(getIntent().getBooleanExtra("flag",false)) {
            ArrayList<Movie> moviesRating = (ArrayList<Movie>) getIntent().getSerializableExtra("movieListRating");
            listView = (ListView) findViewById(R.id.myResults);
            movieList = moviesRating;
            MovieAdapter adapter = new MovieAdapter(MainActivity.this,R.layout.row_item_layout,movieList);
            listView.setAdapter(adapter);
            for(Movie m : movieList)
                Log.d("demo",m.getRating());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie = movieList.get(position);
                    Intent intent = new Intent(MainActivity.this, Movie_Details.class);
                    intent.putExtra("movieList", movie);
                    startActivity(intent);
                }
            });
        }else if(getIntent().getBooleanExtra("flagPopularity",false)) {
            ArrayList<Movie> moviesPopularity = (ArrayList<Movie>) getIntent().getSerializableExtra("movieListPopularity");
            listView = (ListView) findViewById(R.id.myResults);
            movieList = moviesPopularity;
            MovieAdapter adapter = new MovieAdapter(MainActivity.this,R.layout.row_item_layout,movieList);
            listView.setAdapter(adapter);
            for(Movie m : movieList)
                Log.d("demo",m.getPopularity());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie = movieList.get(position);
                    Intent intent = new Intent(MainActivity.this, Movie_Details.class);
                    intent.putExtra("movieList", movie);
                    startActivity(intent);
                }
            });
        }
        favMovieList = new ArrayList<Movie>();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = sharedPrefs.edit();
        listView = (ListView) findViewById(R.id.myResults);
        url = new StringBuilder();
        editText = (EditText) findViewById(R.id.editTextSearchBox);
        initial_url = "https://api.themoviedb.org/3/search/movie?query=";
        api_key = "&api_key=e4022bd38090f2695725b4908852bfcd";
        page = "&page=1";
        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_text = editText.getText().toString();
                url.append(initial_url + search_text + api_key + page);
                if ((!"".equalsIgnoreCase(search_text)) && (!"#".equalsIgnoreCase(search_text))) {
                    if (isConnected()) {
                        new GetMovieAsyncTask(new GetMovieAsyncTask.AsyncResponse() {
                            @Override
                            public void processFinish(ArrayList<Movie> movies) {
                                movieList = movies;
                                if (movies != null && movies.size() > 0) {
                                    MovieAdapter adapter = new MovieAdapter(MainActivity.this,R.layout.row_item_layout,movies);
                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Movie movie = movieList.get(position);
                                            Intent intent = new Intent(MainActivity.this, Movie_Details.class);
                                            intent.putExtra("movieList", movie);
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    Toast.makeText(MainActivity.this, "No Movie found with movie name: " + search_text, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).execute(url.toString());
                    } else {
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if ("#".equalsIgnoreCase(search_text)) {
                        Toast.makeText(MainActivity.this, "Movie name is not valid", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Movie Name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void StarClick(View v) {
        ImageView img = (ImageView) v;
        View parentRow = (View) v.getParent();
        LinearLayout linearLayout = (LinearLayout) parentRow.getParent();
        ListView listView = (ListView) linearLayout.getParent();
        int position = listView.getPositionForView(parentRow);

        Movie movie = movieList.get(position);
        if(movie.getFlag() == 0){
            img.setImageResource(android.R.drawable.btn_star_big_on);
            movie.setSetStar(android.R.drawable.btn_star_big_on+"");
            movie.setFlag(1);
            MainActivity.favMovieList.add(movie);
            Gson gson = new Gson();
            String json = gson.toJson(MainActivity.favMovieList);
            editor.putString(MainActivity.FAVORITES, json);
            editor.commit();
            Toast.makeText(this, "You have favourited a movie", Toast.LENGTH_SHORT).show();
        }else if(movie.getFlag() == 1){
            img.setImageResource(android.R.drawable.btn_star_big_off);
            movie.setSetStar(android.R.drawable.btn_star_big_off+"");
            movie.setFlag(0);
            MainActivity.favMovieList.remove(position);
            Gson gson = new Gson();
            String json = gson.toJson(MainActivity.favMovieList);
            editor.putString(MainActivity.FAVORITES, json);
            editor.commit();
            Toast.makeText(this, "You have un-favourited a movie", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemQuit) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MainActivity.SHOULD_FINISH, true);
            startActivity(intent);
        } else if(id == R.id.itemFavorites) {
            Intent intent = new Intent(this, Favorite_Movies.class);
            intent.putExtra("favorites",MainActivity.favMovieList);
            startActivity(intent);
        } else if(id == R.id.itemRating) {
            Intent intent = new Intent(this, MainActivity.class);
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return o2.getRating().compareTo(o1.getRating());
                }
            });
            intent.putExtra("flag", true);
            intent.putExtra("movieListRating",movieList);
            startActivity(intent);
        } else if(id == R.id.itemPopularity) {
            Intent intent = new Intent(this, MainActivity.class);
            Collections.sort(movieList, new Comparator<Movie>() {

                @Override
                public int compare(Movie o1, Movie o2) {
                    return o2.getPopularity().compareTo(o1.getPopularity());
                }
            });
            intent.putExtra("flagPopularity", true);
            intent.putExtra("movieListPopularity",movieList);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
