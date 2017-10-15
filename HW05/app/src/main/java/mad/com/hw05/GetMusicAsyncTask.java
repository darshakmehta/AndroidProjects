package mad.com.hw05;

import android.os.AsyncTask;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetMusicAsyncTask extends AsyncTask<String, Void, ArrayList<Music>>{

    public AsyncResponse response;
    public GetMusicAsyncTask(AsyncResponse response) {
        this.response = response;
    }
    @Override
    protected ArrayList<Music> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            String method = params[1];
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == con.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while(line != null){
                    sb.append(line);
                    line = reader.readLine();
                }
                if("search".equalsIgnoreCase(method))
                    return MusicUtil.MusicJSONParser.parseMusic(sb.toString());
                else if("similar".equalsIgnoreCase(method))
                    return MusicUtil.MusicJSONParser.parseTrack(sb.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Music> musics) {
        super.onPostExecute(musics);
        response.processFinish(musics);
    }
    public interface AsyncResponse {
        void processFinish(ArrayList<Music> questions);
    }
}
