package mad.com.inclass07;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by darsh on 10/23/2017.
 */

public class GetAppAsyncTask extends AsyncTask<String, Void, ArrayList<App>> {

    public AsyncResponse response;
    public GetAppAsyncTask(AsyncResponse response) {
        this.response = response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<App> doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == con.HTTP_OK){
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while(line != null){
                    sb.append(line);
                    line = reader.readLine();
                }
                    return AppUtil.AppJSONParser.parseMusic(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }  finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<App> apps) {
        super.onPostExecute(apps);
        if(apps !=null)
            response.processFinish(apps);
    }

    public interface AsyncResponse {
        void processFinish(ArrayList<App> questions);
    }
}
