package mad.com.hw05;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class Track_Details extends AppCompatActivity {

    String url, initial_URL, api_key, limit, format;
    StringBuilder trackURL;
    ArrayList<Music> musicList;
    ListView similarTracks;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Track_Details.this);
        editor = sharedPrefs.edit();

        Music music = (Music) getIntent().getExtras().getSerializable("trackList");

        ImageView img = (ImageView) findViewById(R.id.imageViewIcon);
        TextView nameBox = (TextView) findViewById(R.id.textViewName);
        TextView artistBox = (TextView) findViewById(R.id.textViewArtist);
        TextView URLBox = (TextView) findViewById(R.id.textViewURL);

        String name = music.name.trim();
        String nameNew = name.replace(" ", "+");
        String artist = music.artist.trim();
        String artistNew = artist.replace(" ","+");
        url = music.url;
        nameBox.setText("Name: "+name);
        artistBox.setText("Artist: "+artist);
        URLBox.setText("URL: "+url);
        URLBox.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
        URLBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        Picasso.with(this).load(music.large_img_url).into(img);

        trackURL = new StringBuilder();
        initial_URL = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist=";
        api_key = "&api_key=b3bb9685c793aa5972f8bb8227edb5d7";
        limit = "&limit=10";
        format = "&format=json";

        trackURL.append(initial_URL+artistNew+"&track="+nameNew+api_key+format+limit);
        Log.d("demo",trackURL.toString());

        similarTracks  = (ListView) findViewById(R.id.trackDetail);
        new GetMusicAsyncTask(new GetMusicAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<Music> musics) {
                musicList = musics;
                if(MainActivity.favMusicList!=null && musicList!=null) {
                    for (Music music : musicList) {
                        for (Music favMusic : MainActivity.favMusicList) {
                            if (music.getName().equalsIgnoreCase(favMusic.getName())) {
                                if (music.getArtist().equalsIgnoreCase(favMusic.getArtist())) {
                                    if (music.getUrl().equalsIgnoreCase(favMusic.getUrl())) {
                                        music.setFlag(1);
                                        favMusic.setFlag(1);
                                        music.setStar_img(android.R.drawable.btn_star_big_on+"");
                                        favMusic.setStar_img(android.R.drawable.btn_star_big_on+"");
                                    }
                                }
                            }
                        }
                    }
                }
                if(musicList!=null && musicList.size()>0) {
                    MusicAdapter trackAdapter = new MusicAdapter(Track_Details.this, R.layout.row_item_layout, musics);
                    trackAdapter.setNotifyOnChange(true);
                    similarTracks.setAdapter(trackAdapter);
                }else {
                    Toast.makeText(Track_Details.this,"No similar Track found", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute(trackURL.toString(),"similar");

        similarTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music music = musicList.get(position);
                Intent intent = new Intent(Track_Details.this,Track_Details.class);
                intent.putExtra("trackList",music);
                startActivity(intent);
            }
        });
    }

    public void StarClick(View v) {

        ImageView img = (ImageView) v;
        View parentRow = (View) v.getParent();
        LinearLayout linearLayout = (LinearLayout) parentRow.getParent();
        ListView listView = (ListView) linearLayout.getParent();
        int position = listView.getPositionForView(parentRow);

        Music music = musicList.get(position);
        if(music.getFlag() == 0){
            img.setImageResource(android.R.drawable.btn_star_big_on);
            music.setStar_img(android.R.drawable.btn_star_big_on+"");
            music.setFlag(1);
            MainActivity.favMusicList.add(music);
            Gson gson = new Gson();
            String json = gson.toJson(MainActivity.favMusicList);
            editor.putString(MainActivity.FAVORITES, json);
            editor.commit();
            Toast.makeText(this, "You have favourited a track", Toast.LENGTH_SHORT).show();
        }else if(music.getFlag() == 1){
            img.setImageResource(android.R.drawable.btn_star_big_off);
            music.setStar_img(android.R.drawable.btn_star_big_off+"");
            music.setFlag(0);
            MainActivity.favMusicList.remove(position);
            Gson gson = new Gson();
            String json = gson.toJson(MainActivity.favMusicList);
            editor.putString(MainActivity.FAVORITES, json);
            editor.commit();
            Toast.makeText(this, "You have un-favourited a track", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if(id == R.id.quit){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MainActivity.SHOULD_FINISH, true);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
