package mad.com.hw05;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import java.util.ArrayList;

public class Search_Results extends AppCompatActivity {

    ListView listView;
    ArrayList<Music> musicList;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Search_Results.this);
        editor = sharedPrefs.edit();

        musicList = (ArrayList<Music>) getIntent().getSerializableExtra(MainActivity.MUSIC_LIST);
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

        listView = (ListView) findViewById(R.id.resultListView);
        MusicAdapter adapter = new MusicAdapter(this, R.layout.row_item_layout, musicList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music music = musicList.get(position);
                Intent intent = new Intent(Search_Results.this, Track_Details.class);
                intent.putExtra("trackList", music);
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
        if (id == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.quit) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MainActivity.SHOULD_FINISH, true);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
