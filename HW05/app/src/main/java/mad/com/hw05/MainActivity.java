package mad.com.hw05;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity {
    StringBuilder url;
    EditText editText;
    String initial_URL, api_key, limit, search_text;
    public static ArrayList<Music> favMusicList;
    ListView listViewFav;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    public static final String SHOULD_FINISH = "finish";
    public static final String MUSIC_LIST = "Music List";
    public static final String FAVORITES = "Music_Favorite";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getBooleanExtra(MainActivity.SHOULD_FINISH, false)) {
            finish();
        }

        editText = (EditText) findViewById(R.id.editTextSearchBox);
        url = new StringBuilder();
        initial_URL = "http://ws.audioscrobbler.com/2.0/?&format=json&method=track.search&track=";
        api_key = "&api_key=b3bb9685c793aa5972f8bb8227edb5d7";
        limit = "&limit=20";

        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_text = editText.getText().toString();
                if((!"".equalsIgnoreCase(search_text)) && (!"#".equalsIgnoreCase(search_text))){
                    url.append(initial_URL + search_text + api_key + limit);
                    new GetMusicAsyncTask(new GetMusicAsyncTask.AsyncResponse() {
                        @Override
                        public void processFinish(ArrayList<Music> musics) {
                            if (musics != null && musics.size() > 0) {
                                Intent intent = new Intent(MainActivity.this, Search_Results.class);
                                intent.putExtra(MainActivity.MUSIC_LIST, musics);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "No music found with track name: " + search_text, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).execute(url.toString(), "search");
                }else{
                    if("#".equalsIgnoreCase(search_text)){
                        Toast.makeText(MainActivity.this, "Track Name is not valid", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Track Name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if(sharedPrefs.contains(FAVORITES)){
            String json = sharedPrefs.getString(FAVORITES, null);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Music>>() {}.getType();
            favMusicList = gson.fromJson(json, type);

            if(favMusicList.size()>20)
                favMusicList = (ArrayList<Music>) favMusicList.subList(0,20);

            listViewFav=(ListView)findViewById(R.id.myFavList);
            MusicAdapter adapter = new MusicAdapter(this, R.layout.row_item_layout, favMusicList);
            listViewFav.setAdapter(adapter);

            listViewFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Music music = favMusicList.get(position);
                    Intent intent = new Intent(MainActivity.this, Track_Details.class);
                    intent.putExtra("trackList", music);
                    startActivity(intent);
                }
            });
        }else{
            favMusicList = new ArrayList<Music>();
        }
        /*editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();*/
    }

    public void StarClick(View v) {

        ImageView img = (ImageView) v;
        View parentRow = (View) v.getParent();
        LinearLayout linearLayout = (LinearLayout) parentRow.getParent();
        ListView listView = (ListView) linearLayout.getParent();
        int position = listView.getPositionForView(parentRow);

        favMusicList.remove(position);

        editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.favMusicList);
        editor.putString(FAVORITES, json);
        editor.commit();
        if(favMusicList.size()>20)
            favMusicList = (ArrayList<Music>) favMusicList.subList(0,20);
        MusicAdapter adapter = new MusicAdapter(this, R.layout.row_item_layout, favMusicList);
        listViewFav.setAdapter(adapter);
        Toast.makeText(this, "Track removed from Favorites", Toast.LENGTH_SHORT).show();
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
