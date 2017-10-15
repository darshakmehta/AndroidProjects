package mad.com.hw05;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MusicUtil {
    static public class MusicJSONParser {
        static ArrayList<Music> parseMusic(String in) throws JSONException {
            ArrayList<Music> musicList = new ArrayList<Music>();
            JSONObject root = new JSONObject(in);
            JSONObject results = root.getJSONObject("results");
            JSONObject trackmatches = results.getJSONObject("trackmatches");
            JSONArray track = trackmatches.getJSONArray("track");

            for(int i = 0; i<track.length();i++) {
                JSONObject trackJSONObject = track.getJSONObject(i);
                Music music = new Music();
                music.setStar_img(android.R.drawable.btn_star_big_off+"");
                if (trackJSONObject.has("name"))
                    music.setName(trackJSONObject.getString("name").trim());
                if (trackJSONObject.has("artist"))
                    music.setArtist(trackJSONObject.getString("artist").trim());
                if (trackJSONObject.has("url"))
                    music.setUrl(trackJSONObject.getString("url").trim());

                if(trackJSONObject.has("image")) {
                    JSONArray image = trackJSONObject.getJSONArray("image");
                    for (int j = 0; j < image.length(); j++) {
                        JSONObject imageObject = image.getJSONObject(j);
                        if (j == 0) {
                            music.setSmall_img_url(imageObject.getString("#text").trim());
                        } else if (j == 2) {
                            music.setLarge_img_url(imageObject.getString("#text").trim());
                        }
                    }
                }
                musicList.add(music);
            }
            return musicList;

        }
        static ArrayList<Music> parseTrack(String in) throws JSONException {
            ArrayList<Music> musicList = new ArrayList<Music>();
            JSONObject root = new JSONObject(in);
            JSONObject similartracks = root.getJSONObject("similartracks");
            JSONArray trackArray = similartracks.getJSONArray("track");

            for(int i = 0; i<trackArray.length();i++) {
                JSONObject trackJSONObject = trackArray.getJSONObject(i);
                Music music = new Music();
                music.setStar_img(android.R.drawable.btn_star_big_off+"");
                if (trackJSONObject.has("name"))
                    music.setName(trackJSONObject.getString("name").trim());
                if(trackJSONObject.has("artist")) {
                    JSONObject artistObject = trackJSONObject.getJSONObject("artist");
                    if (artistObject.has("name"))
                        music.setArtist(artistObject.getString("name").trim());
                }
                if (trackJSONObject.has("url"))
                    music.setUrl(trackJSONObject.getString("url").trim());

                if(trackJSONObject.has("image")) {
                    JSONArray image = trackJSONObject.getJSONArray("image");
                    for (int j = 0; j < image.length(); j++) {
                        JSONObject imageObject = image.getJSONObject(j);
                        if (j == 0) {
                            music.setSmall_img_url(imageObject.getString("#text").trim());
                        } else if (j == 2) {
                            music.setLarge_img_url(imageObject.getString("#text").trim());
                        }
                    }
                }
                musicList.add(music);
            }
            return musicList;
        }
    }
}
