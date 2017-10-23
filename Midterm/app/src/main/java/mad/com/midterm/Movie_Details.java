package mad.com.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Movie_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textViewName = (TextView) findViewById(R.id.textViewTitleName);
        TextView textViewOverview = (TextView) findViewById(R.id.textViewOverview);
        TextView textViewRelease = (TextView) findViewById(R.id.textViewRelease);
        TextView textViewRating = (TextView) findViewById(R.id.textViewRating);
        ImageView img = (ImageView) findViewById(R.id.imageViewIcon);
        String img_url;
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movieList");
        img_url = "http://image.tmdb.org/t/p/w342/"+movie.getPoster_path();
        Picasso.with(Movie_Details.this).load(img_url).into(img);
        textViewName.setText(movie.getMovie_name());
        textViewOverview.setText("Overview: "+movie.getOverview());
        textViewRelease.setText("Release Date: " + movie.getRelease_date());
        textViewRating.setText("Rating:" + movie.getRating()+"/10");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

}
